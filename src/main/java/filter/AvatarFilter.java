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

public class AvatarFilter extends AbstractFilter{
    private static final Logger LOGGER = LogManager.getLogger("riccardo_tumiati_logger");
    private static final String CLASS = "gwa.filter.AvatarFilter: ";


    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException
    {
        HttpSession session = req.getSession(false);
        String email;

        if ((session == null) || ((email = (String) session.getAttribute(Constants.EMAIL)) == null))
        {
            LOGGER.info(CLASS + "Request to /personal_info while not logged in yet. Redirected to /login.");
            req.setAttribute("redirect", Constants.RELATIVE_URL_PERSONALINFO);

            req.getRequestDispatcher(Constants.RELATIVE_URL_LOGIN).forward(req, res);
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
