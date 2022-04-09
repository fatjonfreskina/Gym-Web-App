package servlet.secretary.rest;

import dao.lecturetimeslot.DeleteLectureTimeSlotDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.LectureTimeSlot;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

/**
 * @author Riccardo Forzan
 */
public class DeleteLectureTimeSlot extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //http://localhost:8080/wa2122-gwa/secretary/rest/deletelecturetimeslot?roomname=Stamina&date=2022-04-05&starttime=18:00:00

        //Parse the parameters
        String roomName = request.getParameter("roomname");
        Date date = Date.valueOf(request.getParameter("date"));
        Time startTime = Time.valueOf(request.getParameter("starttime"));

        LectureTimeSlot toFind = new LectureTimeSlot(roomName,date,startTime,null,null,null);

        try {

            //TODO: Delete all the users of the lecturetimeslot and send an email to all the users

            //Delete the LectureTimeSlot (all subscriptions have been removed)
            new DeleteLectureTimeSlotDatabase(getDataSource().getConnection(),toFind).execute();

        } catch (SQLException | NamingException e) {

            //TODO: Handle the error

        }

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("DEBUG MESSAGES");
        out.println(roomName);
        out.println(date);
        out.println(startTime);
        out.println(toFind);

    }

}
