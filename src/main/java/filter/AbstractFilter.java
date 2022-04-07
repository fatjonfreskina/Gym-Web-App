package filter;

import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * Added if we need datasource for the filters
 */


public class AbstractFilter extends HttpFilter {


    //private static DataSource ds = null;

    @Override
    public void init(FilterConfig config) throws ServletException
    {

    }

    @Override
    public void destroy()
    {

    }


}
