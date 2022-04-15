package servlet.secretary.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import constants.Codes;
import dao.person.GetListPersonByLikeEmailDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.Message;
import resource.Person;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class ListLikePersonsServlet extends AbstractServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Codes error = Codes.OK;
        String partialEmail = request.getParameter("partial_email");
        String json = null;
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        if(partialEmail != null && !"".equals(partialEmail))
        {
            try
            {
                List<Person> list= new GetListPersonByLikeEmailDatabase(getDataSource().getConnection(),partialEmail).execute();
                json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(list);
            } catch (SQLException | NamingException e)
            {
                error = Codes.INTERNAL_ERROR;
            }
        }
        if(error == Codes.OK)
        {
            //no error
            out.print(json);
        }else
        {
            out.print(new Gson().toJson(new Message(error.getErrorMessage(), true)));
        }
        out.flush();
        out.close();
    }
}
