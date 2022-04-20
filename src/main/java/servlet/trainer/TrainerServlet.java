package servlet.trainer;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

    try {
      TrainerService trainerService = new TrainerService(getDataSource(), trainerEmail);
      req.setAttribute("coursesStatus", trainerService.getTrainersCoursesStatus());
    } catch (SQLException | NamingException e) {
      logger.error(loggerClass + e.getMessage());
    }
    req.getRequestDispatcher(Constants.PATH_TRAINER_HOME).forward(req, resp);
  }
}
