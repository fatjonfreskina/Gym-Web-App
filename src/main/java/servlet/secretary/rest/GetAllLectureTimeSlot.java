package servlet.secretary.rest;

import com.google.gson.Gson;
import dao.lecturetimeslot.GetAllLectureTimeSlotDatabase;
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
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Riccardo Forzan
 */
public class GetAllLectureTimeSlot extends AbstractServlet {

    /**
     * Helper class used to fill the calendar using AJAX
     */
    private class MyLectureTimeSlot {

        private final String roomName;
        private final String dateTime;
        private final Integer courseEditionId;
        private final String courseName;
        private final String substitution;

        public MyLectureTimeSlot(String roomName, String dateTime, Integer courseEditionId, String courseName, String substitution) {
            this.roomName = roomName;
            this.dateTime = dateTime;
            this.courseEditionId = courseEditionId;
            this.courseName = courseName;
            this.substitution = substitution;
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Date startDate = Date.valueOf(request.getParameter("start"));
        Date endDate = Date.valueOf(request.getParameter("end"));

        List<LectureTimeSlot> lectureTimeSlots = null;

        //Get all the lecture time slots
        try {
            lectureTimeSlots = new GetAllLectureTimeSlotDatabase(getDataSource().getConnection(), startDate, endDate).execute();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }

        List<MyLectureTimeSlot> myLectureTimeSlots = new ArrayList<>(lectureTimeSlots.size());

        for(LectureTimeSlot lts:lectureTimeSlots){
            //Create timestamp from date and time
            long time = lts.getDate().getTime() + lts.getStartTime().getTime();
            LocalDateTime starts = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), TimeZone.getDefault().toZoneId());
            //Create custom object
            MyLectureTimeSlot myLectureTimeSlot = new MyLectureTimeSlot(lts.getRoomName(), starts.toString(), lts.getCourseEditionId(), lts.getCourseName(), lts.getSubstitution());
            //Add to the collection
            myLectureTimeSlots.add(myLectureTimeSlot);
        }

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String ltsJson = new Gson().toJson(myLectureTimeSlots);
        out.println(ltsJson);

    }

}
