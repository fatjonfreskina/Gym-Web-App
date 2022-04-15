package servlet.auth;

import constants.Codes;
import constants.Constants;
import dao.passwordreset.InsertPasswordResetDatabase;
import dao.person.GetPersonByEmailDatabase;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.Message;
import resource.PasswordReset;
import resource.Person;
import servlet.AbstractServlet;
import utils.EncryptionManager;
import utils.MailTypes;

import javax.naming.NamingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Riccardo Forzan
 */
public class PasswordForgotServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //If a GET request has been sent, then the form to ask for a password reset is shown
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute(Constants.EMAIL) == null)
            req.getRequestDispatcher(Constants.PATH_PASSWORD_FORGOT).forward(req, resp);
        else{
            req.setAttribute(Constants.EMAIL, session.getAttribute(Constants.EMAIL).toString());
            doPost(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        //If a POST request has been sent, then add a record to the table for password reset and return a view
        Message message = askForPasswordReset(req);

        if (message.isError()) {
            //Return the same page of password reset passing the error message
            req.setAttribute(Constants.MESSAGE, message);
            req.getRequestDispatcher(Constants.PATH_PASSWORD_FORGOT).forward(req, resp);
        } else {
            //Redirect to the login page passing the successful message
            req.setAttribute(Constants.MESSAGE, message);
            req.getRequestDispatcher(Constants.PATH_LOGIN).forward(req, resp);
        }
    }

    /**
     * Handles the logic of adding a new record in the password reset table, sending a mail to the user and so on
     *
     * @param req HTTP request to process
     * @return Message with information about the state of the request
     */
    private Message askForPasswordReset(HttpServletRequest req) {

        //Read the token field from the request (GET parameter)
        String email = req.getParameter(Constants.EMAIL);
        if (email == null)
            email = req.getAttribute(Constants.EMAIL).toString();


        Person person = null;
        Connection conn = null;

        try {
            //Get database connection
            conn = getDataSource().getConnection();
            //Check if such an email exists in the database
            person = new GetPersonByEmailDatabase(conn, email).execute();
        } catch (SQLException | NamingException e) {
            //Not possible to find the person with the specified email in the database
            return new Message(Codes.PERSON_NOT_FOUND.getErrorMessage(), true);
        }

        if (person == null) {
            //Not possible to find the person with the specified email in the database
            return new Message(Codes.PERSON_NOT_FOUND.getErrorMessage(), true);
        }

        //Generate a new token string of length 256
        try {
            String token = EncryptionManager.encrypt(person.getAddress());
            //Reopen a new connection
            conn = getDataSource().getConnection();
            //Take the actual date and add 24H
            Date now = new Date();
            Date expiration = new Date(now.getTime() + TimeUnit.HOURS.toMillis(24));
            Timestamp expirationTimestamp = new Timestamp(expiration.getTime());
            //PasswordReset
            PasswordReset passwordReset = new PasswordReset(token, expirationTimestamp, person.getEmail());
            //Insert the password reset into the database
            new InsertPasswordResetDatabase(conn, passwordReset).execute();
            //Send an email with password reset link
            MailTypes.mailForPasswordChanges(person, passwordReset);

            //The email with password reset token has been sent
            return new Message(Codes.PASSWORD_RESET_SENT.getErrorMessage(), false);

        } catch (NoSuchAlgorithmException | MessagingException | SQLException | NamingException e) {
            //Some internal error occurred while generating the token
            return new Message(Codes.INTERNAL_ERROR.getErrorMessage(), true);
        }

    }

}
