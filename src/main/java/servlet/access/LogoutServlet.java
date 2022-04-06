package servlet.access;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.AbstractServlet;

public class LogoutServlet extends AbstractServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    req.getSession().invalidate();
    res.sendRedirect("/");
  }
}
