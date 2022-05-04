package servlet.secretary;

import constants.Codes;
import constants.Constants;
import dao.person.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Message;
import resource.Person;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.*;
import java.sql.SQLException;

/**
 * Servlet used by a secretary to manage the roles of a user
 *
 * @author Alberto Campeol
 */
public class ManageRolesServlet extends AbstractServlet {

    /**
     * Handles the get request by retrieving the opportune page
     *
     * @param req  the request
     * @param res  the response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher(Constants.PATH_SECRETARY_MANAGE_ROLES).forward(req, res);
    }

    /**
     * Handles the post request by adding to the database the different roles for a user
     * specified in the request. It also returns a confirmation/error message
     *
     * @param req  the request
     * @param res  the response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String email = null;
        Message message = new Message(Codes.OK.getErrorMessage(), false);
        Codes error = parseParams(req, res);
        if (error.getErrorCode() != Codes.OK.getErrorCode())
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
                    error = Codes.INVALID_FIELDS;
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
                error = Codes.INTERNAL_ERROR;
            } catch (NamingException e) {
                error = Codes.INTERNAL_ERROR;
            }
            if (error.getErrorCode() != Codes.OK.getErrorCode())
                message = new Message(error.getErrorMessage(), true);

        }
        res.setStatus(error.getHTTPCode());
        req.setAttribute(Constants.MESSAGE, message);
        req.getRequestDispatcher(Constants.PATH_SECRETARY_MANAGE_ROLES).forward(req, res);
    }

    /**
     * Checks if the different parameters are well formatted
     *
     * @param req  the request
     * @param res  the response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public Codes parseParams(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String email = null;
        Codes error = Codes.OK;
        try
        {
            email = req.getParameter(Constants.EMAIL);
        }
        catch (IllegalArgumentException e)
        {
            error = Codes.INVALID_FIELDS;
        }
        if (error.getErrorCode() == Codes.OK.getErrorCode())
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
                error = Codes.EMPTY_INPUT_FIELDS;
        }
        return error;
    }

}