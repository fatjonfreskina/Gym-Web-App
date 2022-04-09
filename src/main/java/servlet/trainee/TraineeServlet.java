package servlet.trainee;

import constants.Constants;
import dao.teaches.GetTeachesByTrainerDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.LectureTimeSlot;
import resource.Person;
import resource.Teaches;
import resource.view.CourseStatus;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class TraineeServlet extends AbstractServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Constants.PATH_TRAINEE).forward(req, resp);
    }
}
