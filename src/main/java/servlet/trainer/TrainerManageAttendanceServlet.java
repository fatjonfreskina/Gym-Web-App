package servlet.trainer;

import com.google.gson.Gson;
import constants.Constants;
import constants.Codes;
import constants.exceptions.CustomException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.*;
import service.GsonService;
import service.TrainerService;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet handling requests for managing the attendances by a trainer
 *
 * @author Andrea Pasin
 * @author Harjot Singh
 */
public class TrainerManageAttendanceServlet extends AbstractServlet {
  private final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = this.getClass().getCanonicalName() + ": ";


  /**
   * Handles the get request by retrieving the reservations for a lecture and all the trainees
   * subscribed to the course taught in that lecture
   *
   * @param req  the request
   * @param res  the response
   * @throws ServletException
   * @throws IOException
   */
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

  /**
   * Handles the post request by analyzing the action performed by the trainer and, based on it,
   * deletes a reservation or inserts a reservation from a trainee subscribed to the given course
   *
   * @param req  the request
   * @param res  the response
   * @throws ServletException
   * @throws IOException
   */
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

  /**
   * Auxiliary method to sends a successful message or an error message
   * @param req  the http request
   * @param error  the error or successful message to send
   */
  private void sendFeedback(HttpServletRequest req, Codes error) {
    sendFeedback(req, error, true);
  }

  /**
   * Auxiliary method to sends a successful message or an error message
   * @param req  the http request
   * @param error  the error or successful message to send
   * @param isError  true if it is an error, false if it is a successful message
   */
  private void sendFeedback(HttpServletRequest req, Codes error, boolean isError) {
    //String messageJson = new Gson().toJson(new Message(error.getErrorMessage(), isError));
    //logger.error(loggerClass + messageJson);
    //Message message =
    req.setAttribute("message", new Message(error.getErrorMessage(),isError));
  }
}
