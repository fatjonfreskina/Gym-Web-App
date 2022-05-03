package servlet;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet that handles http get requests for the TheGym web page
 *
 * @author Harjot Singh
 */
public class TheGymServlet extends AbstractServlet {

    /**
     * Handles the get request
     * @param req  the request
     * @param res  the response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    /*try {
      DataSource dataSource = getDataSource();
      //? SELECT Name, Slots FROM Room
    } catch (NamingException e) {
      System.out.println("Error " + e.getMessage());
      e.printStackTrace();
    }*/
        req.getRequestDispatcher(Constants.PATH_THE_GYM).forward(req, res);
    }
}
