package servlet.trainee.rest;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import constants.Constants;
import constants.ErrorCodes;
import dao.lecturetimeslot.GetLectureTimeSlotsAvailableForUserByWeekDatabase;
import dao.person.GetStaffPeopleDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.LectureTimeSlot;
import resource.Message;
import resource.Reservation;
import servlet.AbstractRestServlet;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: aggiungere testo che descrive cosa fa la servlet.
    @author Tumiati Riccardo, Marco Alessio, Fatjon Freskina
 */

public class TraineeAvailableSlots extends AbstractRestServlet {

    private static final Pattern URI_REGEX = Pattern.compile(
            "/wa2122-gwa/trainee/rest/available/from-day/(.*)/to-day/(.*)", Pattern.DOTALL);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //checkAcceptMediaType(req, resp);

        processRequest(req,resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

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
            sendErrorResponse(resp, ErrorCodes.BAD_REQUEST);
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
            sendErrorResponse(resp, ErrorCodes.BAD_REQUEST);
            return;
        }

        // Check that fromDate is before toDate.
        if (fromDate.compareTo(toDate) > 0)
        {
            sendErrorResponse(resp, ErrorCodes.BAD_REQUEST);
            return;
        }


        try {

            // Retrieve requested data from database.
            final Connection con = getConnection();

            List<LectureTimeSlot> l_slots =
                    new GetLectureTimeSlotsAvailableForUserByWeekDatabase(con, email, fromDate, toDate).execute();

            // Send the data as response in JSON format.
            sendDataResponse(resp, l_slots);//, new TypeToken<List<LectureTimeSlot>>() {}.getType());
        }
        catch (Throwable th)
        {
            sendErrorResponse(resp, ErrorCodes.UNEXPECTED_ERROR);
            return;
        }
    }
}
