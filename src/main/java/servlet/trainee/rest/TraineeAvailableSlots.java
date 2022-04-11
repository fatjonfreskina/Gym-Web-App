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
        final OutputStream out = resp.getOutputStream();

        if(checkMethodMediaType(req,resp).getErrorCode() != 0)
            return;


        //super.service(req, resp);
    }

    private ErrorCodes checkMethodMediaType(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        final String method = req.getMethod();
        //WATCH OUT THIS
        final String content = req.getContentType();
        final String accept = req.getHeader("Accept");
        ErrorCodes code = ErrorCodes.OK;

        if(accept == null) {
            code = ErrorCodes.ACCEPT_MISSING;
            String messageJson = new Gson().toJson(new Message(code.getErrorMessage(), true));
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            resp.setStatus(code.getHTTPCode());
            out.print(messageJson);
            return code;
        }

        if(!accept.contains(JSON_MEDIA_TYPE) && !accept.equals(ALL_MEDIA_TYPE)) {
            code = ErrorCodes.MEDIA_TYPE_NOT_SUPPORTED;
            String messageJson = new Gson().toJson(new Message(code.getErrorMessage(), true));
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            resp.setStatus(code.getHTTPCode());
            out.print(messageJson);
            return code;
        }

        return code;
    }

    private void setUpErrorMessage(HttpServletResponse resp, ErrorCodes code){
        
    }
}
