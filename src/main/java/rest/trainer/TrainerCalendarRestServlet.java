package rest.trainer;

import constants.Codes;
import constants.Constants;
import dao.lecturetimeslot.GetLectureTimeSlotsByCourseDatabase;
import dao.lecturetimeslot.GetLectureTimeSlotsInRangeDatabase;
import dao.subscription.GetSubscriptionsByCourseDatabase;
import dao.teaches.GetTeachesByTrainerDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.CourseEdition;
import resource.LectureTimeSlot;
import resource.Person;
import resource.Teaches;
import resource.view.CourseStatus;
import resource.view.Trainer;
import service.TrainerService;
import servlet.AbstractRestServlet;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Harjot Singh
 */
public class TrainerCalendarRestServlet extends AbstractRestServlet {

  private static final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = "gwa.rest.trainer.TrainerCalendarRestServlet: ";

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
        logger.debug(loggerClass + "addWeeksParameter:" + Integer.parseInt(addWeeksParameter[0]));
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
      logger.error(loggerClass + e.getMessage());
      sendErrorResponse(res, Codes.INTERNAL_ERROR);

    }
  }
}
