package servlet.secretary;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.AbstractServlet;

import java.io.IOException;

public class SecretaryServlet extends AbstractServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.getRequestDispatcher(Constants.PATH_SECRETARY_HOME).forward(request,response);
    }


}
