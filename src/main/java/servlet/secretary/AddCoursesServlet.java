package servlet.secretary;

import constants.Constants;
import constants.ErrorCodes;
import dao.courseedition.GetAvailableCourses;
import dao.person.GetListOfTeachersDatabase;
import dao.room.GetListRoomsDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Course;
import resource.Person;
import resource.Room;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddCoursesServlet extends AbstractServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //request.getRequestDispatcher(Constants.PATH_SECRETARY_ADD_COURSES).forward(request,response);
        //All the courses?
        //Tutti insegnati
        //Tutte le stanze
        ErrorCodes error = ErrorCodes.OK;

        List<Room> rooms = null;
        List<Course> courses = null;
        List<Person> teachers = null;
        try
        {
            rooms = (new GetListRoomsDatabase(getDataSource().getConnection()).execute());
            request.setAttribute("rooms",rooms);
            courses = (new GetAvailableCourses(getDataSource().getConnection())).execute();
            request.setAttribute("courses",courses);
            teachers = (new GetListOfTeachersDatabase(getDataSource().getConnection())).execute();
            //stabilire se solo email?? Informazioni pericolose
            request.setAttribute("teachers",teachers);
        }catch (SQLException | NamingException e)
        {
            error = ErrorCodes.INTERNAL_ERROR;
        }
        if(error != ErrorCodes.OK)
        {
            //error page error
        }else
        {
            request.getRequestDispatcher(Constants.PATH_SECRETARY_ADD_COURSES).forward(request,response);
        }



    }


}