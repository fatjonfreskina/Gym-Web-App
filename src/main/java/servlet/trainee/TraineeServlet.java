package servlet.trainee;

import constants.Constants;
import dao.person.GetStaffPeopleDatabase;
import dao.teaches.GetTeachesByTrainerDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.LectureTimeSlot;
import resource.Message;
import resource.Person;
import resource.Teaches;
import resource.view.CourseStatus;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class TraineeServlet extends AbstractServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Message m = null;
        try {
            l_trainer = new GetStaffPeopleDatabase(getDataSource().getConnection()).execute();
            m = new Message("Trainers retrieved correctly");
        } catch (NamingException e) {
            m = new Message("NamingException", true);
        } catch (SQLException e) {
            m = new Message("SQLException", true);
        }

        req.setAttribute("trainerlist", l_trainer);
        req.setAttribute("message", m);*/

        req.getRequestDispatcher(Constants.PATH_TRAINEE).forward(req, resp);
    }
}
