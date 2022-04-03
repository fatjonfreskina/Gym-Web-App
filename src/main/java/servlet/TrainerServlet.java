package servlet;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author Riccardo Forzan
 */
public class TrainerServlet extends AbstractServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        //req.setAttribute("", null);
        req.getRequestDispatcher(Constants.PATH_TRAINER_HOME).forward(req, res);
    }

}
