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
 * Ensure that the user has logged in before accessing /personal_info page.
 * @author Marco Alessio
 */
public class PersonalInfoFilter extends AbstractFilter
{
    private static final Logger LOGGER = LogManager.getLogger("marco_alessio_logger");
    private static final String CLASS = "gwa.filter.PersonalInfoFilter: ";


    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException
    {
        HttpSession session = req.getSession(false);
        String email;

        if ((session == null) || ((email = (String) session.getAttribute("email")) == null))
        {
            LOGGER.info(CLASS + "Request to /personal_info while not logged in yet. Redirected to /login.");

            res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN);
        }
        else
        {
            LOGGER.info(CLASS + "Request to /personal_info received by user: \"" + email + "\".");

            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            chain.doFilter(req, res); // User is logged in, just continue request.
        }
    }
}