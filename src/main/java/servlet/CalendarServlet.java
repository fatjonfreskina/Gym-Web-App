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
 * Servlet handling requests for the Calendar page
 *
 * @author Francesco Caldivezzi
 * @author Alberto Campeol
 */


public class CalendarServlet extends AbstractServlet {

    /**
     * Handles the get request by retrieving the page
     *
     * @param req  the request
     * @param res  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher(Constants.PATH_CALENDAR).forward(req, res);
    }
}
