package servlet.access;


import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.AbstractServlet;

import java.io.IOException;

public class ConfirmRegistrationServlet extends AbstractServlet
{

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        req.getRequestDispatcher(Constants.PATH_CONFIRM_REGISTRATION).forward(req, res);
    }
}
