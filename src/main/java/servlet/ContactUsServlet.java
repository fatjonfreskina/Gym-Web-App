package servlet;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Servlet handling requests for the ContactUs page
 *
 * @author Andrea Pasin
 */
public class ContactUsServlet extends AbstractServlet {
    /**
     * Handles the get request by retrieving the page
     *
     * @param req  the request
     * @param res  the response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        req.getRequestDispatcher(Constants.PATH_CONTACT_US).forward(req, res);
    }
}