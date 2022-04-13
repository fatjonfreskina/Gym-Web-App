package servlet.auth;

import constants.Constants;
import constants.ErrorCodes;
import dao.passwordreset.DeletePasswordResetDatabase;
import dao.passwordreset.GetPasswordResetDatabase;
import dao.person.GetPersonByEmailDatabase;
import dao.person.UpdatePersonDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Message;
import resource.PasswordReset;
import resource.Person;
import servlet.AbstractServlet;
import utils.EncryptionManager;
import utils.InputValidation;

import javax.naming.NamingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
        String token = req.getParameter(Constants.PASSWORDRESET_TOKEN);
        //Pass the token
        req.setAttribute(Constants.PASSWORDRESET_TOKEN, token);
        //Show the form to insert the new password
        req.getRequestDispatcher(Constants.PATH_PASSWORD_CHANGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        Message message = changePassword(req);

        if(message.isError()){
            //Return the same page of password reset passing the error message
            req.setAttribute(Constants.MESSAGE, message);
            req.getRequestDispatcher(Constants.PATH_PASSWORD_CHANGE).forward(req, resp);
        } else {
            //Redirect to the login page passing the successful message
            req.setAttribute(Constants.MESSAGE, message);
            req.getRequestDispatcher(Constants.PATH_LOGIN).forward(req, resp);
        }

    }

    /**
     * Implements the logic of the password change method elaborating the request object
     *
     * @param req  HTTP request object
     * @return Message with some information about the password change request
     */
    private Message changePassword(HttpServletRequest req) {

        //Get the token (hidden attribute in the form)
        String token = req.getParameter(Constants.PASSWORDRESET_TOKEN);

        //Handle the case in which the token is null
        if (token == null) {
            return new Message(ErrorCodes.TOKEN_NOT_FOUND.getErrorMessage(), true);
        }

        Person actualPerson = null;
        PasswordReset passwordReset = null;

        try {
            //Retrieve the PasswordReset instance
            PasswordReset passwordResetDatabase = new GetPasswordResetDatabase(getDataSource().getConnection(), token).execute();
            //Retrieve the Person associated
            actualPerson = new GetPersonByEmailDatabase(getDataSource().getConnection(), passwordResetDatabase.getPerson()).execute();
        } catch (SQLException | NamingException e) {
            //Something went wrong in the handling of the token
            return new Message(ErrorCodes.TOKEN_NOT_FOUND.getErrorMessage(), true);
        }

        //If password reset is null it means that a password reset token has not been found in the database
        if (passwordReset == null) {
            return new Message(ErrorCodes.TOKEN_NOT_FOUND.getErrorMessage(), true);
        }

        //Get from the request the new password and then change it
        String raw_password = req.getParameter(Constants.PASSWORD);
        String raw_confirm = req.getParameter(Constants.CONFIRM_PASSWORD);

        //Validate the password field against XSS, the password is valid if it does not contain something suspicious
        boolean valid = !InputValidation.containsXSS(raw_password);

        //If the input field contains something strange similar to JS code print an error
        if (!valid) {
            return new Message(ErrorCodes.PASSWORD_NOT_VALID.getErrorMessage(), true);
        }


        //Check if the password and the confirmation password are equal
        if (!raw_password.equals(raw_confirm)) {
            return new Message(ErrorCodes.DIFFERENT_PASSWORDS.getErrorMessage(), true);
        }

        //The password is valid and should be updated
        try {

            String hashed = EncryptionManager.encrypt(raw_password);
            //Construct a new person with the same fields, except the password field
            Person newPerson = new Person(actualPerson.getEmail(), actualPerson.getName(), actualPerson.getSurname(), hashed, actualPerson.getTaxCode(), actualPerson.getBirthDate(), actualPerson.getTelephone(), actualPerson.getAddress(), actualPerson.getAvatarPath());
            //Update the person
            new UpdatePersonDatabase(getDataSource().getConnection(), newPerson).execute();
            //No error occurred, password has been changed, remove the token for password reset
            new DeletePasswordResetDatabase(getDataSource().getConnection(), passwordReset);

            //The procedure has terminated correctly, return a positive message
            return new Message(ErrorCodes.OK.getErrorMessage(), false);

        } catch (NoSuchAlgorithmException | SQLException | NamingException e) {
            //Create the error message if something went wrong
            return new Message(ErrorCodes.INTERNAL_ERROR.getErrorMessage(), true);
        }

    }

}
