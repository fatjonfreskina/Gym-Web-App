package servlet.trainee.rest;

import com.google.gson.JsonParseException;
import constants.Constants;
import constants.Codes;
import dao.lecturetimeslot.GetLectureTimeSlotByRoomDateStartTimeDatabase;
import dao.medicalcertificate.GetLastMedicalCertfiticateByPersonDatabase;
import dao.reservation.GetAvailableSlotsReservationDatabase;
import dao.reservation.GetReservationByAllFieldsDatabase;
import dao.reservation.InsertReservationDatabase;
import dao.subscription.GetSubscriptionExpirationByLTSDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.LectureTimeSlot;
import resource.MedicalCertificate;
import resource.Person;
import resource.Reservation;
import servlet.AbstractRestServlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;


/**
 * Rest servlet used to add a reservation
 *
 * @author Tumiati Riccardo
 * @author Marco Alessio
 * @author Fatjon Freskina
 */
public class TraineeNewReservationServlet extends AbstractRestServlet {

    /**
     * Handles the post request by adding a reservation
     *
     * @param req the request
     * @param resp  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Codes code = checkAcceptMediaType(req);
        if (code != Codes.OK)
        {
            sendErrorResponse(resp, code);
            return;
        }

        code = checkContentTypeMediaType(req);
        if (code != Codes.OK)
        {
            sendErrorResponse(resp, code);
            return;
        }

        processRequest(req,resp);
    }

    /**
     * Auxiliary method to handle the post request by adding a reservation
     *
     * @param req the request
     * @param resp  the response
     * @throws IOException if it was not possible to forward the request and write the response
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Reader input = new InputStreamReader(req.getInputStream(), StandardCharsets.UTF_8);
        Reservation res;
        try {
            res = GSON.fromJson(input, Reservation.class);
        } catch(JsonParseException e) {
            sendErrorResponse(resp, Codes.WRONG_JSON_RESERVATION);
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
        res = new Reservation(res, email);

        //Check 0: date and time for the reservations greater than current date and time
        if(!isDateAndTimeValid(res.getLectureDate(), res.getLectureStartTime())) {
            sendErrorResponse(resp, Codes.INVALID_DATE);
            return;
        }

        try {
            //Check 1: requested reservation is related to a real lecture time slot
            LectureTimeSlot lts = new LectureTimeSlot(res.getRoom(), res.getLectureDate(), res.getLectureStartTime(), 0, null, null);
            if (new GetLectureTimeSlotByRoomDateStartTimeDatabase(getConnection(), lts).execute() == null) {
                sendErrorResponse(resp, Codes.LECTURETIMESLOT_NOT_FOUND);
                return;
            }

            //Check 2: requested reservation is compatible with my subscriptions and is not over its expiration of the subscription
            Date expiration = new GetSubscriptionExpirationByLTSDatabase(getConnection(),lts,email).execute();
            if (expiration == null) {
                sendErrorResponse(resp, Codes.TRAINEE_NOT_ENROLLED_TO_THE_COURSE);
                return;
            }
            if (expiration.compareTo(res.getLectureDate()) <= 0) {
                sendErrorResponse(resp, Codes.SUBSCRIPION_EXPIRED_BEFORE);
                return;
            }

            //Check 3: available slots for the requested reservation
            if (new GetAvailableSlotsReservationDatabase(getConnection(),res).execute() == 0) {
                sendErrorResponse(resp, Codes.ROOM_ALREADY_FULL);
                return;
            }

            //Check 4: not already present a reservation made by the same user in the same slot
            if (new GetReservationByAllFieldsDatabase(getConnection(),res).execute() != null) {
                sendErrorResponse(resp, Codes.RESERVATION_ALREADY_PRESENT);
                return;
            }

            //Check 5: user's medical certificate not valid
            MedicalCertificate medicalCertificate = new GetLastMedicalCertfiticateByPersonDatabase(getDataSource().getConnection(),
                    new Person(email,null,null,null,null,null,null,null,null)).execute();
            if(medicalCertificate == null || medicalCertificate.getExpirationDate().toLocalDate().isBefore(LocalDate.now())){
                sendErrorResponse(resp, Codes.BAD_REQUEST);
                return;
            }
        } catch(Throwable th) {
            sendErrorResponse(resp, Codes.INTERNAL_ERROR);
            return;
        }


        try {
            final Connection conn = getConnection();
            new InsertReservationDatabase(conn, res).execute();
            sendDataResponse(resp, res);

        } catch (Throwable th) {
            sendErrorResponse(resp, Codes.INTERNAL_ERROR);
        }
    }

    /**
     * Checks if a reservation is valid according to its date and time
     *
     * @param reservationDate the date of the reservation
     * @param reservationTime  the time of the reservation
     */
    private boolean isDateAndTimeValid(Date reservationDate, Time reservationTime)
    {
        long millis = System.currentTimeMillis();
        Date today = new Date(millis);
        Time now = new Time(millis);

        return today.compareTo(reservationDate) < 0 ||
                (today.compareTo(reservationDate) == 0 && now.compareTo(reservationTime) <= 0);
    }

}
