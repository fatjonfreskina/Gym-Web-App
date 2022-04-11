package servlet.trainee.rest;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import constants.Constants;
import constants.ErrorCodes;
import dao.reservation.GetListReservationByEmailAndDateDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.Message;
import resource.Reservation;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetReservationsServlet extends AbstractServlet
{
    private static final Pattern URI_REGEX = Pattern.compile(
            "/wa2122-gwa/trainee/rest/reservation/from-day/(.*)/to-day/(.*)", Pattern.DOTALL);
    private static final Gson GSON = new GsonBuilder()/*.setPrettyPrinting()*/.create();

    private static final String ACCEPT_HEADER = "accept";

    private static final String UTF8_ENCODING = "utf-8";

    private static final String JSON_MEDIA_TYPE = "application/json";
    private static final String ALL_MEDIA_TYPE = "*/*";

    private ErrorCodes checkContentTypeHeaderForJSON(HttpServletRequest req)
    {
        final String contentType = req.getContentType();

        if (contentType == null)
            return ErrorCodes.CONTENTTYPE_MISSING;

        if (!contentType.contains(JSON_MEDIA_TYPE))
            return ErrorCodes.MEDIA_TYPE_NOT_SUPPORTED;

        return ErrorCodes.OK;
    }


    private ErrorCodes checkAcceptHeaderForJSON(HttpServletRequest req)
    {
        final String accept = req.getHeader(ACCEPT_HEADER);

        if (accept == null)
            return ErrorCodes.ACCEPT_MISSING;

        if ((!accept.contains(JSON_MEDIA_TYPE)) && (!accept.equals(ALL_MEDIA_TYPE)))
            return ErrorCodes.MEDIA_TYPE_NOT_SUPPORTED;

        return ErrorCodes.OK;
    }

    private void sendError(HttpServletResponse res, ErrorCodes code) throws IOException
    {
        final PrintWriter out = res.getWriter();

        res.setStatus(code.getHTTPCode());
        out.println(GSON.toJson(new Message(code.getErrorMessage(), true)));

        out.flush();
        out.close();
    }

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
            sendError(res, ErrorCodes.BAD_REQUEST);
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
        catch (Exception e)
        {
            sendError(res, ErrorCodes.BAD_REQUEST);
            return;
        }

        // Set headers of the response.
        res.setContentType(JSON_MEDIA_TYPE);
        res.setCharacterEncoding(UTF8_ENCODING);


        // Check that the request accepts JSON format.
        //ErrorCodes code = checkAcceptHeaderForJSON(req);
        ErrorCodes code = ErrorCodes.OK; //To enable browser requests (no JSON accepted) to be executed.
        if (code != ErrorCodes.OK)
        {
            sendError(res, code);
            return;
        }

        // Retrieve requested data from database.
        List<Reservation> reservations;

        try
        {
            final Connection con = getDataSource().getConnection();

            reservations = new GetListReservationByEmailAndDateDatabase(con, email, fromDate, toDate).execute();
        }
        catch (NamingException | SQLException e)
        {
            sendError(res, ErrorCodes.INTERNAL_ERROR);
            return;
        }

        // Send requested data to the user.
        res.setStatus(HttpServletResponse.SC_OK);

        final PrintWriter out = res.getWriter();
        out.println(GSON.toJson(reservations, new TypeToken<List<Reservation>>() {}.getType()));

        out.flush();
        out.close();
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException
    {
        // Set headers of the response.
        res.setContentType(JSON_MEDIA_TYPE);
        res.setCharacterEncoding(UTF8_ENCODING);

        sendError(res, ErrorCodes.DELETE_OPERATION_NOT_SUPPORTED);
    }

    @Override
    public void doHead(HttpServletRequest req, HttpServletResponse res) throws IOException
    {
        // Set headers of the response.
        res.setContentType(JSON_MEDIA_TYPE);
        res.setCharacterEncoding(UTF8_ENCODING);

        sendError(res, ErrorCodes.HEAD_OPERATION_NOT_SUPPORTED);
    }

    @Override
    public void doOptions(HttpServletRequest req, HttpServletResponse res) throws IOException
    {
        // Set headers of the response.
        res.setContentType(JSON_MEDIA_TYPE);
        res.setCharacterEncoding(UTF8_ENCODING);

        sendError(res, ErrorCodes.OPTIONS_OPERATION_NOT_SUPPORTED);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
    {
        // Set headers of the response.
        res.setContentType(JSON_MEDIA_TYPE);
        res.setCharacterEncoding(UTF8_ENCODING);

        sendError(res, ErrorCodes.POST_OPERATION_NOT_SUPPORTED);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException
    {
        // Set headers of the response.
        res.setContentType(JSON_MEDIA_TYPE);
        res.setCharacterEncoding(UTF8_ENCODING);

        sendError(res, ErrorCodes.PUT_OPERATION_NOT_SUPPORTED);
    }

    @Override
    public void doTrace(HttpServletRequest req, HttpServletResponse res) throws IOException
    {
        // Set headers of the response.
        res.setContentType(JSON_MEDIA_TYPE);
        res.setCharacterEncoding(UTF8_ENCODING);

        sendError(res, ErrorCodes.TRACE_OPERATION_NOT_SUPPORTED);
    }
}