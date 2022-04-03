package servlet.auth;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.AbstractServlet;

import java.io.IOException;

/**
 * This servlet is accessible after having asked for a password reset.
 * @author Riccardo Forzan
 */
public class PasswordChangeServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Show the form to insert the new password
        req.getRequestDispatcher(Constants.PATH_PASSWORD_CHANGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Get from the request the new password and then change it
        //TODO: Read the fields from the HTTP request, then calculate the HASH of the new password and save it

        resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
}
