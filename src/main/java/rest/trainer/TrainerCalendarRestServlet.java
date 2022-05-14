package rest.trainer;

import constants.Codes;
import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.LectureTimeSlot;
import service.TrainerService;
import servlet.AbstractRestServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 * Rest servlet used to get the calendar for a trainer
 *
 * @author Harjot Singh
 */
public class TrainerCalendarRestServlet extends AbstractRestServlet {

    /**
     * Handles the get request by providing the schedules of courses held by a trainer
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
            // GET addWeeks PARAMETER FROM REQUEST
            String[] addWeeksParameter = req.getParameterValues(Constants.URL_PARAMETER_TRAINER_HOME_PAGE_ADD_WEEKS);
            int addWeeks = 0;
            if (addWeeksParameter != null) {
                if (addWeeksParameter.length > 0) addWeeks = Integer.parseInt(addWeeksParameter[0]);
            }
            LocalDate fromLocalDate = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).plusDays(7 * addWeeks);
            Date fromDate = Date.valueOf(fromLocalDate);
            Date toDate = Date.valueOf(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).plusDays(7 * addWeeks));
            // PERFORM ACTION
            List<LectureTimeSlot> allLessonsInThisWeek = trainerService.getAllLessonsInRange(fromDate, toDate);
            // SEND RESPONSE
            sendDataResponse(res, allLessonsInThisWeek);
        } catch (SQLException | NamingException e) {
            sendErrorResponse(res, Codes.INTERNAL_ERROR);
        }

    }
}
