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
        if (session == null)
            res.sendRedirect(Constants.RELATIVE_URL_LOGIN);

        final String email = (String) session.getAttribute("email");
        //TODO: can this really happen? Anyway, find a more suitable action to perform.
        if (email == null)
            res.sendRedirect(Constants.RELATIVE_URL_LOGIN);

        try
        {
            //TODO: is still needed to query from the database, or the data is already available through the session object?
            Person person = new GetPersonByEmailDatabase(getDataSource().getConnection(), email).execute();

            req.setAttribute("personalInfo", person);
            req.getRequestDispatcher(Constants.PATH_PERSONALINFO).forward(req, res);
        } catch (NamingException | SQLException e)
        {
            throw new ServletException(e);
        }
    }
}