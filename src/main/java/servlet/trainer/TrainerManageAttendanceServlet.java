package servlet.trainer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import constants.Constants;
import constants.Codes;
import constants.exeption.CustomException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.*;
import service.GsonService;
import service.TrainerService;
import servlet.AbstractServlet;
import utils.JsonTimeDeserializer;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;

/**
 * @author Andrea Pasin
 * @author Harjot Singh
 */
public class TrainerManageAttendanceServlet extends AbstractServlet {
  private final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = "gwa.servlet.trainer.TManageAttendanceS : ";

  /* GET ALL RESERVATIONS AND SUBSCRIPTIONS */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    String trainerEmail = req.getSession(false).getAttribute("email").toString();

    try {
      req.setAttribute("trainerAttendance", new TrainerService(getDataSource(), trainerEmail).getTrainerAttendance());
    } catch (SQLException | NamingException e) {
      logger.error(loggerClass + e.getMessage());
      sendFeedback(req, Codes.INTERNAL_ERROR);
    } catch (CustomException e) {
      sendFeedback(req, e.getErrorCode());
    }
    req.getRequestDispatcher(Constants.PATH_TRAINER_MANAGE_ATTENDANCE).forward(req, res);
  }

  /* INSERT A RESERVATION FROM SUBSCRIPTION! */
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    String trainerEmail = req.getSession(false).getAttribute("email").toString();
    String action = req.getParameter("action");
    boolean success = false;

    try {
      TrainerService trainerService = new TrainerService(getDataSource(), trainerEmail);
      GsonService gsonService = new GsonService();

      if (action != null && action.equals("doDelete")) { //DELETE
        // GET RESERVATION FROM REQUEST
        String param = req.getParameter("reservation").substring(req.getParameter("reservation").indexOf("{"));
        Reservation reservation = gsonService.getReservationFromString(param);

        // PERFORM ACTION
        success = trainerService.removePresenceFromCurrentLectureTimeSlot(reservation);
      } else {
        // GET SUBSCRIPTION FROM REQUEST
        String param = req.getParameter("subscription").substring(req.getParameter("subscription").indexOf("{"));
        Subscription subscription = gsonService.getSubscriptionFromString(param);

        // PERFORM ACTION
        success = trainerService.addPresenceToCurrentLectureTimeSlot(subscription);
      }
    } catch (SQLException | NamingException | NullPointerException e) {
      e.printStackTrace();
      sendFeedback(req, Codes.INTERNAL_ERROR);
    } catch (CustomException e) {
      sendFeedback(req, e.getErrorCode());
    }

    if (success) {
      sendFeedback(req, Codes.OK, false);
      res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_TRAINER_MANAGE_ATTENDANCE);
    } else {
      req.getRequestDispatcher(Constants.PATH_TRAINER_MANAGE_ATTENDANCE).forward(req, res);
    }

  }

  /* PRIVATE METHODS */
  private void sendFeedback(HttpServletRequest req, Codes error) {
    sendFeedback(req, error, true);
  }

  private void sendFeedback(HttpServletRequest req, Codes error, boolean isError) {
    String messageJson = new Gson().toJson(new Message(error.getErrorMessage(), isError));
    logger.error(loggerClass + messageJson);
    req.setAttribute("error", messageJson);
  }
}
