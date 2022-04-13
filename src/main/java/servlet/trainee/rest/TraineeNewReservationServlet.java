package servlet.trainee.rest;

import constants.Constants;
import constants.ErrorCodes;
import dao.lecturetimeslot.GetLectureTimeSlotByRoomDateStartTimeDatabase;
import dao.reservation.GetReservationByAllFields;
import dao.reservation.InsertReservationDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.LectureTimeSlot;
import resource.Reservation;
import servlet.AbstractRestServlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

public class TraineeNewReservationServlet extends AbstractRestServlet {

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
        Reservation res = GSON.fromJson(input, Reservation.class);

        // Retrieve trainee email by session.
        final HttpSession session = req.getSession(false);
        if (session == null)
        {
            resp.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN);
            return;
        }
        final String email = session.getAttribute("email").toString();

        res = new Reservation(res, email);

        try{
            //Check 1: requested reservation is related to a real lecture time slot
            LectureTimeSlot lts = new LectureTimeSlot(res.getRoom(), res.getLectureDate(), res.getLectureStartTime(), 0, null, null);
            if(new GetLectureTimeSlotByRoomDateStartTimeDatabase(getConnection(),lts).execute() == null) {
                sendErrorResponse(resp, ErrorCodes.LECTURETIMESLOT_NOT_FOUND);
                return;
            }
            //Check 2: available slots for the requested reservation

            //Check 3: requested reservation is compatible with my subscriptions

            //Check 4: requested reservation data is not over the expiration of the subscription

            //Check 5: not already present a reservation made by the same user in the same slot
            if(new GetReservationByAllFields(getConnection(),res).execute() == null) {
                sendErrorResponse(resp, ErrorCodes.RESERVATION_ALREADY_PRESENT);
                return;
            }
        }catch(Throwable e){
            sendErrorResponse(resp, ErrorCodes.INTERNAL_ERROR);
        }


        try {
            Connection con = getConnection();
            new InsertReservationDatabase(con, res).execute();
            sendDataResponse(resp, res);

        } catch (Throwable th) {
            sendErrorResponse(resp, ErrorCodes.INTERNAL_ERROR);
        }
    }

}
