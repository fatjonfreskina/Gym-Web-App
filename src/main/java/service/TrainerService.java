package service;

import constants.exeption.*;
import dao.lecturetimeslot.GetLectureTimeSlotByCourseEditionIdNowDatabase;
import dao.reservation.DeleteReservation;
import dao.reservation.GetListReservationByLectureDatabase;
import dao.reservation.InsertReservationDatabase;
import dao.room.GetRoomByNameDatabase;
import dao.subscription.GetSubscriptionsByCourseDatabase;
import dao.subscription.GetValidSubscriptionsByCourseDatabase;
import dao.teaches.GetTeachesByTrainerDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.*;
import resource.rest.TrainerAttendance;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
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
  }

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

  public boolean addPresenceToCurrentLectureTimeSlot(Subscription subscription) throws NamingException, SQLException, ReservationAlreadyPresent, RoomAlreadyFull, TraineeNotEnrolledToTheCourse, TrainerCoursesOverlapping, TrainerNoCourseHeld, TrainerNoCourseHeldNow {
    LectureTimeSlot lectureHeldNow = getTrainersCurrentLectureTimeSlot(trainerEmail);
    CourseEdition courseEdition = new CourseEdition(lectureHeldNow.getCourseEditionId(), lectureHeldNow.getCourseName());
    // check if the trainee can be added, has a subscription to this course
    logger.trace(loggerClass + "checking isValidSubscription(" + subscription + "," + courseEdition + ")");
    if (isValidSubscription(subscription, courseEdition)) {
      logger.trace(loggerClass + "isValidSubscription");
      //DataSource dataSource = getDataSource();
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
        return true;
      } else {
        throw new RoomAlreadyFull();
      }
    } else {
      throw new TraineeNotEnrolledToTheCourse();
    }
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

  public TrainerAttendance getTrainerAttendance() throws SQLException, TrainerCoursesOverlapping, TrainerNoCourseHeld, TrainerNoCourseHeldNow {
    LectureTimeSlot lectureHeldNow = getTrainersCurrentLectureTimeSlot(trainerEmail);

    logger.debug(loggerClass + "Current Lecture: " + lectureHeldNow);

    //Get the list of reservation for the lecture now and their corresponding trainees
    List<Reservation> reservations = new GetListReservationByLectureDatabase(dataSource.getConnection(), lectureHeldNow).execute();

    //Get the subscriptions for the course held now and their corresponding trainees
    List<Subscription> subscriptions = new GetValidSubscriptionsByCourseDatabase(dataSource.getConnection(), new CourseEdition(lectureHeldNow.getCourseEditionId(), lectureHeldNow.getCourseName())).execute();
    //TODO reservations.stream().forEach(reservation ->{reservation.getTrainee();});
    for (Reservation r : reservations) {
      String trainee = r.getTrainee();
      subscriptions = subscriptions.stream().filter(subscription -> !subscription.getTrainee().equals(trainee)).collect(Collectors.toList());
    }
    return new TrainerAttendance(lectureHeldNow, reservations, subscriptions);
  }

  private boolean isValidSubscription(Subscription subscription, CourseEdition courseEdition) throws NamingException, SQLException {
    if (subscription == null || courseEdition == null) return false;
    List<Subscription> subscriptionsList = new GetSubscriptionsByCourseDatabase(dataSource.getConnection(), courseEdition).execute();
    logger.trace(loggerClass + "allSubscriptions:" + subscriptionsList);
    boolean isSubscribed = subscriptionsList.contains(subscription);
    logger.trace(loggerClass + "isSubscribed:" + isSubscribed);
    return isSubscribed;
  }

}
