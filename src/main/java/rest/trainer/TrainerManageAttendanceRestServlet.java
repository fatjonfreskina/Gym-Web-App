package rest.trainer;

import constants.Codes;
import constants.exceptions.CustomException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Reservation;
import resource.Subscription;
import service.GsonService;
import service.TrainerService;
import servlet.AbstractRestServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * Rest servlet used to manage the attendances by a trainer
 *
 * @author Andrea Pasin
 * @author Harjot Singh
 */
public class TrainerManageAttendanceRestServlet extends AbstractRestServlet {

    /**
     * Handles the get request by retrieving all reservations and subscriptions
     *
     * @param req the request
     * @param res the response
     * @throws ServletException if either the request or response are not of the expected types or any other error occurs
     * @throws IOException      if some error occurs while writing the response
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String trainerEmail = req.getSession(false).getAttribute("email").toString();

        try {
            sendDataResponse(res, new TrainerService(getDataSource(), trainerEmail).getTrainerAttendance());
        } catch (SQLException | NamingException e) {
            sendErrorResponse(res, Codes.INTERNAL_ERROR);
        } catch (CustomException e) {
            sendErrorResponse(res, e.getErrorCode());
        }
    }

    /**
     * Handles the post request by adding a reservation for a user subscribed to the
     * course held by a trainer
     *
     * @param req the request
     * @param res the response
     * @throws ServletException if either the request or response are not of the expected types or any other error occurs
     * @throws IOException      if some error occurs while writing the response
     */
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
                sendErrorResponse(res, e.getErrorCode());
            }
            //Return positive feedback
            if (success) sendErrorResponse(res, Codes.OK);
        }
    }

    /**
     * Handles the delete request by deleting a reservation for a given lecture
     *
     * @param req the request
     * @param res the response
     * @throws ServletException if either the request or response are not of the expected types or any other error occurs
     * @throws IOException      if some error occurs while writing the response
     */
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
                sendErrorResponse(res, Codes.INTERNAL_ERROR);
            } catch (CustomException e) {
                sendErrorResponse(res, e.getErrorCode());
            }
            //Return positive feedback
            if (success) sendErrorResponse(res, Codes.OK);
        }
    }
}
