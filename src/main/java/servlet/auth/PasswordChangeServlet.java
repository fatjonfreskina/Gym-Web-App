package servlet.auth;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.AbstractServlet;

import java.io.IOException;
import java.io.PrintWriter;

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

        String raw_password = req.getParameter("password");
        String raw_confirm = req.getParameter("password-confirm");

        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h3>The inserted password: " + raw_password + "</h3>");
        out.println("<h3>The confirmation password: " + raw_confirm + "</h3>");
        out.println("</body></html>");
    }
}
