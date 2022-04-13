package servlet.trainee.rest;

import constants.ErrorCodes;
import dao.reservation.InsertReservationDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Reservation;
import servlet.AbstractRestServlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

public class TraineeNewReservationServlet extends AbstractRestServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ErrorCodes code = checkAcceptMediaType(req);
        if (code != ErrorCodes.OK)
        {
            sendErrorResponse(resp, code);
            return;
        }

        code = checkContentTypeMediaType(req);
        if (code != ErrorCodes.OK)
        {
            sendErrorResponse(resp, code);
            return;
        }

        processRequest(req,resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        Reader input = new InputStreamReader(req.getInputStream(), StandardCharsets.UTF_8);
        final Reservation res = GSON.fromJson(input, Reservation.class);

        //TODO: input sanitization

        try {
            Connection con = getConnection();
            new InsertReservationDatabase(con, res).execute();
            sendDataResponse(resp, res);

        } catch (Throwable th) {
            sendErrorResponse(resp, ErrorCodes.INTERNAL_ERROR);
        }
    }

}
