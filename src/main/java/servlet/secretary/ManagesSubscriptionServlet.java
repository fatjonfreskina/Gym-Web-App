package servlet.secretary;

import com.google.gson.Gson;
import constants.Codes;
import constants.Constants;
import dao.courseedition.GetAvailableCoursesDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Course;
import resource.Message;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class ManagesSubscriptionServlet extends AbstractServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Codes error = Codes.OK;

        List<Course> courses = null;

        try
        {
            courses = (new GetAvailableCoursesDatabase(getDataSource().getConnection())).execute();
            request.setAttribute("courses",courses);
        }catch (SQLException | NamingException e)
        {
            error = Codes.INTERNAL_ERROR;
        }
        if(error != Codes.OK)
        {
            String messageJson = new Gson().toJson(new Message(error.getErrorMessage(), true));
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            out.print(messageJson);
        }else
        {
            request.getRequestDispatcher(Constants.PATH_SECRETARY_MANAGES_SUBSCRIPTION).forward(request,response);
        }
    }


}
