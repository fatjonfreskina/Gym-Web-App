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

        Logger logger = LogManager.getLogger("francesco_caldivezzi_logger");
        logger.info("SONO QUI");
        try
        {

            list = new GetListSubscriptionTypeDatabase(getDataSource().getConnection()).getListSubscriptionType();
            logger.info(list.size());

            for ( SubscriptionType l : list)
            {
                logger.info(l.getCourseName());
            }






        } catch (NumberFormatException ex)
        {
            logger.info(ex.getMessage());

        } catch (SQLException ex)
        {
            logger.info(ex.getMessage());

        }catch (NamingException ex)
        {
            logger.info(ex.getMessage());
        }
        req.setAttribute("subscriptionTypeList", list);
        req.getRequestDispatcher(Constants.PATHPRICESJSP).forward(req, res);
    }
}
