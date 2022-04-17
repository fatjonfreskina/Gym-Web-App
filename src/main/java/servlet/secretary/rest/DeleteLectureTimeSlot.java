package servlet.secretary.rest;

import com.google.gson.Gson;
import constants.Codes;
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
import servlet.AbstractRestServlet;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;

/**
 * @author Riccardo Forzan
 */
public class DeleteLectureTimeSlot extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

        DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("MMM dd, yyyy")).toFormatter();
        LocalDate localDate = LocalDate.parse(request.getParameter("date"), formatter);
        Date date = new Date(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());

        LocalTime localTime = LocalTime.parse(request.getParameter("starttime"), DateTimeFormatter.ofPattern("hh:mm:ss a"));
        Time startTime = Time.valueOf(localTime);

        //Check you are trying to delete a lecture after the date of today
        Date actual = new Date(System.currentTimeMillis());
        if(date.after(actual)){
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


}
