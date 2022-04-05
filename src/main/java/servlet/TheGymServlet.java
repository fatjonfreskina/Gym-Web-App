package servlet;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class TheGymServlet extends AbstractServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    /*try {
      DataSource dataSource = getDataSource();
      //? SELECT Name, Slots FROM Room
    } catch (NamingException e) {
      System.out.println("Error " + e.getMessage());
      e.printStackTrace();
    }*/
    req.getRequestDispatcher(Constants.PATH_THE_GYM).forward(req, res);
  }
}
