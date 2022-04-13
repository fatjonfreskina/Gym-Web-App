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

public class AbstractServlet extends HttpServlet {

    private static DataSource ds = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    public DataSource getDataSource() throws NamingException {
        if (ds == null) {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup(Constants.DATASOURCE);
        }
        return ds;
    }

    public Connection getConnection() throws SQLException, NamingException {
        return getDataSource().getConnection();
    }
}