package servlet.trainee.rest;

import com.google.gson.Gson;
import constants.Codes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Message;
import resource.Reservation;
import servlet.AbstractServlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class TraineeNewReservationServlet extends AbstractServlet {
    private static final String JSON_MEDIA_TYPE = "application/json";
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";
    private static final String ALL_MEDIA_TYPE = "*/*";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Codes code = Codes.GET_OPERATION_NOT_SUPPORTED;
        setUpErrorMessage(resp,code);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Codes code = Codes.HEAD_OPERATION_NOT_SUPPORTED;
        setUpErrorMessage(resp,code);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(checkMethodMediaType(req,resp).getErrorCode() != 0)
            return;

        processRequest(req,resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Codes code = Codes.PUT_OPERATION_NOT_SUPPORTED;
        setUpErrorMessage(resp,code);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Codes code = Codes.DELETE_OPERATION_NOT_SUPPORTED;
        setUpErrorMessage(resp,code);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Codes code = Codes.OPTIONS_OPERATION_NOT_SUPPORTED;
        setUpErrorMessage(resp,code);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Codes code = Codes.TRACE_OPERATION_NOT_SUPPORTED;
        setUpErrorMessage(resp,code);
    }

    private Codes checkMethodMediaType(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        final String accept = req.getHeader("Accept");
        final String contentType = req.getContentType();
        Codes code = Codes.OK;

        if(accept == null) {
            code = Codes.ACCEPT_MISSING;
            setUpErrorMessage(resp,code);
            return code;
        }

        if(!accept.contains(JSON_MEDIA_TYPE) && !accept.equals(ALL_MEDIA_TYPE)) {
            code = Codes.MEDIA_TYPE_NOT_SUPPORTED;
            setUpErrorMessage(resp,code);
            return code;
        }

        if(contentType == null){
            code = Codes.CONTENTTYPE_MISSING;
            setUpErrorMessage(resp,code);
            return code;
        }

        if(!contentType.contains(JSON_MEDIA_TYPE)){
            code = Codes.CONTENTTYPE_UNSUPPORTED;
            setUpErrorMessage(resp,code);
            return code;
        }

        return code;
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        Reader input = new InputStreamReader(req.getInputStream(), StandardCharsets.UTF_8);
        final Reservation res = new Gson().fromJson(input, Reservation.class);

        System.out.println(res);
    }

    private void setUpErrorMessage(HttpServletResponse resp, Codes code) throws IOException{
        String messageJson = new Gson().toJson(new Message(code.getErrorMessage(), true));
        PrintWriter out = resp.getWriter();
        resp.setContentType(JSON_UTF_8_MEDIA_TYPE);
        resp.setCharacterEncoding("utf-8");
        resp.setStatus(code.getHTTPCode());
        out.print(messageJson);

        out.flush();
        out.close();
    }
}
