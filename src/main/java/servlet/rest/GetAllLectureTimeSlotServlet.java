package servlet.rest;

import com.google.gson.Gson;
import dao.lecturetimeslot.GetAllLectureTimeSlotDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.LectureTimeSlot;
import servlet.AbstractRestServlet;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static constants.DateTimeFormats.dateFormat;
import static constants.DateTimeFormats.timeFormat;

/**
 * Rest servlet to retrieve all the lectures in a given period
 * @author Riccardo Forzan
 */
public class GetAllLectureTimeSlotServlet extends AbstractRestServlet {

    /**
     * Helper class used to fill the calendar using AJAX
     */
    private class MyLectureTimeSlot {

        private final String roomName;
        private final String dateTime;
        private final String date;
        private final String startTime;
        private final Integer courseEditionId;
        private final String courseName;
        private final String substitution;

        public MyLectureTimeSlot(String roomName, String dateTime, Date date, Time startTime, Integer courseEditionId, String courseName, String substitution) {
            this.roomName = roomName;
            this.dateTime = dateTime;
            this.date = dateFormat.format(date);
            this.startTime = timeFormat.format(startTime);
            this.courseEditionId = courseEditionId;
            this.courseName = courseName;
            this.substitution = substitution;
        }

    }

    /**
     * Handles the get request by retrieving all the lectures held in a given period
     * of time
     *
     * @param request the request
     * @param response the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
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
            MyLectureTimeSlot myLectureTimeSlot = new MyLectureTimeSlot(lts.getRoomName(), starts.toString(),
                    lts.getDate(), lts.getStartTime(), lts.getCourseEditionId(),
                    lts.getCourseName(), lts.getSubstitution());
            //Add to the collection
            myLectureTimeSlots.add(myLectureTimeSlot);
        }

        sendDataResponse(response,myLectureTimeSlots);
    }

}
