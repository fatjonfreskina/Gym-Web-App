package servlet;

import constants.Constants;
import dao.courseedition.GetAvailableCourses;
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
 * @author Andrea Pasin
 */
public class CoursesServlet extends AbstractServlet{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Course> courseList = null;
        Logger logger= LogManager.getLogger("andrea_pasin_logger");
        try {
            courseList = new GetAvailableCourses(getDataSource().getConnection()).execute();
        } catch (SQLException | NamingException ex) {
            logger.info(ex);
        }
        req.setAttribute("courseList", courseList);
        req.getRequestDispatcher(Constants.PATH_COURSES).forward(req, res);
    }
}
