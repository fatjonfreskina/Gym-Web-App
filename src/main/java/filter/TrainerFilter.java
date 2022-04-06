package filter;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class TrainerFilter extends HttpFilter {

  @Override
  public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

    HttpSession session = req.getSession(false);
    String loginURI = "/login";
    String unauthorizedPage = req.getContextPath() + "/unauthorized";

    boolean loggedIn = session != null && session.getAttribute("email") != null;

    //System.out.println("Trainer filter entered");
    if (loggedIn) {
      List roles = (List) session.getAttribute("roles");
      System.out.println(roles);
      if (roles.contains("trainer")) {
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        chain.doFilter(req, res); // User is logged in, just continue request.
      }
      else {
        System.out.println("user "+session.getAttribute("email")+
            " with role "+session.getAttribute("role")+
            " tried to access the admin page");
        res.sendRedirect(unauthorizedPage); //Not authorized, show the proper page
      }
    } else {
      res.sendRedirect(loginURI); // Not logged in, show login page.
    }
  }
}
