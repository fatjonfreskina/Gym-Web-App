package servlet;

import constants.Constants;
import dao.subscriptiontype.GetListForPrices;
import dao.subscriptiontype.GetListSubscriptionTypeDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.SubscriptionType;
import resource.view.PricesView;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Francesco Caldivezzi
 */
public class PricesServlet extends AbstractServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<PricesView> list = null;
        try {
            list = new GetListForPrices(getDataSource().getConnection()).execute();
        } catch (SQLException | NamingException ex) {

        }
        req.setAttribute("pricesView", list);
        req.getRequestDispatcher(Constants.PATH_PRICES).forward(req, res);

    }
}
