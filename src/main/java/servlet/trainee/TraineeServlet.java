package servlet.trainee;

import constants.Constants;
import dao.medicalcertificate.GetLastMedicalCertfiticateByPersonDatabase;
import dao.subscription.GetValidSubscriptionByTraineeDatabase;
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
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Servlet handling requests for the Trainee home page
 */
public class TraineeServlet extends AbstractServlet {
    /**
     * Handles the get request by retrieving the courses for which a trainee is subscribed, the
     * status of his/her medical certificate and a message of error/success
     *
     * @param req  the request
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Message m;
        List<ValidSubscription> l_subscription = new ArrayList<>();
        MedicalCertificate medicalCertificate = null;

        HttpSession session = req.getSession(false);
        String email = session.getAttribute("email").toString();

        try {
            l_subscription = new GetValidSubscriptionByTraineeDatabase(getDataSource().getConnection(),email).execute();
            medicalCertificate = new GetLastMedicalCertfiticateByPersonDatabase(getDataSource().getConnection(),
                    new Person(email,null,null,null,null,null,null,null,null)).execute();

            if(medicalCertificate != null && medicalCertificate.getExpirationDate().toLocalDate().isBefore(LocalDate.now())){
                medicalCertificate = null;
            }

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
