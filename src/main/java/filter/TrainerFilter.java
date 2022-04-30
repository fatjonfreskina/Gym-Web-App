package filter;

import constants.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * @author Harjot Singh
 */
public class TrainerFilter extends AbstractFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        boolean isRest = req.getRequestURI().contains("rest");

        List<String> roles = (List<String>) session.getAttribute("roles");
        if (roles.contains("trainer")) {
            if (!session.getAttribute("defaultRole").equals("trainer")) {
                session.setAttribute("defaultRole", "trainer");
            }
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            chain.doFilter(req, res); // User is logged in, just continue request.
        } else {
            if (isRest)
                sendRestResponse(res, HttpServletResponse.SC_FORBIDDEN, "Unauthorized User: user does not have enough privileges to perform the action!");
            else
                req.getRequestDispatcher(Constants.PATH_UNAUTHORIZED).forward(req, res); //Not authorized, show the proper page
        }
    }
}
