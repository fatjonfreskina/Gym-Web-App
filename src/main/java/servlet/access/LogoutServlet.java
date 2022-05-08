package servlet.access;

import java.io.IOException;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.AbstractServlet;

/**
 * Servlet used to logout
 * @author Harjot Singh
 */
public class LogoutServlet extends AbstractServlet {

  /**
   * Handles the get request by invalidating the user session and redirecting him/her
   * to the not-logged home area of our web-app
   *
   * @param req  the request
   * @param res  the response
   * @throws ServletException if some internal error happens
   * @throws IOException if it was not possible to forward the request and write the response
   */
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    req.getSession().invalidate();
    res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_HOME);
  }
}
