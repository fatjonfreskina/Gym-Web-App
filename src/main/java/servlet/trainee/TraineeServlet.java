package servlet.trainee;

import constants.Constants;
import dao.medicalcertificate.GetLastMedicalCertfiticateByPersonDatabase;
import dao.subscription.GetValidSubscriptionByTrainee;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.MedicalCertificate;
import resource.Message;
import resource.Person;
import resource.view.ValidSubscription;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TraineeServlet extends AbstractServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Message m;
        List<ValidSubscription> l_subscription = new ArrayList<>();
        MedicalCertificate medicalCertificate = null;

        HttpSession session = req.getSession(false);
        String email = session.getAttribute("email").toString();

        try {
            l_subscription = new GetValidSubscriptionByTrainee(getDataSource().getConnection(),email).execute();
            medicalCertificate = new GetLastMedicalCertfiticateByPersonDatabase(getDataSource().getConnection(),
                    new Person(email,null,null,null,null,null,null,null,null)).execute();

            m = new Message("Subscriptions retrieved correctly and also medicalcertificate");
        } catch (NamingException e) {
            m = new Message("NamingException", true);
        } catch (SQLException e) {
            m = new Message("SQLException", true);
        }

        req.setAttribute("subscriptionlist", l_subscription);
        req.setAttribute("medicalCertificate", medicalCertificate);
        req.setAttribute("message", m);

        req.getRequestDispatcher(Constants.PATH_TRAINEE).forward(req, res);
    }
}
