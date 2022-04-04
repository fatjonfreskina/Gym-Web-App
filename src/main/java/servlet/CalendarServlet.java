package servlet;

import constants.Constants;
import dao.lecturetimeslot.GetLectureTimeSlotsCurrentWeekDatabase;
import dao.subscriptiontype.GetListSubscriptionTypeDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.LectureTimeSlot;
import resource.SubscriptionType;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
//import

public class CalendarServlet extends AbstractServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        List<LectureTimeSlot> list = null;
        try
        {
            list = new GetLectureTimeSlotsCurrentWeekDatabase(getDataSource().getConnection()).execute();
        }
        catch (SQLException ex)
        {

        }
        catch (NamingException ex)
        {

        }
        req.setAttribute("lectureTimeSlotList", list);
        req.getRequestDispatcher(Constants.PATH_CALENDAR).forward(req, res);

    }
}
