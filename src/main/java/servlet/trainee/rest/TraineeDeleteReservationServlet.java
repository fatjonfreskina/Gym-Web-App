package servlet.trainee.rest;

import constants.ErrorCodes;
import dao.reservation.DeleteReservation;
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
 * TODO: aggiungere testo che descrive cosa fa la servlet.
 * @author Tumiati Riccardo, Marco Alessio, Fatjon Freskina
 */


public class TraineeDeleteReservationServlet extends AbstractRestServlet {
    // trainee/rest/reservation/room/{room}/date/{date}/starttime/{time}
    private static final Pattern URI_REGEX = Pattern.compile(
            "/wa2122-gwa/trainee/rest/reservation/room/(.*)/date/(.*)/starttime/(.*)", Pattern.DOTALL);

    //TODO: for debugging purposes only, must be canceled
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // Retrieve input data from the requested URI.
        final String uri = req.getRequestURI();
        final Matcher matcher = URI_REGEX.matcher(uri);

        if ((!matcher.find()) || (matcher.groupCount() != 3)) {
            sendErrorResponse(resp, ErrorCodes.BAD_REQUEST);
            return;
        }

        final String room = matcher.group(1);
        final Date date = Date.valueOf(matcher.group(2));
        final Time time = Time.valueOf(matcher.group(3));
        HttpSession session = req.getSession(false);
        final String email = session.getAttribute("email").toString();

        if (isDateAndTimeValid(date, time)) {
            try {
                final Reservation reservation = new Reservation(email, room, date, time);
                new DeleteReservation(getDataSource().getConnection(), reservation).execute();
                sendDataResponse(resp, reservation);

            } catch (Throwable e) {
                sendErrorResponse(resp, ErrorCodes.UNEXPECTED_ERROR);
            }
        } else {
            //Trying to delete future(/present) reservation
            sendErrorResponse(resp, ErrorCodes.INVALID_FIELDS);
        }
    }

    private boolean isDateAndTimeValid(Date reservationDate, Time reservationTime)
    {
        long millis = System.currentTimeMillis();
        Date today = new Date(millis);
        Time now = new Time(millis);

        return today.compareTo(reservationDate) < 0 ||
                (today.compareTo(reservationDate) == 0 && now.compareTo(reservationTime) <= 0);
    }
}