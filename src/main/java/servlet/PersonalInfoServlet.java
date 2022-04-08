package servlet;

import constants.Constants;
import dao.person.GetPersonByEmailDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.Person;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Shows all the personal information about the current user (must be logged in).
 * @author Marco Alessio
 */
public class PersonalInfoServlet extends AbstractServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        //Old version way to get the "email" of the user.
        //final String email = req.getParameter("email");

        HttpSession session = req.getSession(false);
        //This should never happen, given the use of PersonalInfoFilter.
        if (session == null)
            res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN);

        final String email = (String) session.getAttribute("email");
        //This should never happen, given the use of PersonalInfoFilter.
        if (email == null)
            res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN);

        Person person;

        try
        {
            //TODO: is still needed to query from the database, or the data is already available through the session object?
            person = new GetPersonByEmailDatabase(getDataSource().getConnection(), email).execute();
        } catch (NamingException | SQLException e)
        {
            //This should never happen, given the use of PersonalInfoFilter.
            throw new ServletException(e);
        }

        req.setAttribute("personalInfo", person);
        req.getRequestDispatcher(Constants.PATH_PERSONALINFO).forward(req, res);
    }
}