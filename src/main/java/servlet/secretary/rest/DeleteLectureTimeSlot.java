package servlet.secretary.rest;

import dao.lecturetimeslot.DeleteLectureTimeSlotDatabase;
import dao.lecturetimeslot.GetLectureTimeSlotByRoomDateStartTimeDatabase;
import dao.person.GetPersonByEmailDatabase;
import dao.reservation.GetAllPeopleInReservationTimeSlotDatabase;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.LectureTimeSlot;
import resource.Person;
import resource.Reservation;
import servlet.AbstractServlet;
import utils.MailTypes;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

/**
 * @author Riccardo Forzan
 */
public class DeleteLectureTimeSlot extends AbstractServlet {

    @Override
    //TODO: Convert to doPost
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //http://localhost:8080/wa2122-gwa/secretary/rest/deletelecturetimeslot?roomname=Stamina&date=2022-04-05&starttime=18:00:00

        //Parse the parameters
        String roomName = request.getParameter("roomname");
        Date date = Date.valueOf(request.getParameter("date"));
        Time startTime = Time.valueOf(request.getParameter("starttime"));

        //Edited reservation
        Reservation toFind = new Reservation(roomName,date,startTime);

        //Get the lectureTimeSlot
        LectureTimeSlot lectureTimeSlot = null;
        try {
            lectureTimeSlot = new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection(), new LectureTimeSlot(roomName,date,startTime,null,null,null)).execute();
        } catch (SQLException | NamingException e) {
            //TODO: handle the errors
        }

        //List of person that will be notified
        List<Person> noticeTo = null;

        //DEBUG
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("DEBUG MESSAGES");
        out.println(roomName);
        out.println(date);
        out.println(startTime);
        out.println(lectureTimeSlot);

        try {

            //Get the collection of people that should be notified
            noticeTo = new GetAllPeopleInReservationTimeSlotDatabase(getDataSource().getConnection(),toFind).execute();

            //Iterate over all persons
            for (Person p: noticeTo) {
                Person person = new GetPersonByEmailDatabase(getDataSource().getConnection(),p.getEmail()).execute();
                out.println("email to: " + person);
                //TODO: unlock mail sending             MailTypes.mailForCancellationLecture(person,lectureTimeSlot);
            }

            //Delete the LectureTimeSlot (all subscriptions have been removed)
            new DeleteLectureTimeSlotDatabase(getDataSource().getConnection(),lectureTimeSlot).execute();

        } catch (SQLException | NamingException e) {
            //TODO: Handle the error
        }

    }

}
