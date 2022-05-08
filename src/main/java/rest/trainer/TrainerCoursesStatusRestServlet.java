package rest.trainer;

import constants.Codes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.view.CourseStatus;
import service.TrainerService;
import servlet.AbstractRestServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Rest servlet used to retrieve the courses' statues of the courses held by a trainer
 *
 * @author Harjot Singh
 */
public class TrainerCoursesStatusRestServlet extends AbstractRestServlet {

    /**
     * Handles the get request by getting all the courses' statuses
     *
     * @param req the request
     * @param res the response
     * @throws ServletException if either the request or response are not of the expected types or any other error occurs
     * @throws IOException      if some error occurs while writing the response
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String trainerEmail = session.getAttribute("email").toString();

        try {
            TrainerService trainerService = new TrainerService(getDataSource(), trainerEmail);
            List<CourseStatus> coursesStatus = trainerService.getTrainersCoursesStatus();
            sendDataResponse(res, coursesStatus);
        } catch (SQLException | NamingException e) {
            sendErrorResponse(res, Codes.INTERNAL_ERROR);
        }
    }
}