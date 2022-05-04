package servlet.secretary.rest;

import com.google.gson.Gson;
import constants.Codes;
import dao.lecturetimeslot.GetLastLecureForLectureTimeSlotDatabase;
import dao.lecturetimeslot.GetListTimeScheduleForLectureTimeSlotDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.CourseEdition;
import resource.LectureTimeSlot;
import resource.Message;
import resource.view.GeneralWeekHours;
import servlet.AbstractRestServlet;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Rest servlet used to get the schedule for a given course
 *
 * @author Francesco Caldivezzi
 * */
public class TimeSchedulesServlet extends AbstractServlet
{
    /**
     * Handles the get request by retrieving the schedule for a given course
     *
     * @param request  the request
     * @param response  the response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Codes error = Codes.OK;
        String coursename = request.getParameter("course_name");
        Set<String> json = new HashSet<>();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        if(coursename != null && !"".equals(coursename))
        {
            try
            {
                List<GeneralWeekHours> list = new GetListTimeScheduleForLectureTimeSlotDatabase(getDataSource().getConnection(),new CourseEdition(-1,coursename)).execute();
                for (GeneralWeekHours lecture: list)
                {
                    LectureTimeSlot lts = new GetLastLecureForLectureTimeSlotDatabase(getDataSource().getConnection(),
                            new CourseEdition(lecture.getCourseEditionId(),null)).execute();
                    if(lts != null)
                        json.add(new Gson().toJson(lts));
                }
            } catch (SQLException | NamingException e)
            {
                error = Codes.INTERNAL_ERROR;
            }
        }
        if(error == Codes.OK)
        {
            out.print(json);
        }else
        {
            out.print(new Gson().toJson(new Message(error.getErrorMessage(), true)));
        }
        out.flush();
        out.close();
    }
}
