package servlet;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


/** Servlet handling requests for the About Us page
 *
 * @author Andrea Pasin
 */
public class ContactUsServlet extends AbstractServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        req.getRequestDispatcher(Constants.PATH_CONTACT_US).forward(req, res);
    }
}