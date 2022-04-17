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

    /**
     * The JSON UTF-8 MIME media type
     */
    private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //res.setContentType(JSON_UTF_8_MEDIA_TYPE);
        List<WeeklyCalendarSlot> list = null;
        try {
            list = new GetWeeklyCalendarSlotByDateDatabase(getDataSource().getConnection(), Date.valueOf(LocalDate.now())).execute();
        } catch (SQLException | NamingException ex) {
            //errore
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        req.setAttribute("weeklyCalendar", gson.toJson(list));
        req.getRequestDispatcher(Constants.PATH_CALENDAR).forward(req, res);
    }
}
