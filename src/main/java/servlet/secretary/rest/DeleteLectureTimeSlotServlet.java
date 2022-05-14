package servlet.secretary.rest;

import com.google.gson.Gson;
import constants.Codes;
import constants.DateTimeFormats;
import dao.lecturetimeslot.DeleteLectureTimeSlotDatabase;
import dao.lecturetimeslot.GetLectureTimeSlotByRoomDateStartTimeDatabase;
import dao.person.GetPersonByEmailDatabase;
import dao.reservation.GetAllPeopleInReservationTimeSlotDatabase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.LectureTimeSlot;
import resource.Message;
import resource.Person;
import resource.Reservation;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Rest servlet used to delete a lecture and notifying the users
 *
 * @author Riccardo Forzan
 */
public class DeleteLectureTimeSlotServlet extends AbstractServlet {

    /**
     * Handles the get request by deleting a lecture from the schedule of the gym
     * also notifying all the users about this
     *
     * @param request the request
     * @param response the response
     * @throws IOException if it was not possible to forward the request and write the response
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Try to perform the operation
        Message message = deleteLectureTimeSlot(request);

        //Output the message returned by the execution of the operation
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        //Print the message (JSON encoded) regarding the status of the operation
        out.println(new Gson().toJson(message));
        out.flush();
        out.close();
    }

    /**
     * Deletes a LectureTimeSlot from the schedule of the gym, also notifying all the users about this
     *
     * @param request HTTP requests to process (containing the parameters)
     * @return Message regarding the status of the required operation
     */
    private Message deleteLectureTimeSlot(HttpServletRequest request) {

        //Parse the parameters
        String roomName = request.getParameter("roomname");

        Date date;
        try {
            var temp = DateTimeFormats.dateFormat.parse(request.getParameter("date"));
            date = DateTimeFormats.dateConvert(temp);
        } catch (ParseException e) {
            return new Message(Codes.INVALID_DATE.getErrorMessage(), true);
        }

        LocalTime startLocalTime = LocalTime.parse(request.getParameter("starttime"),
                DateTimeFormatter.ofPattern(DateTimeFormats.timeFormatPattern));

        //Validate the date, it must have minutes equal to 0 and seconds equal to 0
        if(!isDateValid(startLocalTime)){
            return new Message(Codes.INVALID_DATE.getErrorMessage(), true);
        }

        Time startTime = Time.valueOf(startLocalTime);

        //Check you are trying to delete a lecture after the date of today
        Date actual = new Date(System.currentTimeMillis());
        if (date.before(actual)) {
            return new Message(Codes.INVALID_DATE.getErrorMessage(), true);
        }

        //Edited reservation
        Reservation toFind = new Reservation(roomName, date, startTime);

        //Get the lectureTimeSlot
        LectureTimeSlot lectureTimeSlot;
        try {
            lectureTimeSlot = new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection(), new LectureTimeSlot(roomName, date, startTime, null, null, null)).execute();
        } catch (SQLException | NamingException e) {
            return new Message(Codes.LECTURETIMESLOT_NOT_FOUND.getErrorMessage(), true);
        }

        //List of person that will be notified
        List<Person> noticeTo;

        try {

            //Get the collection of people that should be notified
            noticeTo = new GetAllPeopleInReservationTimeSlotDatabase(getDataSource().getConnection(), toFind).execute();

            //Iterate over all persons
            for (Person p : noticeTo) {
                Person person = new GetPersonByEmailDatabase(getDataSource().getConnection(), p.getEmail()).execute();
                //TODO: unlock mail sending             MailTypes.mailForCancellationLecture(person,lectureTimeSlot);
            }

            //Delete the LectureTimeSlot (all subscriptions have been removed)
            new DeleteLectureTimeSlotDatabase(getDataSource().getConnection(), lectureTimeSlot).execute();

            //Operation has been completed successfully
            return new Message(Codes.OK.getErrorMessage(), false);

        } catch (SQLException | NamingException e) {
            return new Message(Codes.INTERNAL_ERROR.getErrorMessage(), true);
        }
    }

    /**
     * Checks for the validity of a date
     * @param time  the date
     * @return  true if the date is valid, false otherwise
     */
    private boolean isDateValid(LocalTime time) {
        return time.getMinute() == 0 && time.getSecond() == 0;
    }

}
