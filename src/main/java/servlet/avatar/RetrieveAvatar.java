package servlet.avatar;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servlet.AbstractServlet;

import java.io.IOException;

public class RetrieveAvatar extends AbstractServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null)
            res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN);

        final String avatarPath = (String) session.getAttribute(Constants.AVATAR);
        System.out.println("CIAO");
    }
}
