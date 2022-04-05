package servlet.auth;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.AbstractServlet;

import java.io.IOException;

/**
 * @author Riccardo Forzan
 */
public class PasswordForgotServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //If a GET request has been sent, then the form to ask for a password reset is shown
        req.getRequestDispatcher(Constants.PATH_PASSWORD_FORGOT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //If a POST request has been sent, then add a record to the table for password reset and return a view
        //TODO
        resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
}
