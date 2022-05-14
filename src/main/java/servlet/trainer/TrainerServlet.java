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
 * Servlet handling requests for the Trainer home page
 * @author Harjot Singh
 */
public class TrainerServlet extends AbstractServlet {


  /**
   * Handles the get request by retrieving the page with the courses taught by the trainer
   * and their statuses
   *
   * @param req  the request
   * @param resp  the response
   * @throws ServletException if some internal error happens
   * @throws IOException if it was not possible to forward the request and write the response
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    String trainerEmail = session.getAttribute("email").toString();

    try {
      TrainerService trainerService = new TrainerService(getDataSource(), trainerEmail);
      req.setAttribute("coursesStatus", trainerService.getTrainersCoursesStatus());
    } catch (SQLException | NamingException e) {
    }
    req.getRequestDispatcher(Constants.PATH_TRAINER_HOME).forward(req, resp);
  }
}
