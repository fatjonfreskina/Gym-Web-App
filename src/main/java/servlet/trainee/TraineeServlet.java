package servlet.trainee;

import constants.Constants;
import dao.person.GetStaffPeopleDatabase;
import dao.subscription.GetValidSubscriptionByTrainee;
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
import resource.view.ValidSubscription;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TraineeServlet extends AbstractServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Message m;
        List<ValidSubscription> l_subscription = new ArrayList<>();
        HttpSession session = req.getSession(false);
        String email = session.getAttribute("email").toString();

        try {
            l_subscription = new GetValidSubscriptionByTrainee(getDataSource().getConnection(),email).execute();
            m = new Message("Subscriptions retrieved correctly");
        } catch (NamingException e) {
            m = new Message("NamingException", true);
        } catch (SQLException e) {
            m = new Message("SQLException", true);
        }

        req.setAttribute("subscriptionlist", l_subscription);
        req.setAttribute("message", m);

        req.getRequestDispatcher(Constants.PATH_TRAINEE).forward(req, res);
    }
}
