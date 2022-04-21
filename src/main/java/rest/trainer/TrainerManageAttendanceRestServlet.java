package rest.trainer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.naming.NamingException;

import constants.exceptions.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.Codes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Reservation;
import resource.Subscription;
import service.GsonService;
import service.TrainerService;
import servlet.AbstractRestServlet;

/**
 * @author Andrea Pasin
 * @author Harjot Singh
 */
public class TrainerManageAttendanceRestServlet extends AbstractRestServlet {
  private final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = this.getClass().getCanonicalName() + ": ";

  /* GET ALL RESERVATIONS AND SUBSCRIPTIONS */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    String trainerEmail = req.getSession(false).getAttribute("email").toString();

    try {
      sendDataResponse(res, new TrainerService(getDataSource(), trainerEmail).getTrainerAttendance());
    } catch (SQLException | NamingException e) {
      logger.error(loggerClass + e.getMessage());
      sendErrorResponse(res, Codes.INTERNAL_ERROR);
    } catch (CustomException e) {
      logger.error(loggerClass + e.getErrorCode());
      sendErrorResponse(res, e.getErrorCode());
    }
  }

  /* INSERT A RESERVATION FROM SUBSCRIPTION! */
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    String trainerEmail = req.getSession(false).getAttribute("email").toString();
    Codes checkType = checkContentTypeMediaType(req);
    if (checkType.getHTTPCode() != Codes.OK.getHTTPCode()) sendErrorResponse(res, checkType);
    else {
      boolean success = false;
      try {
        checkContentTypeMediaType(req);
        TrainerService trainerService = new TrainerService(getDataSource(), trainerEmail);
        GsonService gsonService = new GsonService();

        // GET RESERVATION FROM REQUEST
        String param = req.getReader().lines().collect(Collectors.joining());
        Subscription subscription = gsonService.getSubscriptionFromString(param);

        // PERFORM ACTION
        success = trainerService.addPresenceToCurrentLectureTimeSlot(subscription);
      } catch (SQLException | NamingException e) {
        e.printStackTrace();
        sendErrorResponse(res, Codes.INTERNAL_ERROR);
      } catch (CustomException e) {
        logger.error(loggerClass + e.getErrorCode());
        sendErrorResponse(res, e.getErrorCode());
      }
      //Return positive feedback
      if (success) sendErrorResponse(res, Codes.OK);
    }
  }

  /* DELETE A RESERVATION */
  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    String trainerEmail = req.getSession(false).getAttribute("email").toString();
    Codes checkType = checkContentTypeMediaType(req);
    if (checkType.getHTTPCode() != Codes.OK.getHTTPCode()) sendErrorResponse(res, checkType);
    else {
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
        sendErrorResponse(res, Codes.INTERNAL_ERROR);
      } catch (CustomException e) {
        logger.error(loggerClass + e.getErrorCode());
        sendErrorResponse(res, e.getErrorCode());
      }
      //Return positive feedback
      if (success) sendErrorResponse(res, Codes.OK);
    }
  }
}
