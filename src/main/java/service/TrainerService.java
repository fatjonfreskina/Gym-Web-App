package service;

import constants.exceptions.*;
import dao.lecturetimeslot.GetLectureTimeSlotByCourseEditionIdNowDatabase;
import dao.lecturetimeslot.GetLectureTimeSlotsByCourseDatabase;
import dao.lecturetimeslot.GetLectureTimeSlotsInRangeDatabase;
import dao.lecturetimeslot.GetLectureTimeSlotsInRangeSubstitutionDatabase;
import dao.reservation.DeleteReservationDatabase;
import dao.reservation.GetListReservationByLectureDatabase;
import dao.reservation.InsertReservationDatabase;
import dao.room.GetRoomByNameDatabase;
import dao.subscription.GetSubscriptionsByCourseDatabase;
import dao.teaches.GetTeachesByTrainerDatabase;
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
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 * Service handling different operations performed by a trainer
 *
 * @author Harjot Singh
 */
public class TrainerService {

    private final DataSource dataSource;
    private final String trainerEmail;

    /**
     * The constructor
     * @param dataSource  the datasource from which data is retrieved
     * @param trainerEmail  the trainer's email
     */
    public TrainerService(DataSource dataSource, String trainerEmail) {
        this.dataSource = dataSource;
        this.trainerEmail = trainerEmail;
    }

    /**
     * Removes an attendance from the current lecture a trainer is holding
     * @param reservation  the reservation
     * @return  true if the reservation has been removed, false otherwise
     * @throws SQLException if there is an error concerning the sql database
     * @throws ReservationNotFound if the reservation is not found
     * @throws TrainerCoursesOverlapping if the trainer courses are overlapping
     * @throws TrainerNoCourseHeld if the trainer does not hold any course
     * @throws TrainerNoCourseHeldNow if there is no course to be held now
     * @throws ConflictBetweenReservationAndLectureTimeSlotValues if there are conflicts
     */
    public boolean removePresenceFromCurrentLectureTimeSlot(Reservation reservation) throws SQLException, ReservationNotFound, TrainerCoursesOverlapping, TrainerNoCourseHeld, TrainerNoCourseHeldNow, ConflictBetweenReservationAndLectureTimeSlotValues {

        LectureTimeSlot curLesson = getTrainersCurrentLectureTimeSlot(trainerEmail);//checking
        //delete iif reservation is for curLesson
        if (curLesson.getStartTime().equals(reservation.getLectureStartTime()) &&
                curLesson.getDate().equals(reservation.getLectureDate()) &&
                curLesson.getRoomName().equals(reservation.getRoom())) {
            Reservation deleted = new DeleteReservationDatabase(dataSource.getConnection(), reservation).execute();
            if (deleted == null) {
                throw new ReservationNotFound();
            }
        } else {
            throw new ConflictBetweenReservationAndLectureTimeSlotValues();
        }
        return true;
    }

    /**
     * Adds an attendance to the current lecture a trainer is holding
     * @param subscription  the subscription of a trainee
     * @return  true if the reservation has been added, false otherwise
     * @throws SQLException if there is an error concerning the sql database
     * @throws TrainerCoursesOverlapping if the trainer courses are overlapping
     * @throws TrainerNoCourseHeld if the trainer does not hold any course
     * @throws TrainerNoCourseHeldNow if there is no course to be held now
     * @throws NamingException if there is an error regarding the datasource
     * @throws ReservationAlreadyPresent if the reservation is already present
     * @throws RoomAlreadyFull if the room is already full
     * @throws TraineeNotEnrolledToTheCourse if the trainee is not enrolled to the course
     * @throws SubscriptionNotStartedOrTerminated no subscription started
     */
    public boolean addPresenceToCurrentLectureTimeSlot(Subscription subscription) throws NamingException, SQLException, ReservationAlreadyPresent, RoomAlreadyFull, TraineeNotEnrolledToTheCourse, TrainerCoursesOverlapping, TrainerNoCourseHeld, TrainerNoCourseHeldNow, SubscriptionNotStartedOrTerminated {
        LectureTimeSlot lectureHeldNow = getTrainersCurrentLectureTimeSlot(trainerEmail);
        CourseEdition courseEdition = new CourseEdition(lectureHeldNow.getCourseEditionId(), lectureHeldNow.getCourseName());
        // check if the trainee can be added, has a subscription to this course

        validateSubscription(subscription, courseEdition);//propagating the errors

        List<Reservation> reservations = new GetListReservationByLectureDatabase(dataSource.getConnection(), lectureHeldNow).execute();

        Room room = new GetRoomByNameDatabase(dataSource.getConnection(), new Room(lectureHeldNow.getRoomName())).execute();

        Reservation reservationToAdd = new Reservation(subscription.getTrainee(), room.getName(), lectureHeldNow.getDate(), lectureHeldNow.getStartTime());
        if (reservations.contains(reservationToAdd)) {
            throw new ReservationAlreadyPresent();
        }

        //Check if there are enough spots available for the given room
        int availability = room.getSlots() - reservations.size();
        if (availability >= 1) {
            new InsertReservationDatabase(dataSource.getConnection(), reservationToAdd).execute();
        } else {
            throw new RoomAlreadyFull();
        }
        return true; //is always true or it will throw an error
    }

    /**
     * Gets the attendances and subscriptions
     * of different trainees according to the lecture the trainer is holding
     * @return  the trainees attending the trainer's lecture or subscribed to the trainer's course
     * @throws SQLException if there is an error concerning the sql database
     * @throws TrainerCoursesOverlapping if the trainer courses are overlapping
     * @throws TrainerNoCourseHeld if the trainer does not hold any course
     * @throws TrainerNoCourseHeldNow if there is no course to be held now
     * @throws NoSubscriptionToTheCourse if there is no subscription to the course
     */
    public TrainerAttendance getTrainerAttendance() throws SQLException, TrainerCoursesOverlapping, TrainerNoCourseHeld, TrainerNoCourseHeldNow, NoSubscriptionToTheCourse {
        LectureTimeSlot lectureHeldNow = getTrainersCurrentLectureTimeSlot(trainerEmail);


        //Get the list of reservation for the lecture now and their corresponding trainees
        List<Reservation> reservations = new GetListReservationByLectureDatabase(dataSource.getConnection(), lectureHeldNow).execute();

        //TODO change back to GetValidSubscriptionByCourseDatabase, problems:
        // 1) does not give the valid ones (date problems)
        // 2) not all invalid are removed
        // CUR sol: giving back all subscription but adding only those are valid
        //Get the subscriptions for the course held now and their corresponding trainees
        List<Subscription> subscriptions = new GetSubscriptionsByCourseDatabase(dataSource.getConnection(),
                new CourseEdition(lectureHeldNow.getCourseEditionId(), lectureHeldNow.getCourseName())).execute();

        if (reservations.isEmpty() && subscriptions.isEmpty()) {
            throw new NoSubscriptionToTheCourse();
        }
        for (Reservation r : reservations) {
            String trainee = r.getTrainee();
            subscriptions = subscriptions.stream().filter(subscription -> !subscription.getTrainee().equals(trainee)).collect(Collectors.toList());
        }
        return new TrainerAttendance(lectureHeldNow, reservations, subscriptions);
    }

    /**
     * Gets a trainer current lecture
     * @param trainerEmail  the email of a trainer
     * @return  the lecture a trainer has to hold at the current moment
     * @throws SQLException if there is an error concerning the sql database
     * @throws TrainerCoursesOverlapping if the trainer courses are overlapping
     * @throws TrainerNoCourseHeld if the trainer does not hold any course
     * @throws TrainerNoCourseHeldNow if there is no course to be held now
     */
    public LectureTimeSlot getTrainersCurrentLectureTimeSlot(String trainerEmail) throws SQLException, TrainerNoCourseHeldNow, TrainerCoursesOverlapping, TrainerNoCourseHeld {
        List<Teaches> teaches = new GetTeachesByTrainerDatabase(dataSource.getConnection(), new Person(trainerEmail)).execute();

        if (teaches.isEmpty()) throw new TrainerNoCourseHeld();

        //Get the lecture that should be held now
        List<LectureTimeSlot> lectureTimeSlots = new ArrayList<>();
        for (Teaches t : teaches) {
            LectureTimeSlot l = new GetLectureTimeSlotByCourseEditionIdNowDatabase(dataSource.getConnection(),
                    new LectureTimeSlot(t.getCourseEdition(), t.getCourseName())).execute();
            if (l != null) lectureTimeSlots.add(l);
        }

        if (lectureTimeSlots.isEmpty()) throw new TrainerNoCourseHeldNow();

        //TODO INTERNAL ERROR? SECRETARY SHOULD NOT ADD IT IN FIRST PLACE
        if (lectureTimeSlots.size() > 1) throw new TrainerCoursesOverlapping();
        LectureTimeSlot curr = lectureTimeSlots.get(0);
        return curr;
    }

    /**
     * Gets the statuses of the trainer's courses
     * @return  the statuses of the trainer's courses
     * @throws SQLException if there is an error concerning the sql database
     */
    public List<CourseStatus> getTrainersCoursesStatus() throws SQLException {
        List<CourseEdition> coursesHeld = getTrainersCourses();

        List<CourseStatus> coursesStatus = new ArrayList<>();
        for (CourseEdition c : coursesHeld) {
            // getting lectures progress number
            List<LectureTimeSlot> allLectures = new GetLectureTimeSlotsByCourseDatabase(dataSource.getConnection(),
                    new LectureTimeSlot(null, null, null, c.getId(), c.getCourseName(), null)).execute();
            int sum = 0;
            for (LectureTimeSlot cur : allLectures) {
                Date beforeDate = Date.valueOf(LocalDate.now().plusDays(0));//"2022-05-12");//
                Time beforeTime = Time.valueOf(LocalTime.now());
                if (cur.getDate().compareTo(beforeDate) < 0 || (cur.getDate().compareTo(beforeDate) == 0 && cur.getStartTime().before(beforeTime))) {
                    sum++;
                }
            }
            // getting trainees number
            int traineesNumber = new GetSubscriptionsByCourseDatabase(dataSource.getConnection(),
                    new CourseEdition(c.getId(), c.getCourseName())).execute().size(), currentLessonNumber = sum, totalLessonsNumber = allLectures.size();
            coursesStatus.add(new CourseStatus(c.getCourseName(), c.getId(), traineesNumber, currentLessonNumber, totalLessonsNumber));
        }
        return coursesStatus;
    }

    /**
     * Gets all the lectures a trainer has to hold in a given range
     * @param from  the starting date
     * @param to  the ending date
     * @return  all the lectures a trainer has to hold
     * @throws NamingException if there is an issue concerning the datasource
     * @throws SQLException if there is an error concerning the sql database
     */
    public List<LectureTimeSlot> getAllLessonsInRange(Date from, Date to) throws NamingException, SQLException {
        List<CourseEdition> coursesHeld = getTrainersCourses();
        List<LectureTimeSlot> allLessonInRange = new ArrayList<>(); //trainerEmail
        for (CourseEdition c : coursesHeld) {
            List<LectureTimeSlot> curCourseLTSinRange = new GetLectureTimeSlotsInRangeDatabase(dataSource.getConnection(), from, to).execute();
            curCourseLTSinRange.forEach(l -> {
                if (c.equals(new CourseEdition(l.getCourseEditionId(), l.getCourseName())))
                    allLessonInRange.add(l);
            });
        }
        //rimuovi tutte le lezioni in cui c'è un substitute in un corso in cui insegno
        allLessonInRange.removeIf(lectureTimeSlot -> lectureTimeSlot.getSubstitution() != null);

        //per range di date per il trainer tutte le lts di sostituzione
        allLessonInRange.addAll(new GetLectureTimeSlotsInRangeSubstitutionDatabase(dataSource.getConnection(),from,to,new Person(trainerEmail)).execute());

        return allLessonInRange;
    }

    /*PRIVATE METHODS*/
    private List<CourseEdition> getTrainersCourses() throws SQLException {
        List<Teaches> allTeachings = new GetTeachesByTrainerDatabase(dataSource.getConnection(),
                new Person(trainerEmail, null, null, null, null, null, null, null, null)).execute();
        List<CourseEdition> coursesHeld = allTeachings.stream().map(teaches -> new CourseEdition(teaches.getCourseEdition(), teaches.getCourseName())).collect(Collectors.toList());
        return coursesHeld;
    }

    private void validateSubscription(Subscription subscription, CourseEdition courseEdition) throws SQLException, TraineeNotEnrolledToTheCourse, SubscriptionNotStartedOrTerminated {
        //change error type
        if (subscription == null || courseEdition == null) throw new TraineeNotEnrolledToTheCourse();

        // retrieving all subscription to the courseEdition -> course
        List<Subscription> subscriptionsList = new GetSubscriptionsByCourseDatabase(dataSource.getConnection(), courseEdition).execute();

        // check if is subscribed
        boolean isSubscribed = subscriptionsList.contains(subscription);
        if (!isSubscribed) throw new TraineeNotEnrolledToTheCourse();


        // check dates
        LocalDate today = LocalDate.now();

        // check if subscription is started
        LocalDate startDay = subscription.getStartDay().toLocalDate();

        boolean started = today.isAfter(startDay) || today.isEqual(startDay);
        if (!started) throw new SubscriptionNotStartedOrTerminated();

        // check if subscription is currently valid
        LocalDate terminationDate = startDay.plusDays(subscription.getDuration());
        boolean terminated = today.isAfter(terminationDate);

        if (terminated) throw new SubscriptionNotStartedOrTerminated();
    }

}
