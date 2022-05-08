package servlet.avatar;

import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import servlet.AbstractServlet;

import java.io.*;

/**
 * Servlet used to retrieve an avatar for a user
 *
 * @author Riccardo Tumiati
 */
public class RetrieveAvatar extends AbstractServlet {

    /**
     * Handles the get request by retrieving the avatar image of a user
     * @param req  the request
     * @param res  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN);
            return;
        }

        final String avatarPath = session.getAttribute("avatarPath").toString()
                .replace('/', File.separatorChar);
        final String[] s_avatarPath =  avatarPath.split("\\.");
        final String format = s_avatarPath[s_avatarPath.length - 1];

        res.setContentType("image/"+format);
        File file = new File(avatarPath);
        res.setContentLength((int)file.length());

        try {
            FileInputStream in = new FileInputStream(file);
            OutputStream out = res.getOutputStream();

            // Copy the contents of the file to the output stream
            byte[] buf = new byte[1024];
            int count = 0;
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }
            out.close();
            in.close();
        }
        catch(FileNotFoundException e) {
            req.getRequestDispatcher(Constants.RELATIVE_URL_HOME).forward(req, res);
        }
    }
}
