package servlet;

import constants.Constants;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The class extending {@code HttpServlet} used in order to be extended
 * by all the servlets not working with Rest
 */
public class AbstractServlet extends HttpServlet {

    private static DataSource ds = null;

    /**
     * Initializes the servlet instance
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**
     * Destroys the servlet instance
     */
    @Override
    public void destroy() {
        super.destroy();
    }

    /**
     * Returns the {@code Datasource}. If it is not initialized it initializes it
     * @return the {@code Datasource}
     * @throws NamingException
     */
    public DataSource getDataSource() throws NamingException {
        if (ds == null) {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup(Constants.DATASOURCE);
        }
        return ds;
    }

    /**
     * Returns the connection to the {@code Datasource}
     * @return  the connection to the {@code Datasource}
     * @throws SQLException
     * @throws NamingException
     */
    public Connection getConnection() throws SQLException, NamingException {
        return getDataSource().getConnection();
    }
}