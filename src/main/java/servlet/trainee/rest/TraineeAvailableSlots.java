package servlet.trainee.rest;

import com.google.gson.Gson;
import constants.ErrorCodes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Message;
import servlet.AbstractServlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

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

        if(processRequest(req,resp).getErrorCode() == 0)
            return;
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

    private ErrorCodes processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        ErrorCodes code = ErrorCodes.OK;
        String messageJson = new Gson().toJson(new Message(code.getErrorMessage(), false));
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        resp.setStatus(code.getHTTPCode());
        out.print(messageJson);

        out.flush();
        out.close();

        return code;
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
