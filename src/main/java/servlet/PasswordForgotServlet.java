package servlet;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Riccardo Forzan
 */
public class PasswordForgotServlet extends AbstractServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //If a GET request has been sent, then the form to ask for a password reset is shown
        req.getRequestDispatcher(Constants.PATH_AKS_FOT_PASSWORD_RESET).forward(req, resp);
    }
}
