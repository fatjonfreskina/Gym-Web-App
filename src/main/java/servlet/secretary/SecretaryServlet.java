package servlet.secretary;

import constants.Constants;
import dao.person.GetListOfTeachersDatabase;
import dao.room.GetListRoomsDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Person;
import resource.Room;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SecretaryServlet extends AbstractServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Constants.PATH_SECRETARY_HOME).forward(request, response);
    }
}
