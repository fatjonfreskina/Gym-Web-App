package servlet.secretary.rest;

import com.google.gson.Gson;
import constants.ErrorCodes;
import dao.lecturetimeslot.DeleteLectureTimeSlotDatabase;
import dao.lecturetimeslot.GetLectureTimeSlotByRoomDateStartTimeDatabase;
import dao.person.GetPersonByEmailDatabase;
import dao.reservation.GetAllPeopleInReservationTimeSlotDatabase;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.LectureTimeSlot;
import resource.Message;
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //Result of the request
        Message resultMessage = null;
        boolean error = false;

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
            error = true;
            resultMessage = new Message(ErrorCodes.LECUTRETIMESLOT_NOT_FOUND.getErrorMessage(), true);
        }

        //Execute only if previously no error has been generated
        if(!error) {

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

            } catch (SQLException | NamingException e) {
                error = true;
                resultMessage = new Message(ErrorCodes.INTERNAL_ERROR.getErrorMessage(), true);
            }
        }

        //Prepare to send feedback about the operation using a JSON object
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        //If no error has been generated then generate a success message
        if(!error){
            resultMessage = new Message("Operation completed", false);
        }

        out.println(new Gson().toJson(resultMessage));

    }

}
