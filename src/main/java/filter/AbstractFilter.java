package filter;

import com.google.gson.Gson;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletResponse;
import resource.Message;

import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author Harjot Singh
 * @author Francesco Caldivezzi
 */
public class AbstractFilter extends HttpFilter {

  @Override
  public void init(FilterConfig config) throws ServletException {

  }

  @Override
  public void destroy() {

  }

  protected void sendRestResponse(HttpServletResponse res, int errorCode, String message) throws IOException {
    // Set headers of the response.
    res.setContentType("application/json");
    res.setCharacterEncoding("utf-8");

    // Set HTTP error code.
    res.setStatus(errorCode);

    // Write the output.
    final PrintWriter out = res.getWriter();
    out.print(new Gson().toJson(new Message(message, true)));

    // Flush the output stream and close it.
    out.flush();
    out.close();
  }

}