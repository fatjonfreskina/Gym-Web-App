package servlet;

import dao.GetListSubscriptionTypeDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.SubscriptionType;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PricesServlet extends AbstractServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {

        List<SubscriptionType> list = null;

        try
        {
            list = new GetListSubscriptionTypeDatabase(getDataSource().getConnection()).getListSubscriptionType();







        } catch (NumberFormatException ex)
        {


        } catch (SQLException ex)
        {


        }catch (NamingException ex)
        {

        }
        req.setAttribute("pricesList", list);
        req.getRequestDispatcher("/jsp/prices.jsp").forward(req, res);
    }
}
