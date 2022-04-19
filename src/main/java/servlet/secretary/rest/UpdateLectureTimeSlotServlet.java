package servlet.secretary.rest;

import com.google.gson.Gson;
import constants.Codes;
import dao.lecturetimeslot.GetLectureTimeSlotByRoomDateStartTimeDatabase;
import dao.lecturetimeslot.UpdateLectureTimeSlotDatabase;
import dao.person.GetPersonByEmailDatabase;
import dao.reservation.GetAllPeopleInReservationTimeSlotDatabase;
import jakarta.servlet.ServletException;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

/**
 * @author Riccardo Forzan
 */
public class UpdateLectureTimeSlotServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Try to perform the operation
        Message message = performUpdate(request);

        //Output the message returned by the execution of the operation
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        //Print the message (JSON encoded) regarding the status of the operation
        out.println(new Gson().toJson(message));
        out.flush();
        out.close();

    }

    private Message performUpdate(HttpServletRequest request) {

        DateTimeFormatter formatter;

        //Parse the parameters
        String oldRoomName = request.getParameter("oldRoomName");
        formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("MMM dd, yyyy")).toFormatter();
        LocalDate oldLocalDate = LocalDate.parse(request.getParameter("oldDate"), formatter);
        Date oldDate = new Date(Date.from(oldLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
        LocalTime oldLocalTime = LocalTime.parse(request.getParameter("oldStartTime"), DateTimeFormatter.ofPattern("hh:mm:ss a"));
        Time oldStartTime = Time.valueOf(oldLocalTime);

        String newRoomName = request.getParameter("newRoomName");
        formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        LocalDate newLocalDate = LocalDate.parse(request.getParameter("newDate"), formatter);
        Date newDate = new Date(Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
        LocalTime newLocalTime = LocalTime.parse(request.getParameter("newStartTime"), DateTimeFormatter.ofPattern("hh:mm:ss a"));
        Time newStartTime = Time.valueOf(newLocalTime);

        Date actual = new Date(System.currentTimeMillis());
        //Check the date of the lesson you are trying to move is after the current date in millis
        if(oldDate.before(actual)){
            return new Message(Codes.INVALID_DATE.getErrorMessage(), true);
        }
        //Check the new date of the lesson you are trying to move is after the current date in millis
        if(newDate.after(actual)){
            return new Message(Codes.INVALID_DATE.getErrorMessage(), true);
        }

        //Check the lecture starts in the range 8:00 AM to 20:00 PM
        LocalTime openingTime = LocalTime.parse("8:00:00 AM", DateTimeFormatter.ofPattern("hh:mm:ss a"));
        LocalTime closingTime = LocalTime.parse("20:00:00 PM", DateTimeFormatter.ofPattern("hh:mm:ss a"));
        if(newLocalTime.isAfter(openingTime) && newLocalTime.isBefore(closingTime)){
            return new Message(Codes.INVALID_DATE.getErrorMessage(), true);
        }

        //TODO: Check the new Date and Time in which you want to move the lecture does not do any overlap

        LectureTimeSlot lectureTimeSlot;

        //Try to update the LectureTimeSlot
        try {
            //Get the LectureTimeSlot
            lectureTimeSlot = new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection(),
                    new LectureTimeSlot(oldRoomName, oldDate, oldStartTime, null, null, null))
                    .execute();
            //Create a new LectureTimeSlot updating the substitution field
            LectureTimeSlot updatedLectureTimeSlot = new LectureTimeSlot(newRoomName, newDate, newStartTime,
                    lectureTimeSlot.getCourseEditionId(), lectureTimeSlot.getCourseName(),
                    lectureTimeSlot.getSubstitution());
            //Update the LectureTimeSlot
            new UpdateLectureTimeSlotDatabase(getDataSource().getConnection(), lectureTimeSlot, updatedLectureTimeSlot)
                    .execute();
        } catch (SQLException | NamingException e) {
            return new Message(Codes.INTERNAL_ERROR.getErrorMessage(), true);
        }

        //Notify all the subscribed people
        Reservation reservation = new Reservation(newRoomName, newDate, newStartTime);

        //Try to notify all the users
        try {
            List<Person> personList = new GetAllPeopleInReservationTimeSlotDatabase(getDataSource().getConnection(), reservation).execute();
            //Send a mail to each user
            for (Person p : personList) {
                Person person = new GetPersonByEmailDatabase(getDataSource().getConnection(), p.getEmail()).execute();
                //TODO: unlock mail sending          MailTypes.mailForTrainerChanged(person, lectureTimeSlot, notes);
            }
            return new Message(Codes.OK.getErrorMessage(), false);
        } catch (SQLException | NamingException e) {
            return new Message(Codes.INTERNAL_ERROR.getErrorMessage(), true);
        }

    }

}
