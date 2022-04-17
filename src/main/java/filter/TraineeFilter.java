package filter;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @author Harjot Singh
 */
public class TraineeFilter extends AbstractFilter {

  private final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = this.getClass().getCanonicalName() + ": ";

  @Override
  public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
    logger.trace(loggerClass + "Filter for Trainee's restricted area");

    HttpSession session = req.getSession(false);
    logger.trace(loggerClass + "URI: " + req.getRequestURI());
    boolean isRest = req.getRequestURI().contains("rest");
    logger.trace(loggerClass + "isRest: " + isRest);

    List<String> roles = (List<String>) session.getAttribute("roles");
    if (roles.contains("trainee")) {
      ////////////////////////////////////////////////////////////////
      if (!session.getAttribute("defaultRole").equals("trainee")) {
        session.setAttribute("defaultRole", "trainee");
      }
      ///////////////////////////////////////////////////////////////
      res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
      res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
      chain.doFilter(req, res); // User is logged in, just continue request.
    } else {
      logger.warn(loggerClass + "unauthorized user " + session.getAttribute("email") + " with roles " + roles + " tried to access the trainee's sections");
      if (isRest)
        sendRestResponse(res, HttpServletResponse.SC_FORBIDDEN, "Unauthorized User: user does not have enough privileges to perform the action!");
      else
        req.getRequestDispatcher(Constants.PATH_UNAUTHORIZED).forward(req, res); //Not authorized, show the proper page
    }
  }
}
