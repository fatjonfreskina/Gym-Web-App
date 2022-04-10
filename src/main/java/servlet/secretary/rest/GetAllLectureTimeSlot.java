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
import java.util.List;

/**
 * @author Riccardo Forzan
 */
public class GetAllLectureTimeSlot extends AbstractServlet {

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

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String ltsJson = new Gson().toJson(lectureTimeSlots);
        out.println(ltsJson);

    }

}
