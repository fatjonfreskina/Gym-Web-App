package servlet;

import constants.Constants;
import dao.person.GetStaffPeopleDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Message;
import resource.view.Trainer;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet that handles http get requests for the Staff web page
 *
 * @author Riccardo Tumiati
 */
public class StaffServlet extends AbstractServlet {
    /**
     * Handles the get request by sending a response with the list of trainers and a message
     * @param req  the request
     * @param res  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Trainer> l_trainer = null;
        Message m = null;
        try {
            l_trainer = new GetStaffPeopleDatabase(getDataSource().getConnection()).execute();
            m = new Message("Trainers retrieved correctly");
        } catch (NamingException e) {
            m = new Message("NamingException", true);
        } catch (SQLException e) {
            m = new Message("SQLException", true);
        }

        req.setAttribute("trainerlist", l_trainer);
        req.setAttribute("message", m);

        req.getRequestDispatcher(Constants.PATH_STAFF).forward(req, res);
    }
}
