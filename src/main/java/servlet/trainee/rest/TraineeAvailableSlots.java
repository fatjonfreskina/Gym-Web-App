package servlet.trainee.rest;

import com.google.gson.Gson;
import constants.ErrorCodes;
import dao.lecturetimeslot.GetLectureTimeSlotsAvailableForUserByWeekDatabase;
import dao.person.GetStaffPeopleDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.LectureTimeSlot;
import resource.Message;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/*
    @author Tumiati Riccardo, Marco Alessio, Fatjon Freskina
 */

public class TraineeAvailableSlots extends AbstractServlet {
    private static final String JSON_MEDIA_TYPE = "application/json";
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";
    private static final String ALL_MEDIA_TYPE = "*/*";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(JSON_UTF_8_MEDIA_TYPE);

        if(checkMethodMediaType(req,resp).getErrorCode() != 0)
            return;

        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ErrorCodes code = ErrorCodes.METHOD_NOT_ALLOWED;
        setUpErrorMessage(resp,code);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ErrorCodes code = ErrorCodes.METHOD_NOT_ALLOWED;
        setUpErrorMessage(resp,code);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ErrorCodes code = ErrorCodes.METHOD_NOT_ALLOWED;
        setUpErrorMessage(resp,code);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ErrorCodes code = ErrorCodes.METHOD_NOT_ALLOWED;
        setUpErrorMessage(resp,code);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ErrorCodes code = ErrorCodes.METHOD_NOT_ALLOWED;
        setUpErrorMessage(resp,code);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ErrorCodes code = ErrorCodes.METHOD_NOT_ALLOWED;
        setUpErrorMessage(resp,code);
    }

    private ErrorCodes checkMethodMediaType(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        final String accept = req.getHeader("Accept");
        ErrorCodes code = ErrorCodes.OK;

        if(accept == null) {
            code = ErrorCodes.ACCEPT_MISSING;
            setUpErrorMessage(resp,code);
            return code;
        }

        if(!accept.contains(JSON_MEDIA_TYPE) && !accept.equals(ALL_MEDIA_TYPE)) {
            code = ErrorCodes.MEDIA_TYPE_NOT_SUPPORTED;
            setUpErrorMessage(resp,code);
            return code;
        }

        return code;
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        final String path = req.getRequestURI();
        final String[] path_splitted = path.split("\\/");

        int i = 0;
        while(!path_splitted[i].equals("trainee") && i<path_splitted.length){ i++; }

        //Note : 7 is the number of parameters required for a compatible URI request
        if(i != path_splitted.length - 7){
            ErrorCodes code = ErrorCodes.BAD_REQUEST;
            setUpErrorMessage(resp,code);
            return;
        }

        if(     !path_splitted[i+1].equals("rest") ||
                !path_splitted[i+2].equals("available") ||
                !path_splitted[i+3].equals("from-day") ||
                !path_splitted[i+5].equals("to-day")
        ){
            ErrorCodes code = ErrorCodes.BAD_REQUEST;
            setUpErrorMessage(resp,code);
            return;
        }

        PrintWriter out = resp.getWriter();
        final Date fromday = Date.valueOf(path_splitted[i+4]);
        final Date today = Date.valueOf(path_splitted[i+6]);
        HttpSession session = req.getSession(false);
        final String email = session.getAttribute("email").toString();
        List<LectureTimeSlot> l_slots;

        try {
            l_slots = new GetLectureTimeSlotsAvailableForUserByWeekDatabase(getDataSource().getConnection(),email,fromday,today).execute();
            ErrorCodes code = ErrorCodes.OK;
            String messageJson = new Gson().toJson(l_slots);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            resp.setStatus(code.getHTTPCode());
            out.print(messageJson);

            out.flush();
            out.close();
        } catch (Throwable e) {
            ErrorCodes code = ErrorCodes.UNEXPECTED_ERROR;
            setUpErrorMessage(resp,code);
        }
    }

    private void setUpErrorMessage(HttpServletResponse resp, ErrorCodes code) throws IOException{
        String messageJson = new Gson().toJson(new Message(code.getErrorMessage(), true));
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        resp.setStatus(code.getHTTPCode());
        out.print(messageJson);

        out.flush();
        out.close();
    }
}
