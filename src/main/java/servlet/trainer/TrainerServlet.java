package servlet.trainer;

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

import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.Constants;
import dao.lecturetimeslot.GetLectureTimeSlotsByCourseDatabase;
import dao.lecturetimeslot.GetLectureTimeSlotsInRangeDatabase;
import dao.subscription.GetSubscriptionsByCourseDatabase;
import dao.teaches.GetTeachesByTrainerDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import resource.CourseEdition;
import resource.LectureTimeSlot;
import resource.Person;
import resource.Teaches;
import resource.view.CourseStatus;
import service.TrainerService;
import servlet.AbstractServlet;

/**
 * @author Harjot Singh
 */
public class TrainerServlet extends AbstractServlet {

  private static final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = this.getClass().getCanonicalName() + ": ";

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    String trainerEmail = session.getAttribute("email").toString();
    logger.debug(loggerClass + "Trainer " + trainerEmail);

    try {
      TrainerService trainerService = new TrainerService(getDataSource(), trainerEmail);

      req.setAttribute("coursesStatus", trainerService.getTrainersCoursesStatus());

      // OLD CODE for calendar implementation in jsp only:
      String[] addWeeksParameter = req.getParameterValues(Constants.URL_PARAMETER_TRAINER_HOME_PAGE_ADD_WEEKS);
      int addWeeks = 0;
      if (addWeeksParameter != null)
        if (addWeeksParameter.length > 0) addWeeks = Integer.parseInt(addWeeksParameter[0]);

      LocalDate fromLocalDate = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).plusDays(7 * addWeeks);
      Date fromDate = Date.valueOf(fromLocalDate);
      Date toDate = Date.valueOf(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).plusDays(7 * addWeeks));

      List<LectureTimeSlot> allLessonsInRange = trainerService.getAllLessonsInRange(fromDate, toDate);

      String week[] = new String[7];
      for (int i = 0; i < 7; i++) {
        week[i] = fromLocalDate.plusDays(i).toString();
      }

      req.setAttribute("weeklyLTSs", allLessonsInRange);
      req.setAttribute("slots", getWeeklySlotsFromLectureTimeSlots(allLessonsInRange));
      req.setAttribute("week", week);
      req.setAttribute("addWeeks", addWeeks);
    } catch (SQLException | NamingException e) {
      logger.error(e.getMessage());
    }
    req.getRequestDispatcher(Constants.PATH_TRAINER_HOME).forward(req, resp);
  }

  private String[][] getWeeklySlotsFromLectureTimeSlots(List<LectureTimeSlot> lectureTimeSlots) {
    int slotSize = 2;//in hours
    int rows = 24 / slotSize, cols = 8;
    String slots[][] = new String[rows][cols];
    for (int k = 0; k < rows; k++) {
      slots[k][0] = String.valueOf(k * 2) + ":00 \n " + String.valueOf(k * 2 + slotSize) + ":00";
    }
    lectureTimeSlots.forEach(lesson -> {
      Calendar cal = Calendar.getInstance();
      cal.setTime(lesson.getDate());
      int day = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7; // 0=MON
      int time = lesson.getStartTime().toLocalTime().getHour() / slotSize;
      slots[time][day + 1] = lesson.getCourseName();
    });
    return slots;
  }

}
