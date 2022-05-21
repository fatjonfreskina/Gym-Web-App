package servlet.trainee.rest;

import constants.Codes;
import dao.reservation.DeleteReservationDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.Reservation;
import servlet.AbstractRestServlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Rest servlet used to delete a reservation made by a trainee
 *
 * @author Tumiati Riccardo
 * @author Marco Alessio
 * @author Fatjon Freskina
 */
public class TraineeDeleteReservationServlet extends AbstractRestServlet
{
    private static final Pattern URI_REGEX = Pattern.compile(
            "/wa2122-gwa/trainee/rest/reservation/room/(.*)/date/(.*)/starttime/(.*)", Pattern.DOTALL);


    /**
     * Handles the delete request by deleting a reservation made by a trainee
     *
     * @param req the request
     * @param resp  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Auxiliary method to handle the delete request by deleting a reservation made by a trainee
     *
     * @param req the request
     * @param resp  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // Retrieve input data from the requested URI.
        final String uri = req.getRequestURI();
        final Matcher matcher = URI_REGEX.matcher(uri);

        if ((!matcher.find()) || (matcher.groupCount() != 3)) {
            sendErrorResponse(resp, Codes.BAD_REQUEST);
            return;
        }

        final String room = matcher.group(1);
        final String dateString = matcher.group(2);
        final String timeString = matcher.group(3);

        Date date;
        Time time;

        try {
            date = Date.valueOf(dateString);
            time = Time.valueOf(timeString);
        } catch (Throwable th) {
            sendErrorResponse(resp, Codes.BAD_REQUEST);
            return;
        }

        final HttpSession session = req.getSession(false);
        final String email = session.getAttribute("email").toString();

        if (isDateAndTimeValid(date, time)) {
            try {
                final Reservation reservation = new Reservation(email, room, date, time);
                new DeleteReservationDatabase(getConnection(), reservation).execute();
                sendDataResponse(resp, reservation);

            } catch (Throwable e) {
                sendErrorResponse(resp, Codes.UNEXPECTED_ERROR);
                return;
            }
        } else {
            //Trying to delete future(/present) reservation
            sendErrorResponse(resp, Codes.INVALID_FIELDS);
            return;
        }
    }

    /**
     * Checks if a reservation is valid according to its date and time
     *
     * @param reservationDate the date of the reservation
     * @param reservationTime  the time of the reservation
     */
    private boolean isDateAndTimeValid(Date reservationDate, Time reservationTime)
    {
        long millis = System.currentTimeMillis();
        Date today = new Date(millis);
        Time now = new Time(millis);

        return today.compareTo(reservationDate) < 0 ||
                (today.compareTo(reservationDate) == 0 && now.compareTo(reservationTime) <= 0);
    }
}