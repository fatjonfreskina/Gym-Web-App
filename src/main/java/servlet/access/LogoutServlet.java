package servlet.access;

import java.io.IOException;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.AbstractServlet;

/**
 * @author Harjot Singh
 */
public class LogoutServlet extends AbstractServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    req.getSession().invalidate();
    res.sendRedirect(Constants.RELATIVE_URL_HOME);
  }
}
