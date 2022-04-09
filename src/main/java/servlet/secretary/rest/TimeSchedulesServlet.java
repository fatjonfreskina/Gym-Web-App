package servlet.secretary.rest;

import com.google.gson.Gson;
import constants.ErrorCodes;
import dao.lecturetimeslot.GetLastLecureForLectureTimeSlotDatabase;
import dao.lecturetimeslot.GetListTimeScheduleForLectureTimeSlotDatabase;
import dao.person.GetListPersonByLikeEmailDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.CourseEdition;
import resource.LectureTimeSlot;
import resource.Message;
import resource.Person;
import resource.view.GeneralWeekHours;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Francesco Caldivezzi
 * */
public class TimeSchedulesServlet extends AbstractServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        ErrorCodes error = ErrorCodes.OK;
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
                error = ErrorCodes.INTERNAL_ERROR;
            }
        }
        if(error == ErrorCodes.OK)
        {
            //no error
            out.print(json);
        }else
        {
            out.print(new Gson().toJson(new Message(error.getErrorMessage(), true)));
        }
    }


}
