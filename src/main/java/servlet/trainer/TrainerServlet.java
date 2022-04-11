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
import servlet.AbstractServlet;

/**
 * @author Harjot Singh
 */
public class TrainerServlet extends AbstractServlet {

  private static final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = "gwa.servlet.trainer.TrainerServlet: ";

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      String[] addWeeksParameter = req.getParameterValues(Constants.URL_PARAMETER_TRAINER_HOME_PAGE_ADD_WEEKS);

      HttpSession session = req.getSession(false);
      String trainerEmail = session.getAttribute("email").toString();
      logger.debug(loggerClass + "Trainer " + trainerEmail);

      List<Teaches> coursesHeld = new GetTeachesByTrainerDatabase(getDataSource().getConnection(), new Person(trainerEmail, null, null, null, null, null, null, null, null)).execute();
      logger.debug(loggerClass + "Courses held by trainer " + coursesHeld);

      List<CourseStatus> courseStatuses = getCourseStatues(coursesHeld);
      logger.debug(loggerClass + courseStatuses);

      int addWeeks = 0;
      if (addWeeksParameter != null)
        if (addWeeksParameter.length > 0)
          addWeeks = Integer.parseInt(addWeeksParameter[0]);

      LocalDate fromLocalDate = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).plusDays(7 * addWeeks);
      Date fromDate = Date.valueOf(fromLocalDate);
      Date toDate = Date.valueOf(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).plusDays(7 * addWeeks));
      List<LectureTimeSlot> allLessonsInThisWeek = getAllLessonsInRange(coursesHeld, fromDate, toDate);

      String week[] = new String[7];
      for (int i = 0; i < 7; i++) {
        week[i] = fromLocalDate.plusDays(i).toString();
      }

      req.setAttribute("courseStatuses", courseStatuses);
      req.setAttribute("slots", getWeeklySlotsFromLectureTimeSlots(allLessonsInThisWeek));
      req.setAttribute("week", week);
      req.setAttribute("addWeeks", addWeeks);
    } catch (SQLException | NamingException e) {
      logger.error(e.getMessage());
    }
    req.getRequestDispatcher(Constants.PATH_TRAINER_HOME).forward(req, resp);
  }

  private List<CourseStatus> getCourseStatues(List<Teaches> coursesHeld) throws NamingException, SQLException {
    List<CourseStatus> courseStatuses = new ArrayList<>();
    for (Teaches c : coursesHeld) {
      List<LectureTimeSlot> allLectures = new GetLectureTimeSlotsByCourseDatabase(getDataSource().getConnection(), new LectureTimeSlot(null, null, null, c.getCourseEdition(), c.getCourseName(), null)).execute();
      int sum = 0;
      for (LectureTimeSlot cur : allLectures) {
        Date beforeDate = Date.valueOf(LocalDate.now().plusDays(0));//"2022-05-12");//
        Time beforeTime = Time.valueOf(LocalTime.now());
        if (cur.getDate().compareTo(beforeDate) < 0 || (cur.getDate().compareTo(beforeDate) == 0 && cur.getStartTime().before(beforeTime))) {
          sum++;
        }
      }
      int traineesNumber = new GetSubscriptionsByCourseDatabase(getDataSource().getConnection(), new CourseEdition(c.getCourseEdition(), c.getCourseName())).execute().size(), currentLessonNumber = sum, totalLessonsNumber = allLectures.size();
      courseStatuses.add(new CourseStatus(c.getCourseName(), c.getCourseEdition(), traineesNumber, currentLessonNumber, totalLessonsNumber));
    }
    return courseStatuses;
  }

  private List<LectureTimeSlot> getAllLessonsInRange(List<Teaches> coursesHeld, Date from, Date to) throws NamingException, SQLException {
    List<LectureTimeSlot> allLessonsInThisWeek = new ArrayList<>();
    for (Teaches c : coursesHeld) {
      List<LectureTimeSlot> curCourseWeek = new GetLectureTimeSlotsInRangeDatabase(getDataSource().getConnection(), from, to).execute();
      curCourseWeek.forEach(l -> {
        if (l.getCourseName().equals(c.getCourseName()) && l.getCourseEditionId() == c.getCourseEdition()) {
          allLessonsInThisWeek.add(l);
        }
      });
    }
    return allLessonsInThisWeek;
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
