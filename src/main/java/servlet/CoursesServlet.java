package servlet;

import constants.Constants;
import dao.courseedition.GetAvailableCoursesDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.Course;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet that handles http get requests for the Courses web page
 *
 * @author Andrea Pasin
 */
public class CoursesServlet extends AbstractServlet{
    /**
     * Handles the get request by sending a response containing the different courses held in the gym
     *
     * @param req  the request
     * @param res  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Course> courseList = null;
        Logger logger= LogManager.getLogger("andrea_pasin_logger");
        try {
            courseList = new GetAvailableCoursesDatabase(getDataSource().getConnection()).execute();
        } catch (SQLException | NamingException ex) {
            logger.info(ex);
        }
        req.setAttribute("courseList", courseList);
        req.getRequestDispatcher(Constants.PATH_COURSES).forward(req, res);
    }
}
