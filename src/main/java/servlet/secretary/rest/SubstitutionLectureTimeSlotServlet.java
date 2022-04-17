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
public class SubstitutionLectureTimeSlotServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Try to perform the operation
        Message message = performSubstitution(request);

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
     * Executes the substitution of a trainer in a LectureTimeSlot, it also sends an email to all the subscribed users
     *
     * @param request HTTP requests to process (containing the parameters)
     * @return Message regarding the status of the required operation
     */
    private Message performSubstitution(HttpServletRequest request) {

        //Parse the parameters
        String roomName = request.getParameter("roomname");

        DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("MMM dd, yyyy")).toFormatter();
        LocalDate localDate = LocalDate.parse(request.getParameter("date"), formatter);
        Date date = new Date(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());

        LocalTime localTime = LocalTime.parse(request.getParameter("starttime"), DateTimeFormatter.ofPattern("hh:mm:ss a"));
        Time startTime = Time.valueOf(localTime);

        String email = request.getParameter("substitution");
        //This field will be sent as a motivation in the email to notice all the subscribed users
        String notes = request.getParameter("notes");

        LectureTimeSlot lectureTimeSlot;

        //Try to update the LectureTimeSlot
        try {
            //Get the LectureTimeSlot
            lectureTimeSlot = new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection(), new LectureTimeSlot(roomName, date, startTime, null, null, null)).execute();
            //Create a new LectureTimeSlot updating the substitution field
            LectureTimeSlot updatedLectureTimeSlot = new LectureTimeSlot(lectureTimeSlot.getRoomName(), lectureTimeSlot.getDate(), lectureTimeSlot.getStartTime(), lectureTimeSlot.getCourseEditionId(), lectureTimeSlot.getCourseName(), email);
            //Update the LectureTimeSlot
            new UpdateLectureTimeSlotDatabase(getDataSource().getConnection(), lectureTimeSlot, updatedLectureTimeSlot).execute();
        } catch (SQLException | NamingException e) {
            return new Message(Codes.INTERNAL_ERROR.getErrorMessage(), true);
        }

        //Notify all the subscribed people
        Reservation reservation = new Reservation(roomName, date, startTime);

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
