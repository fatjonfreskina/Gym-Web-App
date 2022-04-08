package servlet;

import com.google.gson.Gson;
import constants.Constants;
import dao.lecturetimeslot.GetLectureTimeSlotsCurrentWeekDatabase;
import dao.lecturetimeslot.GetWeeklyCalendarSlotByDateDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.LectureTimeSlot;
import resource.view.WeeklyCalendar;
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
        List<WeeklyCalendarSlot> list = null;
        try {
            list = new GetWeeklyCalendarSlotByDateDatabase(getDataSource().getConnection(), Date.valueOf(LocalDate.now())).execute();
        } catch (SQLException | NamingException ex) {
            //errore
        }
        Gson gson = new Gson();
        req.setAttribute("weeklyCalendar", gson.toJson(list));
        req.getRequestDispatcher(Constants.PATH_CALENDAR).forward(req, res);
    }
}
