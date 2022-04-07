package filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.TypeOfRoles;

/**
 * @author Harjot Singh
 */
public class TrainerFilter extends AbstractFilter {

    private final Logger logger = LogManager.getLogger("harjot_singh_logger");
    private final String loggerClass = "gwa.filter.TrainerFilter: ";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        logger.debug(loggerClass + "Filter for Trainer");

        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("email") != null;
        if (loggedIn) {
            List<TypeOfRoles> rolesAsObj = (List<TypeOfRoles>) session.getAttribute("roles");
            List<String> roles = new ArrayList<>();
            for (TypeOfRoles role : rolesAsObj) roles.add(role.getRole());
            if (roles.contains("trainer")) {
                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                chain.doFilter(req, res); // User is logged in, just continue request.
            } else {
                logger.info(loggerClass + "unauthorized user " + session.getAttribute("email") +
                        " with roles " + roles +
                        " tried to access the trainer's sections");
                res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_UNAUTHORIZED); //Not authorized, show the proper page
            }
        } else {
            logger.info(loggerClass + "User not logged it");
            res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN); // Not logged in, show login page.
        }
    }
}
