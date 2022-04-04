package servlet;

import constants.Constants;
import dao.person.GetUserByEmailDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Person;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

public class PersonalInfoServlet extends AbstractServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        try
        {
            String email = req.getParameter("email");
            var conn = getDataSource().getConnection();
            Person person = new GetUserByEmailDatabase(conn , email).execute();

            req.setAttribute("personalInfo", person);
            req.getRequestDispatcher(Constants.PATH_PERSONALINFO).forward(req, res);
        }
        catch (NamingException | SQLException e)
        {
            throw new ServletException(e);
        }
    }
}