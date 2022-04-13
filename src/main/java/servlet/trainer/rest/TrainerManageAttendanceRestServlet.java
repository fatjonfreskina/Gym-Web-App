package servlet.trainer.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Time;

import javax.naming.NamingException;

import com.google.gson.*;

import constants.exeption.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.ErrorCodes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Message;
import resource.Reservation;
import resource.Subscription;
import resource.rest.TrainerAttendance;
import service.TrainerService;
import servlet.AbstractServlet;
import utils.JsonTimeDeserializer;
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

    //Retrieve the courses that Trainer teaches
    try {
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      PrintWriter out = res.getWriter();
      //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
      Gson gson = new GsonBuilder()
          .setDateFormat("yyyy-MM-dd")
          .registerTypeAdapter(Time.class, new JsonTimeSerializer())
          .create();
      out.print(gson.toJson(new TrainerService(getDataSource(), trainerEmail).getTrainerAttendance(), TrainerAttendance.class));
    } catch (SQLException | NamingException e) {
      logger.error(loggerClass + e.getMessage());
      sendFeedback(res, ErrorCodes.INTERNAL_ERROR);
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

      Gson gson = new GsonBuilder()
          .setDateFormat("yyyy-MM-dd")
          .registerTypeAdapter(Time.class, new JsonTimeDeserializer())
          .create();
      Subscription subscription = gson.fromJson(req.getReader(), Subscription.class);
      logger.debug(loggerClass + "Subscription from body of post: " + subscription);
      // PERFORM ACTION
      success = trainerService.addPresenceToCurrentLectureTimeSlot(subscription);
    } catch (SQLException | NamingException | NullPointerException e) {
      e.printStackTrace();
      sendFeedback(res, ErrorCodes.INTERNAL_ERROR);
    } catch (CustomException e) {
      sendFeedback(res, e.getErrorCode());
    } catch (JsonParseException e) {
      e.printStackTrace();
      logger.error(loggerClass + e.getMessage());
      sendFeedback(res, ErrorCodes.CONTENTTYPE_UNSUPPORTED);
    }
    //Return positive feedback
    if (success) sendFeedback(res, ErrorCodes.OK, false);
  }

  /* DELETE A RESERVATION */
  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    String trainerEmail = req.getSession(false).getAttribute("email").toString();
    boolean success = false;

    try {
      TrainerService trainerService = new TrainerService(getDataSource(), trainerEmail);

      Gson gson = new GsonBuilder()
          .setDateFormat("yyyy-MM-dd")
          .registerTypeAdapter(Time.class, new JsonTimeDeserializer())
          .create();
      Reservation reservation = gson.fromJson(req.getReader(), Reservation.class);
      logger.debug(loggerClass + "Reservation from post body:" + reservation);

      // PERFORM ACTION
      success = trainerService.removePresenceFromCurrentLectureTimeSlot(reservation);
    } catch (SQLException | NamingException e) {
      logger.error(loggerClass + e.getMessage());
      sendFeedback(res, ErrorCodes.INTERNAL_ERROR);
    } catch (CustomException e) {
      logger.error(loggerClass + e.getMessage());
      sendFeedback(res, e.getErrorCode());
    } catch (JsonParseException e) {
      logger.error(loggerClass + e.getMessage());
      sendFeedback(res, ErrorCodes.CONTENTTYPE_UNSUPPORTED);
    }
    //Return positive feedback
    if (success) sendFeedback(res, ErrorCodes.OK, false);
  }

  /* TODO FOR OTHER METHODS THROW NOT IMPLEMENTED */

  /* PRIVATE METHODS */

  private void sendFeedback(HttpServletResponse res, ErrorCodes error) throws IOException {
    sendFeedback(res, error, true);
  }

  private void sendFeedback(HttpServletResponse res, ErrorCodes error, boolean isError) throws IOException {
    String messageJson = new Gson().toJson(new Message(error.getErrorMessage(), isError));
    PrintWriter out = res.getWriter();
    res.setStatus(error.getHTTPCode());
    res.setContentType("application/json");
    res.setCharacterEncoding("utf-8");
    out.print(messageJson);
  }
}
