package servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AbstractServlet extends HttpServlet {

  private static DataSource ds = null;

  //override the init method: here you should put the initialization of your servlet
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  //override destroy method: here you should put the behaviour of your servlet when destroyed
  @Override
  public void destroy(){
    super.destroy();
  }

  public DataSource getDataSource() throws NamingException {

    // we don't want to initialize a new datasoruce everytime, so, we check first that ds is null
    if (ds == null) {

      //we get the context
      InitialContext ctx = new InitialContext();

      //and use the proper resource to initialize the datasource
      ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/gwa-res");
    }
    return ds;
  }
}
