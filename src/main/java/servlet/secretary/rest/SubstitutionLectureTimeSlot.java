package servlet.secretary.rest;

import dao.lecturetimeslot.GetLectureTimeSlotByRoomDateStartTimeDatabase;
import dao.lecturetimeslot.UpdateLectureTimeSlotDatabase;
import dao.person.GetPersonByEmailDatabase;
import dao.reservation.GetAllPeopleInReservationTimeSlotDatabase;
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
public class SubstitutionLectureTimeSlot extends AbstractServlet {

    @Override
    //TODO: Convert to doPost
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //http://localhost:8080/wa2122-gwa/secretary/rest/editlecturetimeslot?roomname=Stamina&date=2022-04-05&starttime=18:00:00&subsitution=giacomo.forza@example.com&notice=the trainer is sick

        //Parse the parameters
        String roomName = request.getParameter("roomname");
        Date date = Date.valueOf(request.getParameter("date"));
        Time startTime = Time.valueOf(request.getParameter("starttime"));
        String email = request.getParameter("substitution");

        //This field will be sent as a motivation in the email to notice all the subscribed users
        String notes = request.getParameter("notes");

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("DEBUG MESSAGES");
        out.println(roomName);

        LectureTimeSlot lectureTimeSlot = null;

        //Try to update the LectureTimeSlot
        try {
            //Get the LectureTimeSlot
            lectureTimeSlot = new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection(),new LectureTimeSlot(roomName,date,startTime,null,null,null)).execute();
            //Create a new LectureTimeSlot updating the substitution field
            LectureTimeSlot updatedLectureTimeSlot = new LectureTimeSlot(lectureTimeSlot.getRoomName(), lectureTimeSlot.getDate(), lectureTimeSlot.getStartTime(), lectureTimeSlot.getCourseEditionId(), lectureTimeSlot.getCourseName(), email);
            //Update the LectureTimeSlot
            new UpdateLectureTimeSlotDatabase(getDataSource().getConnection(), updatedLectureTimeSlot).execute();
        } catch (SQLException | NamingException e) {
            //TODO: handle errors
        }

        //Notify all the subscribed people
        Reservation reservation = new Reservation(roomName,date,startTime);

        //Try to notify all the users
        try {
            List<Person> personList = new GetAllPeopleInReservationTimeSlotDatabase(getDataSource().getConnection(), reservation).execute();
            //Send a mail to each user
            for(Person p:personList){
                Person person = new GetPersonByEmailDatabase(getDataSource().getConnection(),p.getEmail()).execute();
                out.println("email to: " + person);
                //TODO: unlock mail sending          MailTypes.mailForTrainerChanged(person, lectureTimeSlot, notes);
            }
        } catch (SQLException | NamingException e) {
            //TODO: handle errors
        }


    }

}
