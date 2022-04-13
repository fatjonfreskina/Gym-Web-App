package servlet.trainee.rest;

import com.google.common.reflect.TypeToken;
import constants.Constants;
import constants.ErrorCodes;
import dao.reservation.GetListReservationByEmailAndDateDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.Reservation;
import servlet.AbstractRestServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Return a list of reservations of a user within a given date interval.
 *
 * @author Marco Alessio
 */
public class GetReservationsServlet extends AbstractRestServlet
{
    private static final Pattern URI_REGEX = Pattern.compile(
            "/wa2122-gwa/trainee/rest/reservation/from-day/(.*)/to-day/(.*)", Pattern.DOTALL);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        // Retrieve trainee email by session.
        HttpSession session = req.getSession(false);
        if (session == null)
        {
            res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN);
            return;
        }

        final String email = session.getAttribute("email").toString();

        // Retrieve input data from the requested URI.
        final String uri = req.getRequestURI();
        final Matcher matcher = URI_REGEX.matcher(uri);

        if ((!matcher.find()) || (matcher.groupCount() != 2))
        {
            sendErrorResponse(res, ErrorCodes.BAD_REQUEST);
            return;
        }

        final String fromString = matcher.group(1);
        final String toString = matcher.group(2);

        // TODO: input sanitization.
        Date fromDate;
        Date toDate;

        try
        {
            fromDate = Date.valueOf(fromString);
            toDate = Date.valueOf(toString);
        }
        catch (Throwable th)
        {
            sendErrorResponse(res, ErrorCodes.BAD_REQUEST);
            return;
        }

        // Check that fromDate is before toDate.
        if (fromDate.compareTo(toDate) > 0)
        {
            sendErrorResponse(res, ErrorCodes.BAD_REQUEST);
            return;
        }


        // Check that the request accepts JSON format.
        //checkAcceptMediaType(req, res); //To enable browser requests (no JSON accepted) to be executed.


        try
        {
            // Retrieve requested data from database.
            final Connection con = getConnection();
            List<Reservation> reservations =
                    new GetListReservationByEmailAndDateDatabase(con, email, fromDate, toDate).execute();

            // Send the data as response in JSON format.
            sendDataResponse(res, reservations, new TypeToken<List<Reservation>>() {}.getType());
        }
        catch (Throwable th)
        {
            sendErrorResponse(res, ErrorCodes.INTERNAL_ERROR);
            return;
        }
    }
}