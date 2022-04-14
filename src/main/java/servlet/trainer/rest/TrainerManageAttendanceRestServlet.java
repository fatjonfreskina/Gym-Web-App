package servlet.trainer.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Time;
import java.util.stream.Collectors;

import javax.naming.NamingException;

import com.google.gson.*;

import constants.exeption.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.Codes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Message;
import resource.Reservation;
import resource.Subscription;
import resource.rest.TrainerAttendance;
import service.GsonService;
import service.TrainerService;
import servlet.AbstractServlet;
import utils.JsonTimeSerializer;

/**
 * @author Andrea Pasin
 * @author Harjot Singh
 */
public class TrainerManageAttendanceRestServlet extends AbstractServlet {
  private final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = "gwa.servlet.trainer.rest.TraManAttRestS : ";

  /* GET ALL RESERVATIONS AND SUBSCRIPTIONS */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    String trainerEmail = req.getSession(false).getAttribute("email").toString();

    try {
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      PrintWriter out = res.getWriter();
      Gson gson = new GsonBuilder()
          .setDateFormat("yyyy-MM-dd")
          .registerTypeAdapter(Time.class, new JsonTimeSerializer())
          .create();
      out.print(gson.toJson(new TrainerService(getDataSource(), trainerEmail).getTrainerAttendance(), TrainerAttendance.class));
    } catch (SQLException | NamingException e) {
      logger.error(loggerClass + e.getMessage());
      sendFeedback(res, Codes.INTERNAL_ERROR);
    } catch (CustomException e) {
      logger.error(loggerClass + e.getMessage());
      sendFeedback(res, e.getErrorCode());
    }
  }

  /* INSERT A RESERVATION FROM SUBSCRIPTION! */
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    String trainerEmail = req.getSession(false).getAttribute("email").toString();
    boolean success = false;
    try {
      TrainerService trainerService = new TrainerService(getDataSource(), trainerEmail);
      GsonService gsonService = new GsonService();

      // GET RESERVATION FROM REQUEST
      String param = req.getReader().lines().collect(Collectors.joining());
      Subscription subscription = gsonService.getSubscriptionFromString(param);

      // PERFORM ACTION
      success = trainerService.addPresenceToCurrentLectureTimeSlot(subscription);
    } catch (SQLException | NamingException e) {
      e.printStackTrace();
      sendFeedback(res, Codes.INTERNAL_ERROR);
    } catch (CustomException e) {
      sendFeedback(res, e.getErrorCode());
    }
    //Return positive feedback
    if (success) sendFeedback(res, Codes.OK, false);
  }

  /* DELETE A RESERVATION */
  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    String trainerEmail = req.getSession(false).getAttribute("email").toString();
    boolean success = false;

    try {
      TrainerService trainerService = new TrainerService(getDataSource(), trainerEmail);
      GsonService gsonService = new GsonService();

      // GET RESERVATION FROM REQUEST
      String param = req.getReader().lines().collect(Collectors.joining());
      Reservation reservation = gsonService.getReservationFromString(param);

      // PERFORM ACTION
      success = trainerService.removePresenceFromCurrentLectureTimeSlot(reservation);
    } catch (SQLException | NamingException e) {
      logger.error(loggerClass + e.getMessage());
      sendFeedback(res, Codes.INTERNAL_ERROR);
    } catch (CustomException e) {
      logger.error(loggerClass + e.getMessage());
      sendFeedback(res, e.getErrorCode());
    }
    //Return positive feedback
    if (success) sendFeedback(res, Codes.OK, false);
  }

  /* TODO FOR OTHER METHODS THROW NOT IMPLEMENTED */

  /* PRIVATE METHODS */
  private void sendFeedback(HttpServletResponse res, Codes error) throws IOException {
    sendFeedback(res, error, true);
  }

  private void sendFeedback(HttpServletResponse res, Codes error, boolean isError) throws IOException {
    String messageJson = new Gson().toJson(new Message(error.getErrorMessage(), isError));
    PrintWriter out = res.getWriter();
    res.setStatus(error.getHTTPCode());
    res.setContentType("application/json");
    res.setCharacterEncoding("utf-8");
    out.print(messageJson);
  }
}
