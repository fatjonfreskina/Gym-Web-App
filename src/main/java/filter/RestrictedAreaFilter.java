package filter;

import constants.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @author Harjot Singh
 *
 * @author  AvatarFilter PersonalInfoFilter
 */
public class RestrictedAreaFilter extends AbstractFilter {

  private final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = this.getClass().getCanonicalName() + ": ";

  @Override
  public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
    logger.trace(loggerClass + "Filter for all restricted area which requires login");

    HttpSession session = req.getSession(false);
    logger.trace(loggerClass + "URI: " + req.getRequestURI());
    boolean isRest = req.getRequestURI().contains("rest");
    logger.trace(loggerClass + "isRest: " + isRest);
    boolean loggedIn = session != null && session.getAttribute("email") != null;
    if (loggedIn) {
      logger.debug(loggerClass + "Logged in user: " + session.getAttribute("email"));
      res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
      res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
      chain.doFilter(req, res); // User is logged in, just continue request.
    } else {
      logger.info(loggerClass + "User not logged in");
      if (isRest) sendRestResponse(res, HttpServletResponse.SC_UNAUTHORIZED, "User not logged in!");
      else res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN); // Not logged in, show login page.
    }
  }
}
