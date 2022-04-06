package servlet.auth;

import constants.Constants;
import constants.ErrorCodes;
import dao.passwordreset.GetPasswordResetDatabase;
import dao.passwordreset.InsertPasswordResetDatabase;
import dao.person.GetUserByEmailDatabase;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.PasswordReset;
import resource.Person;
import servlet.AbstractServlet;
import utils.EncryptionManager;
import utils.MailTypes;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
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
        req.getRequestDispatcher(Constants.PATH_PASSWORD_FORGOT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //If a POST request has been sent, then add a record to the table for password reset and return a view

        //Manages errors inside this function
        ErrorCodes status = null;

        //Read the token field from the request (GET parameter)
        String email = req.getParameter("email");

        Person person = null;
        Connection conn = null;

        try {
            //Get database connection
            conn = getDataSource().getConnection();
            //Check if such an email exists in the database
            person = new GetUserByEmailDatabase(conn, email).execute();
        } catch (SQLException | NamingException e) {
            //TODO: Error handling
        }

        //If the person has not been found do something
        if(person == null){
            //TODO: Error handling
        } else {
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
                PasswordReset passwordReset = new PasswordReset(token, expirationTimestamp,person.getEmail());
                //Insert the password reset into the database
                new InsertPasswordResetDatabase(conn, passwordReset).execute();
                MailTypes.mailForPasswordChanges(person, passwordReset);
            } catch (NoSuchAlgorithmException | MessagingException | SQLException | NamingException e) {
                //TODO: Error handling
            }

        }


    }
}
