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
public class SecretaryFilter extends AbstractFilter {

  private final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = "gwa.filter.SecretaryFilter: ";

  @Override
  public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

    HttpSession session = req.getSession(false);
    boolean loggedIn = session != null && session.getAttribute("email") != null;

    logger.debug(loggerClass + "Filter for Secretary");
    if (loggedIn) {
      List<String> roles = (List<String>) session.getAttribute("roles");
      if (roles.contains("secretary")) {
        ////////////////////////////////////////////////////////////////
        if(!session.getAttribute("defaultRole").equals("secretary")){
          session.setAttribute("defaultRole","secretary");
        }
        ///////////////////////////////////////////////////////////////
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        chain.doFilter(req, res); // User is logged in, just continue request.
      } else {
        logger.info(loggerClass + "unauthorized user " + session.getAttribute("email") +
            " with roles " + roles +
            " tried to access the secretary's sections");
        res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_UNAUTHORIZED); //Not authorized, show the proper page
      }
    } else {
      logger.info(loggerClass + "User not logged it");
      res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN); // Not logged in, show login page.
    }
  }
}
