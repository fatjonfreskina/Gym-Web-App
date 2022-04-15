package service;

import constants.exceptions.*;
import dao.lecturetimeslot.GetLectureTimeSlotByCourseEditionIdNowDatabase;
import dao.lecturetimeslot.GetLectureTimeSlotsByCourseDatabase;
import dao.lecturetimeslot.GetLectureTimeSlotsInRangeDatabase;
import dao.reservation.DeleteReservation;
import dao.reservation.GetListReservationByLectureDatabase;
import dao.reservation.InsertReservationDatabase;
import dao.room.GetRoomByNameDatabase;
import dao.subscription.GetSubscriptionsByCourseDatabase;
import dao.teaches.GetTeachesByTrainerDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.*;
import resource.rest.TrainerAttendance;
import resource.view.CourseStatus;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainerService {

  private final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = "gwa.service.trainer: ";
  private final DataSource dataSource;
  private final String trainerEmail;

  public TrainerService(DataSource dataSource, String trainerEmail) {
    this.dataSource = dataSource;
    this.trainerEmail = trainerEmail;
    logger.debug(loggerClass + "Trainer " + trainerEmail);
    //TODO DO WE NEED TO CHECK THE EMAIL? -> ALREADY DONE THROUGH FILTERS
  }

  //D
  public boolean removePresenceFromCurrentLectureTimeSlot(Reservation reservation) throws SQLException, ReservationNotFound, TrainerCoursesOverlapping, TrainerNoCourseHeld, TrainerNoCourseHeldNow {
    logger.trace(loggerClass + "Reservation:" + reservation);

    //checkReservation(lectureTimeSlot,reservation);
    getTrainersCurrentLectureTimeSlot(trainerEmail); //checking
    Reservation deleted = new DeleteReservation(dataSource.getConnection(), reservation).execute();
    if (deleted == null) {
      logger.debug("NOT FOUND -> NOT DELETED");
      throw new ReservationNotFound();
    }
    return true;
  }

  //P
  public boolean addPresenceToCurrentLectureTimeSlot(Subscription subscription) throws NamingException, SQLException, ReservationAlreadyPresent, RoomAlreadyFull, TraineeNotEnrolledToTheCourse, TrainerCoursesOverlapping, TrainerNoCourseHeld, TrainerNoCourseHeldNow, SubscriptionNotStartedOrTerminated {
    LectureTimeSlot lectureHeldNow = getTrainersCurrentLectureTimeSlot(trainerEmail);
    CourseEdition courseEdition = new CourseEdition(lectureHeldNow.getCourseEditionId(), lectureHeldNow.getCourseName());
    // check if the trainee can be added, has a subscription to this course
    logger.trace(loggerClass + "validating subscription(" + subscription + "," + courseEdition + ")");

    validateSubscription(subscription, courseEdition);//propagating the errors

    logger.trace(loggerClass + "isValidSubscription");
    List<Reservation> reservations = new GetListReservationByLectureDatabase(dataSource.getConnection(), lectureHeldNow).execute();
    logger.trace(loggerClass + "Reservations: " + reservations);

    Room room = new GetRoomByNameDatabase(dataSource.getConnection(), new Room(lectureHeldNow.getRoomName())).execute();

    Reservation reservationToAdd = new Reservation(subscription.getTrainee(), room.getName(), lectureHeldNow.getDate(), lectureHeldNow.getStartTime());
    if (reservations.contains(reservationToAdd)) {
      logger.warn(loggerClass + "Already present");
      throw new ReservationAlreadyPresent();
    }

    //Check if there are enough spots available for the given room
    int availability = room.getSlots() - reservations.size();
    logger.debug("RoomSlots - reservations=" + room.getSlots() + " - " + reservations.size() + " = " + availability);
    if (availability >= 1) {
      new InsertReservationDatabase(dataSource.getConnection(), reservationToAdd).execute();
    } else {
      throw new RoomAlreadyFull();
    }
    return true; //is always true or it will throw an error
  }

  //G
  public TrainerAttendance getTrainerAttendance() throws SQLException, TrainerCoursesOverlapping, TrainerNoCourseHeld, TrainerNoCourseHeldNow, NoSubscriptionToTheCourse {
    LectureTimeSlot lectureHeldNow = getTrainersCurrentLectureTimeSlot(trainerEmail);

    logger.debug(loggerClass + "Current Lecture: " + lectureHeldNow);

    //Get the list of reservation for the lecture now and their corresponding trainees
    List<Reservation> reservations = new GetListReservationByLectureDatabase(dataSource.getConnection(), lectureHeldNow).execute();

    //TODO change back to GetValidSubscriptionByCourseDatabase, problems:
    // 1) does not give the valid ones (date problems)
    // 2) not all invalid are removed
    // CUR sol: giving back all subscription but adding only those are valid
    //Get the subscriptions for the course held now and their corresponding trainees
    List<Subscription> subscriptions = new GetSubscriptionsByCourseDatabase(dataSource.getConnection(), new CourseEdition(lectureHeldNow.getCourseEditionId(), lectureHeldNow.getCourseName())).execute();

    if (reservations.isEmpty() && subscriptions.isEmpty()) {
      throw new NoSubscriptionToTheCourse();
    }
    for (Reservation r : reservations) {
      String trainee = r.getTrainee();
      subscriptions = subscriptions.stream().filter(subscription -> !subscription.getTrainee().equals(trainee)).collect(Collectors.toList());
    }
    return new TrainerAttendance(lectureHeldNow, reservations, subscriptions);
  }

  public LectureTimeSlot getTrainersCurrentLectureTimeSlot(String trainerEmail) throws SQLException, TrainerNoCourseHeldNow, TrainerCoursesOverlapping, TrainerNoCourseHeld {
    List<Teaches> teaches = new GetTeachesByTrainerDatabase(dataSource.getConnection(), new Person(trainerEmail)).execute();

    if (teaches.isEmpty()) throw new TrainerNoCourseHeld();

    logger.debug(loggerClass + teaches);
    //Get the lecture that should be held now
    List<LectureTimeSlot> lectureTimeSlots = new ArrayList<>();
    for (Teaches t : teaches) {
      LectureTimeSlot l = new GetLectureTimeSlotByCourseEditionIdNowDatabase(dataSource.getConnection(), new LectureTimeSlot(t.getCourseEdition(), t.getCourseName())).execute();
      if (l != null) lectureTimeSlots.add(l);
    }

    if (lectureTimeSlots.isEmpty()) throw new TrainerNoCourseHeldNow();

    //TODO INTERNAL ERROR? SECRETARY SHOULD NOT ADD IT IN FIRST PLACE
    if (lectureTimeSlots.size() > 1) throw new TrainerCoursesOverlapping();
    return lectureTimeSlots.get(0);
  }

  public List<CourseStatus> getTrainersCoursesStatus() throws SQLException {
    List<CourseEdition> coursesHeld = getTrainersCourses();

    List<CourseStatus> coursesStatus = new ArrayList<>();
    for (CourseEdition c : coursesHeld) {
      // getting lectures progress number
      List<LectureTimeSlot> allLectures = new GetLectureTimeSlotsByCourseDatabase(dataSource.getConnection(), new LectureTimeSlot(null, null, null, c.getId(), c.getCourseName(), null)).execute();
      int sum = 0;
      for (LectureTimeSlot cur : allLectures) {
        Date beforeDate = Date.valueOf(LocalDate.now().plusDays(0));//"2022-05-12");//
        Time beforeTime = Time.valueOf(LocalTime.now());
        if (cur.getDate().compareTo(beforeDate) < 0 || (cur.getDate().compareTo(beforeDate) == 0 && cur.getStartTime().before(beforeTime))) {
          sum++;
        }
      }
      // getting trainees number
      int traineesNumber = new GetSubscriptionsByCourseDatabase(dataSource.getConnection(), new CourseEdition(c.getId(), c.getCourseName())).execute().size(), currentLessonNumber = sum, totalLessonsNumber = allLectures.size();
      coursesStatus.add(new CourseStatus(c.getCourseName(), c.getId(), traineesNumber, currentLessonNumber, totalLessonsNumber));
    }
    logger.debug(loggerClass + "CoursesStatus" + coursesStatus);
    return coursesStatus;
  }

  public List<LectureTimeSlot> getAllLessonsInRange(Date from, Date to) throws NamingException, SQLException {
    List<CourseEdition> coursesHeld = getTrainersCourses();
    List<LectureTimeSlot> allLessonInRange = new ArrayList<>();
    for (CourseEdition c : coursesHeld) {
      List<LectureTimeSlot> curCourseLTSinRange = new GetLectureTimeSlotsInRangeDatabase(dataSource.getConnection(), from, to).execute();
      logger.debug(loggerClass + "curCourseLTSinRange: " + curCourseLTSinRange);
      curCourseLTSinRange.forEach(l -> {
        if (c.equals(new CourseEdition(l.getCourseEditionId(), l.getCourseName()))) allLessonInRange.add(l);
      });
    }
    logger.debug(loggerClass + "allLessonInRange: " + allLessonInRange);
    return allLessonInRange;
  }

  /*PRIVATE METHODS*/
  private List<CourseEdition> getTrainersCourses() throws SQLException {
    List<Teaches> allTeachings = new GetTeachesByTrainerDatabase(dataSource.getConnection(), new Person(trainerEmail, null, null, null, null, null, null, null, null)).execute();
    List<CourseEdition> coursesHeld = allTeachings.stream().map(teaches -> new CourseEdition(teaches.getCourseEdition(), teaches.getCourseName())).collect(Collectors.toList());
    logger.debug(loggerClass + "Courses held by trainer " + coursesHeld);
    return coursesHeld;
  }

  private void validateSubscription(Subscription subscription, CourseEdition courseEdition) throws SQLException, TraineeNotEnrolledToTheCourse, SubscriptionNotStartedOrTerminated {
    //change error type
    if (subscription == null || courseEdition == null) throw new TraineeNotEnrolledToTheCourse();

    // retrieving all subscription to the courseEdition -> course
    List<Subscription> subscriptionsList = new GetSubscriptionsByCourseDatabase(dataSource.getConnection(), courseEdition).execute();
    logger.trace(loggerClass + "allSubscriptions:" + subscriptionsList);

    // check if is subscribed
    boolean isSubscribed = subscriptionsList.contains(subscription);
    logger.trace(loggerClass + "isSubscribed:" + isSubscribed);
    if (!isSubscribed) throw new TraineeNotEnrolledToTheCourse();


    // check dates
    LocalDate today = LocalDate.now();
    logger.trace(loggerClass + "today=" + today);

    // check if subscription is started
    LocalDate startDay = subscription.getStartDay().toLocalDate();
    logger.trace(loggerClass + "StartDate=" + startDay);

    boolean started = today.isAfter(startDay) || today.isEqual(startDay);
    logger.trace(loggerClass + "started=" + started);
    if (!started) throw new SubscriptionNotStartedOrTerminated();

    // check if subscription is currently valid
    LocalDate terminationDate = startDay.plusDays(subscription.getDuration());
    logger.trace(loggerClass + "TerminationDate=" + terminationDate);
    boolean terminated = today.isAfter(terminationDate);
    logger.trace(loggerClass + "notTerminated=" + !terminated);

    if (terminated) throw new SubscriptionNotStartedOrTerminated();
  }

}
