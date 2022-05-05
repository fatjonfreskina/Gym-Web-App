package rest.trainee;

import constants.Codes;
import constants.Constants;
import constants.exceptions.TraineeNotFound;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;
import resource.Person;
import resource.rest.Trainee;
import service.TraineeService;
import service.TrainerService;
import servlet.AbstractRestServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Harjot Singh
 * /rest/trainee/{email}
 * allowed trainer to access
 */
public class TraineeRestServlet extends AbstractRestServlet {

  private static final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = this.getClass().getCanonicalName() + ": ";
  private static final Pattern URI_REGEX = Pattern.compile(
      "/rest/trainee/(.*)", Pattern.DOTALL);

  /**
   *
   * @param req The HTTP request.
   * @param res The HTTP response.
   * @throws ServletException
   * @throws IOException
   */

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    try {
      TraineeService traineeService = new TraineeService(getDataSource());

      // GET trainee's email PARAMETER FROM REQUEST URI.
      Matcher matcher = URI_REGEX.matcher(req.getRequestURI());
      if ((!matcher.find()) || (matcher.groupCount() != 1)) {
        sendErrorResponse(res, Codes.BAD_REQUEST);
      } else {
        String traineeEmail = matcher.group(1);

        // PERFORM ACTION
        Trainee trainee = traineeService.getTraineeByEmail(traineeEmail);

        // SEND RESPONSE
        sendDataResponse(res, trainee);
      }
    } catch (SQLException |
        NamingException e) {
      logger.error(loggerClass + e.getMessage());
      sendErrorResponse(res, Codes.INTERNAL_ERROR);
    } catch (
        TraineeNotFound e) {
      logger.error(loggerClass + e.getErrorCode());
      sendErrorResponse(res, e.getErrorCode());
    }
  }
}