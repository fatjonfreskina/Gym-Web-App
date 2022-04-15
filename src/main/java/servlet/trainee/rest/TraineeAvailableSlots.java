package servlet.trainee.rest;

import constants.Constants;
import constants.Codes;
import dao.lecturetimeslot.GetLectureTimeSlotsAvailableForUserByWeekDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.rest.LectureTimeSlotOccupation;
import servlet.AbstractRestServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: aggiungere testo che descrive cosa fa la servlet.
    @author Tumiati Riccardo, Marco Alessio, Fatjon Freskina
 */

public class TraineeAvailableSlots extends AbstractRestServlet {

    private static final Pattern URI_REGEX = Pattern.compile(
            "/wa2122-gwa/trainee/rest/available/from-date/(.*)/to-date/(.*)", Pattern.DOTALL);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check that the request accepts JSON format.
        //final ErrorCodes code = checkAcceptMediaType(req);
        final Codes code = Codes.OK;  //To enable browser requests (no JSON accepted) to be executed.
        if (code != Codes.OK)
        {
            sendErrorResponse(resp, code);
            return;
        }

        // Retrieve trainee email by session.
        final HttpSession session = req.getSession(false);
        if (session == null)
        {
            resp.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN);
            return;
        }

        final String email = session.getAttribute("email").toString();

        // Retrieve input data from the requested URI.
        final String uri = req.getRequestURI();
        final Matcher matcher = URI_REGEX.matcher(uri);

        if ((!matcher.find()) || (matcher.groupCount() != 2))
        {
            sendErrorResponse(resp, Codes.BAD_REQUEST);
            return;
        }

        final String fromString = matcher.group(1);
        final String toString = matcher.group(2);

        Date fromDate;
        Date toDate;

        try
        {
            fromDate = Date.valueOf(fromString);
            toDate = Date.valueOf(toString);
        }
        catch (Throwable th)
        {
            sendErrorResponse(resp, Codes.BAD_REQUEST);
            return;
        }

        // Check that fromDate is before toDate.
        if (fromDate.compareTo(toDate) > 0)
        {
            sendErrorResponse(resp, Codes.BAD_REQUEST);
            return;
        }


        try {
            // Retrieve requested data from database.
            final Connection con = getConnection();

            List<LectureTimeSlotOccupation> l_slots =
                    new GetLectureTimeSlotsAvailableForUserByWeekDatabase(con, email, fromDate, toDate).execute();

            // Send the data as response in JSON format.
            sendDataResponse(resp, l_slots);
        }
        catch (Throwable th)
        {
            sendErrorResponse(resp, Codes.UNEXPECTED_ERROR);
            return;
        }
    }
}
