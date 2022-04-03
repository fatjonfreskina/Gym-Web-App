package servlet;

import constants.Constants;
import dao.GetListSubscriptionTypeDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.SubscriptionType;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
//import

public class PricesServlet extends AbstractServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        List<SubscriptionType> list = null;
        try
        {
            list = new GetListSubscriptionTypeDatabase(getDataSource().getConnection()).execute();
        }
        catch (SQLException ex)
        {

        }
        catch (NamingException ex)
        {

        }
        req.setAttribute("subscriptionTypeList", list);
        req.getRequestDispatcher(Constants.PATH_PRICES).forward(req, res);

    }
}
