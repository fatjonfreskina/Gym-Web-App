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

/**
 * Servlet handling requests for the Secretary home page
 */
public class SecretaryServlet extends AbstractServlet {
    /**
     * Handles the get request by retrieving the different rooms and teachers
     *
     * @param request  the request
     * @param response  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Person> teachers = null;
        try {
            teachers = (new GetListOfTeachersDatabase(getDataSource().getConnection())).execute();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        List<Room> rooms = null;
        try {
            rooms = (new GetListRoomsDatabase(getDataSource().getConnection()).execute());
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        request.setAttribute("rooms", rooms);
        request.setAttribute("teachers", teachers);
        request.getRequestDispatcher(Constants.PATH_SECRETARY_HOME).forward(request, response);
    }
}
