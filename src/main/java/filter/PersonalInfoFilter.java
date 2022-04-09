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

        if ((session == null) || ((email = (String) session.getAttribute(Constants.EMAIL)) == null))
        {
            LOGGER.info(CLASS + "Request to /personal_info while not logged in yet. Redirected to /login.");

            /* READ ME:
             * Trying to access /personal_info while NOT logged in (can happen if manually requesting
             * /personal_info or in case of page reload while in /personal_info and session has expired, due to
             * timeout or logout in another browser tab) result in a redirection to /login,
             * in order to let the user log into his/her account. After the login is successful,
             * the user expects to be automatically redirected to the page he/she originally requested.
             * That's why I decided to add a new attribute to the request called "redirect".
             * Probably it is not the best or smartest way to do it, you are free to change it with
             * something better.
             * @author Marco Alessio
             */
            //session.setAttribute("redirect", Constants.RELATIVE_URL_PERSONALINFO);
            req.setAttribute("redirect", Constants.RELATIVE_URL_PERSONALINFO);

            req.getRequestDispatcher(Constants.RELATIVE_URL_LOGIN).forward(req, res);
            //res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN);
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