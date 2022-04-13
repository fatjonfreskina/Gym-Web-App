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
/*
    @author Riccardo Tumiati
 */
public class RetrieveAvatar extends AbstractServlet {
    private static final Logger LOGGER = LogManager.getLogger("riccardo_tumiati_logger");

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        HttpSession session = req.getSession(false);
        if (session == null)
            res.sendRedirect(req.getContextPath() + Constants.RELATIVE_URL_LOGIN);

        final String avatarPath = (String) session.getAttribute("avatarPath");
        final String s_avatarPath[] =  avatarPath.split("\\.");
        final String format = s_avatarPath[s_avatarPath.length-1];

        res.setContentType("image/"+format);
        File file = new File(avatarPath);
        res.setContentLength((int)file.length());

        try{
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
        }catch(FileNotFoundException e){
            LOGGER.error("Avatar file not found");
            req.getRequestDispatcher(Constants.RELATIVE_URL_HOME).forward(req, res);
        }
    }
}
