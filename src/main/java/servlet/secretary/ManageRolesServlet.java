package servlet.secretary;

import constants.Constants;
import constants.ErrorCodes;
import dao.emailconfirmation.InsertEmailConfirmationDatabase;
import dao.passwordreset.InsertPasswordResetDatabase;
import dao.person.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import resource.EmailConfirmation;
import resource.Message;
import resource.PasswordReset;
import resource.Person;
import servlet.AbstractServlet;
import utils.EncryptionManager;
import utils.InputValidation;
import utils.MailTypes;

import javax.naming.NamingException;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author Alberto Campeol
 */
public class ManageRolesServlet extends AbstractServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher(Constants.PATH_SECRETARY_MANAGE_ROLES).forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String email = null;
        Message message = new Message(ErrorCodes.OK.getErrorMessage(), false);
        ErrorCodes error = parseParams(req, res);
        if (error.getErrorCode() != ErrorCodes.OK.getErrorCode())
        {
            message = new Message(error.getErrorMessage(), true);
        }
        else
        {
            email = req.getParameter(Constants.EMAIL);
            boolean isTrainer = false;
            if (req.getParameter(Person.ROLE_TRAINER) != null)
                isTrainer = req.getParameter(Person.ROLE_TRAINER).equals("on");
            boolean isSecretary = false;
            if (req.getParameter(Person.ROLE_SECRETARY) != null)
                isSecretary = req.getParameter(Person.ROLE_SECRETARY).equals("on");
            boolean isTrainee = false;
            if (req.getParameter(Person.ROLE_TRAINEE) != null)
                isTrainee = req.getParameter(Person.ROLE_TRAINEE).equals("on");
            try
            {
                Person p = new GetPersonByEmailDatabase(getDataSource().getConnection(), email).execute();
                if (p == null)
                    error = ErrorCodes.INVALID_FIELDS;
                else
                {
                    new DeletePersonRoleDatabase(getDataSource().getConnection(), p).execute();
                    if (isSecretary)
                        new InsertPersonRoleDatabase(getDataSource().getConnection(), p, Person.ROLE_SECRETARY).execute();
                    if (isTrainee)
                        new InsertPersonRoleDatabase(getDataSource().getConnection(), p, Person.ROLE_TRAINEE).execute();
                    if (isTrainer)
                        new InsertPersonRoleDatabase(getDataSource().getConnection(), p, Person.ROLE_TRAINER).execute();
                }
            } catch (SQLException e) {
                error = ErrorCodes.INTERNAL_ERROR;
            } catch (NamingException e) {
                error = ErrorCodes.INTERNAL_ERROR;
            }
            if (error.getErrorCode() != ErrorCodes.OK.getErrorCode())
                message = new Message(error.getErrorMessage(), true);

        }
        res.setStatus(error.getHTTPCode());
        req.setAttribute(Constants.MESSAGE, message);
        req.getRequestDispatcher(Constants.PATH_SECRETARY_MANAGE_ROLES).forward(req, res);
    }


    public ErrorCodes parseParams(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String email = null;
        ErrorCodes error = ErrorCodes.OK;
        try
        {
            email = req.getParameter(Constants.EMAIL);
        }
        catch (IllegalArgumentException e)
        {
            error = ErrorCodes.INVALID_FIELDS;
        }
        if (error.getErrorCode() == ErrorCodes.OK.getErrorCode())
        {
            boolean isTrainer = false;
            if (req.getParameter(Person.ROLE_TRAINER) != null)
                isTrainer = req.getParameter(Person.ROLE_TRAINER).equals("on");
            boolean isSecretary = false;
            if (req.getParameter(Person.ROLE_SECRETARY) != null)
                isSecretary = req.getParameter(Person.ROLE_SECRETARY).equals("on");
            boolean isTrainee = false;
            if (req.getParameter(Person.ROLE_TRAINEE) != null)
                isTrainee = req.getParameter(Person.ROLE_TRAINEE).equals("on");
            if (!isSecretary && !isTrainee && !isTrainer)
                error = ErrorCodes.EMPTY_INPUT_FIELDS;
        }
        return error;
    }

}