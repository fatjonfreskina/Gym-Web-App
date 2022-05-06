package filter;

import constants.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * This class represents a filter for a restricted area
 *
 * @author Harjot Singh
 * @author AvatarFilter PersonalInfoFilter
 */
public class RestrictedAreaFilter extends AbstractFilter {

    /**
     * Filters the requests based on their authorization
     * @param req  the request
     * @param res  the response
     * @param chain  the chain of filters
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        boolean isRest = req.getRequestURI().contains("rest");
        boolean loggedIn = session != null && session.getAttribute("email") != null;
        if (loggedIn) {
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            chain.doFilter(req, res); // User is logged in, just continue request.
        } else {
            if (isRest) sendRestResponse(res, HttpServletResponse.SC_UNAUTHORIZED, "User not logged in!");
            else
                res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN); // Not logged in, show login page.
        }
    }
}
