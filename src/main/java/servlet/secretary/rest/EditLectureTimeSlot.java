package servlet.secretary.rest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.AbstractServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;

/**
 * @author Riccardo Forzan
 */
public class EditLectureTimeSlot extends AbstractServlet {

    @Override
    //TODO: Convert to doPost
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //http://localhost:8080/wa2122-gwa/secretary/rest/editlecturetimeslot?roomname=Stamina&date=2022-04-05&starttime=18:00:00&courseeditionid=2&coursename=Cardio&subsitution=giacomo.forza@example.com

        //Parse the parameters
        String roomName = request.getParameter("roomname");
        Date date = Date.valueOf(request.getParameter("date"));
        Time startTime = Time.valueOf(request.getParameter("starttime"));
        Integer coursEditionId = Integer.valueOf(request.getParameter("courseeditionid"));
        String courseName = request.getParameter("coursename");
        String email = request.getParameter("subsitution");

        //This field will be sent as a motivation in the email to notice all the subscripted users
        String notice = request.getParameter("notice");


        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("DEBUG MESSAGES");
        out.println(roomName);
        out.println(date);
        out.println(startTime);
        out.println(coursEditionId);
        out.println(courseName);
        out.println(email);
        out.println(notice);

    }

}
