package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import constants.Constants;
import dao.lecturetimeslot.GetWeeklyCalendarSlotByDateDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.view.WeeklyCalendarSlot;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Francesco Caldivezzi
 * @author Alberto Campeol
 */


public class CalendarServlet extends AbstractServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher(Constants.PATH_CALENDAR).forward(req, res);
    }
}
