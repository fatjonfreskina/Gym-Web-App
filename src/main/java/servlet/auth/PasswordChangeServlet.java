package servlet.auth;

import com.google.common.html.HtmlEscapers;
import constants.Constants;
import constants.ErrorCodes;
import dao.passwordreset.GetPasswordResetDatabase;
import dao.person.GetUserByEmailDatabase;
import dao.person.UpdateUserDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.PasswordReset;
import resource.Person;
import servlet.AbstractServlet;
import utils.EncryptionManager;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This servlet is accessible after having asked for a password reset.
 *
 * @author Riccardo Forzan
 */
public class PasswordChangeServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Read the token field from the request (GET parameter)
        String token = req.getParameter("token"); //TODO: Declare a constant for String "token"
        //Pass the token
        req.setAttribute("token", token);
        //Show the form to insert the new password
        req.getRequestDispatcher(Constants.PATH_PASSWORD_CHANGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //Manages errors inside this function
        ErrorCodes status = null;

        //Get the token (hidden attribute)
        String token = req.getParameter("token");

        if (token == null) {
            //TODO: Throw some error
            return;
        }

        Person actualPerson = null;
        PasswordReset passwordReset = null;

        try {
            //Retrieve the PasswordReset instance
            PasswordReset passwordResetDatabase = new GetPasswordResetDatabase(getDataSource().getConnection(), token).execute();
            //Retrieve the Person associated
            actualPerson = new GetUserByEmailDatabase(getDataSource().getConnection(), passwordResetDatabase.getPerson()).execute();
        } catch (SQLException | NamingException e) {
            //TODO: Manage the error
        }

        if (passwordReset == null) {
            //Password reset record not found in the database
            status = ErrorCodes.INTERNAL_ERROR;
        }

        //Get from the request the new password and then change it
        String raw_password = req.getParameter("password");
        String raw_confirm = req.getParameter("password-confirm");

        //Implement some type of validation for the string field
        String sanitized_password = HtmlEscapers.htmlEscaper().escape(raw_password);

        //If the sanitized password is different from the raw one, the given password is not valid
        boolean valid = raw_password.equals(sanitized_password);

        //Check if the provided password is valid or contains something possibly malicious
        if (valid) {

            //Check if the password and the confirmation password are equal
            if (raw_password.equals(raw_confirm)) {

                //The password is valid and should be updated
                try {

                    String hashed = EncryptionManager.encrypt(raw_password);

                    //Construct a new person with the same fields, except the password field
                    Person newPerson = new Person(actualPerson.getEmail(), actualPerson.getName(), actualPerson.getSurname(), hashed, actualPerson.getTaxCode(), actualPerson.getBirthDate(), actualPerson.getTelephone(), actualPerson.getAddress(), actualPerson.getAvatarPath());

                    //Update the person
                    new UpdateUserDatabase(getDataSource().getConnection(), newPerson).execute();

                    //No error occurred, password has been changed
                    status = ErrorCodes.OK;

                } catch (NoSuchAlgorithmException | SQLException | NamingException e) {
                    //TODO
                    e.printStackTrace();
                    status = ErrorCodes.INTERNAL_ERROR;
                }

            } else {
                //The two provided passwords are not equal
                //TODO
                status = ErrorCodes.INTERNAL_ERROR;
            }

        } else {
            // Input field has something nasty inside
            status = ErrorCodes.INTERNAL_ERROR;
        }

        //If an error has occurred while updating the password than show something
        if (status != ErrorCodes.OK) {
            // TODO: Handle errors
        }

    }
}
