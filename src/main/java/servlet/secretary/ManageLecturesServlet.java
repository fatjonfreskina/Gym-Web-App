package servlet.secretary;

import com.google.gson.Gson;
import dao.lecturetimeslot.GetAllLectureTimeSlotDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.LectureTimeSlot;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Riccardo Forzan
 */
public class ManageLecturesServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<LectureTimeSlot> lectureTimeSlots = null;

        //Get all the lecture time slots
        try {
            lectureTimeSlots = new GetAllLectureTimeSlotDatabase(getDataSource().getConnection()).execute();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        String ltsJson = new Gson().toJson(lectureTimeSlots);
        out.println(ltsJson);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){



    }
}
