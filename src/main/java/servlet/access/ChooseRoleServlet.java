package servlet.access;
import constants.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import servlet.AbstractServlet;
import java.io.IOException;

/**
 * @author Andrea Pasin
 */
public class ChooseRoleServlet  extends AbstractServlet {

    private final Logger logger = LogManager.getLogger("andrea_pasin_logger");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher(Constants.PATH_ROLES).forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (req.getParameter("trainee") != null) {
            session.setAttribute("defaultRole", "trainee");
            logger.info("SET DEFAULT ROLE TO TRAINEE");
            res.sendRedirect(req.getContextPath()+"/trainee");
            return;
        }
        if (req.getParameter("trainer") != null) {
            session.setAttribute("defaultRole", "trainer");
            logger.info("SET DEFAULT ROLE TO TRAINER");
            res.sendRedirect(req.getContextPath()+"/trainer");
            return;
        }
        if (req.getParameter("secretary") != null) {
            session.setAttribute("defaultRole", "secretary");
            logger.info("SET DEFAULT ROLE TO SECRETARY");
            res.sendRedirect(req.getContextPath()+"/secretary");
        }
    }
}
