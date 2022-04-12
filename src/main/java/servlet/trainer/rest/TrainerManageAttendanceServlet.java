package servlet.trainer.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.google.gson.Gson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.ErrorCodes;
import constants.exeption.TrainerCoursesOverlapping;
import constants.exeption.TrainerNoCourseHeld;
import constants.exeption.TrainerNoCourseHeldNow;
import dao.lecturetimeslot.GetLectureTimeSlotByCourseEditionIdNowDatabase;
import dao.reservation.DeleteReservation;
import dao.reservation.GetListReservationByLectureDatabase;
import dao.reservation.InsertReservationDatabase;
import dao.room.GetRoomByNameDatabase;
import dao.subscription.GetSubscriptionsByCourseDatabase;
import dao.subscription.GetValidSubscriptionsByCourseDatabase;
import dao.teaches.GetTeachesByTrainerDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.CourseEdition;
import resource.LectureTimeSlot;
import resource.Message;
import resource.Person;
import resource.Reservation;
import resource.Room;
import resource.Subscription;
import resource.Teaches;
import resource.rest.TrainerAttendance;
import servlet.AbstractServlet;

/**
 * @author Andrea Pasin
 * @author Harjot Singh
 */
public class TrainerManageAttendanceServlet extends AbstractServlet {
  private final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = "gwa.servlet.trainer.rest.TManageAttendanceS : ";

  /* GET ALL RESERVATIONS AND SUBSCRIPTIONS */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    String trainerEmail = req.getSession(false).getAttribute("email").toString();

    //Retrieve the courses that Trainer teaches
    try {
      LectureTimeSlot lectureHeldNow = getCurrentLectureTimeSlot(trainerEmail);
      logger.debug(loggerClass + "Current Lecture: " + lectureHeldNow);
      
      //Get the list of reservation for the lecture now and their corresponding trainees
      List<Reservation> reservations = new GetListReservationByLectureDatabase(getDataSource().getConnection(), lectureHeldNow).execute();

      //Get the subscriptions for the course held now and their corresponding trainees
      List<Subscription> subscriptions = new GetValidSubscriptionsByCourseDatabase(getDataSource().getConnection(), new CourseEdition(lectureHeldNow.getCourseEditionId(), lectureHeldNow.getCourseName())).execute();

      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      PrintWriter out = res.getWriter();
      out.print(new Gson().toJson(new TrainerAttendance(lectureHeldNow, reservations, subscriptions), TrainerAttendance.class));
    } catch (SQLException | NamingException e) {
      logger.error(loggerClass + e.getMessage());
      sendFeedback(res, ErrorCodes.INTERNAL_ERROR);
    } catch (TrainerNoCourseHeld e) {
      sendFeedback(res, ErrorCodes.NO_COURSES_TAUGHT);
    } catch (TrainerNoCourseHeldNow e) {
      sendFeedback(res, ErrorCodes.NO_COURSES_HELD_NOW);
    } catch (TrainerCoursesOverlapping e) {
      sendFeedback(res, ErrorCodes.OVERLAPPING);
    }
  }

  /* INSERT A RESERVATION FROM SUBSCRIPTION! */
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    String trainerEmail = req.getSession(false).getAttribute("email").toString();
    try {
      LectureTimeSlot lectureHeldNow = getCurrentLectureTimeSlot(trainerEmail);

      Subscription subscription = new Gson().fromJson(req.getReader(), Subscription.class);
      logger.debug(loggerClass+ "Subscription from body of post: " + subscription);

      CourseEdition courseEdition = new CourseEdition(lectureHeldNow.getCourseEditionId(), lectureHeldNow.getCourseName());
      // check if the trainee can be added, has a subscription to this couse
      if (isValidSubscritpion(subscription, courseEdition)) {
        List<Reservation> reservations = new GetListReservationByLectureDatabase(getDataSource().getConnection(), lectureHeldNow).execute();
        logger.debug(loggerClass + "Reservations: " + reservations);

        Room room = new GetRoomByNameDatabase(getDataSource().getConnection(), new Room(lectureHeldNow.getRoomName())).execute();

        Reservation reservationToAdd = new Reservation(subscription.getTrainee(), room.getName(), lectureHeldNow.getDate(), lectureHeldNow.getStartTime());
        if (reservations.contains(reservationToAdd)) {
          logger.warn(loggerClass + "Already present");
          sendFeedback(res, ErrorCodes.RESERVATION_ALREADY_PRESENT);
          return;
        }

        //Check if there are enough spots available for the given room
        int availability = room.getSlots() - reservations.size();
        logger.debug("RoomSlots - reservations=" + room.getSlots() + " - " + reservations.size() + " = " + availability);
        if (availability >= 1) {
          new InsertReservationDatabase(getDataSource().getConnection(), reservationToAdd).execute();
          //Return positive feedback
          sendFeedback(res, ErrorCodes.OK, false);
        } else {
          sendFeedback(res, ErrorCodes.ROOM_ALREADY_FULL);
        }

      } else {
        sendFeedback(res, ErrorCodes.TRAINEE_NOT_ENROLLED_TO_THE_COURSE);
      }
    } catch (SQLException | NamingException | NullPointerException e) {
      e.printStackTrace();
      sendFeedback(res, ErrorCodes.INTERNAL_ERROR);
    } catch (TrainerCoursesOverlapping e) {
      sendFeedback(res, ErrorCodes.OVERLAPPING);
    } catch (TrainerNoCourseHeld e) {
      sendFeedback(res, ErrorCodes.NO_COURSES_TAUGHT);
    } catch (TrainerNoCourseHeldNow e) {
      sendFeedback(res, ErrorCodes.NO_COURSES_HELD_NOW);
    }
  }

  /* DELETE A RESERVATION */
  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    Reservation reservation = new Gson().fromJson(req.getReader(), Reservation.class);
    logger.debug("SH - Subscription from post body:" + reservation);

    try {
      Reservation deleted = new DeleteReservation(getDataSource().getConnection(), reservation).execute();
      if (deleted == null) {
        logger.debug("NOT DELETED");
      }
    } catch (SQLException | NamingException e) {
      //e.printStackTrace();
      sendFeedback(res, ErrorCodes.INTERNAL_ERROR);
      return;
    }
    sendFeedback(res, ErrorCodes.OK, false);
  }

  /* TODO FOR OTHER METHODS THROW NOT IMPLEMENTED */
  private void sendFeedback(HttpServletResponse res, ErrorCodes error, boolean isError) throws IOException {
    String messageJson = new Gson().toJson(new Message(error.getErrorMessage(), isError));
    PrintWriter out = res.getWriter();
    res.setContentType("application/json");
    res.setCharacterEncoding("utf-8");
    out.print(messageJson);
  }

  private void sendFeedback(HttpServletResponse res, ErrorCodes error) throws IOException {
    sendFeedback(res, error, true);
  }
  
  private boolean isValidSubscritpion(Subscription subscription, CourseEdition courseEdition) throws NamingException, SQLException {
    if (subscription == null || courseEdition == null) return false;
    List<Subscription> subscriptionsList = new GetSubscriptionsByCourseDatabase(getDataSource().getConnection(), courseEdition).execute();
    return subscriptionsList.contains(subscription);
  }

  private LectureTimeSlot getCurrentLectureTimeSlot(String trainerEmail) throws SQLException, NamingException, TrainerNoCourseHeldNow, TrainerCoursesOverlapping, TrainerNoCourseHeld {
    List<Teaches> teaches = new GetTeachesByTrainerDatabase(getDataSource().getConnection(), new Person(trainerEmail)).execute();

    if (teaches.isEmpty()) throw new TrainerNoCourseHeld();

    logger.debug(loggerClass + teaches);
    //Get the lecture that should be held now
    List<LectureTimeSlot> lectureTimeSlots = new ArrayList<>();
    for (Teaches t : teaches) {
      LectureTimeSlot l = new GetLectureTimeSlotByCourseEditionIdNowDatabase(getDataSource().getConnection(), new LectureTimeSlot(t.getCourseEdition(), t.getCourseName())).execute();
      if (l != null) lectureTimeSlots.add(l);
    }

    if (lectureTimeSlots.isEmpty()) throw new TrainerNoCourseHeldNow();

    //TODO INTERNAL ERROR? SECRETARY SHOULD NOT ADD IT IN FIRST PLACE
    if (lectureTimeSlots.size() > 1) throw new TrainerCoursesOverlapping();
    return lectureTimeSlots.get(0);
  }
}
