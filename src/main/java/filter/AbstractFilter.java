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
 * The abstract filter class. It will be extended by the specific filters
 *
 * @author Harjot Singh
 * @author Francesco Caldivezzi
 */
public class AbstractFilter extends HttpFilter {

  /**
   * Initializes this class
   * @param config  the configuration
   * @throws ServletException
   */
  @Override
  public void init(FilterConfig config) throws ServletException {

  }

  /**
   * Destroys this class
   */
  @Override
  public void destroy() {

  }

  /**
   * Sends the rest response
   * @param res  the http response
   * @param errorCode  the error code
   * @param message  the message
   * @throws IOException
   */
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