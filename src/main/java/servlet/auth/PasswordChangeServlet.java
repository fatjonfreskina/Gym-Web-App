package servlet.auth;

import com.google.common.html.HtmlEscapers;
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
        String raw_password = req.getParameter("password");
        String raw_confirm = req.getParameter("password-confirm");

        //Implement some type of validation for the string field
        String sanitized_password = HtmlEscapers.htmlEscaper().escape(raw_password);

        //If the sanitized password is different from the raw one, the given password is not valid
        boolean validation = raw_password.equals(sanitized_password);

        //TODO: DEBUG PURPOSES ONLY
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");

        //Check if the provided password is valid or contains something possibly malicious
        if(validation){

            //Check if the password and the confirmation password are equal
            if(raw_password.equals(raw_confirm)){
                //The password is valid and should be updated
                out.println("<h3>The new password will be: " + raw_password + "</h3>");
            } else {
                out.println("<h3>The password and the confirmation password fields do not match! </h3>");
            }

        } else {

            out.println("<h1>The password has been detected as malicious!<h1>");
            //The sanitized input password field will be
            out.println("<h3>The sanitized field is: " + sanitized_password + "</h3>");

        }

        out.println("</body></html>");

    }
}
