package rest.trainer;

import constants.Codes;
import constants.Constants;
import dao.lecturetimeslot.GetLectureTimeSlotsInRangeDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;
import resource.Teaches;
import resource.view.CourseStatus;
import service.TrainerService;
import servlet.AbstractRestServlet;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Harjot Singh
 */
public class TrainerCoursesStatusRestServlet extends AbstractRestServlet {

  private static final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = this.getClass().getCanonicalName() + ": ";

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    String trainerEmail = session.getAttribute("email").toString();

    try {
      TrainerService trainerService = new TrainerService(getDataSource(), trainerEmail);

      List<CourseStatus> coursesStatus = trainerService.getTrainersCoursesStatus();
      logger.debug(loggerClass + coursesStatus);//first table
      sendDataResponse(res, coursesStatus);
    } catch (SQLException | NamingException e) {
      sendErrorResponse(res, Codes.INTERNAL_ERROR);
    }
  }
}