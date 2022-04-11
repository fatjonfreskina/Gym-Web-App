package servlet.secretary.rest;

import com.google.gson.Gson;
import constants.ErrorCodes;
import dao.person.InsertPersonSubscriptionDatabase;
import dao.subscription.GetFreeSubscriptionByTraineeAndCourseDatabase;
import dao.subscription.GetValidSubscriptionByCourseAndTraineeDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.*;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AddSubscriptionServlet extends AbstractServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        ErrorCodes error = ErrorCodes.OK;
        String trainee = null;
        String courseName = null;
        Integer courseEditionId = null;
        Integer discount = null;
        Integer duration= null;

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        if((error = parseParams(req,resp)) == ErrorCodes.OK)
        {
            trainee = req.getParameter("trainee");
            courseName = req.getParameter("course_name");
            try
            {
                courseEditionId = Integer.parseInt(req.getParameter("course_edition_id"));
                discount = Integer.parseInt(req.getParameter("discount"));//0-100
                duration = Integer.parseInt(req.getParameter("duration")); //7-30
            }catch (NumberFormatException exception)
            {
                //discount non assinged => not a problem
            }

            try
            {
                if(duration == 7)
                {
                    List<Subscription> lst =  new GetFreeSubscriptionByTraineeAndCourseDatabase(getDataSource().getConnection(),new Course(courseName,null),new Person(trainee,null,null,null,null,null,null,null,null)).execute();
                    if(!lst.isEmpty())
                        error = ErrorCodes.FREE_TRIAL_ALREADY_DONE;
                    //look if it is
                    //GetFreeSuscriptionByTrainee
                    //GetValidSubscrtio By CourseandTrainee
                }else
                {
                    List<Subscription> lst = new GetValidSubscriptionByCourseAndTraineeDatabase(getDataSource().getConnection(),
                            new CourseEdition(courseEditionId,courseName),new Person(trainee,null,
                            null,null,null,null,null,null,null)).execute();
                    if(!lst.isEmpty())
                        error = ErrorCodes.OVELAPPING_SUBSCRIPTIONS;
                }

                if(error == ErrorCodes.OK)
                {
                    discount = discount == null ? 0 : discount;
                    new InsertPersonSubscriptionDatabase(getDataSource().getConnection(),
                            new Subscription(courseEditionId,courseName,duration,new Date(System.currentTimeMillis()),discount,trainee)).execute();
                }

            } catch (SQLException | NamingException e) {
                error = ErrorCodes.INTERNAL_ERROR;
            }
        }

        if(error == ErrorCodes.OK)
            out.print(new Gson().toJson(new Message(error.getErrorMessage(), true)));
        else
            out.print(new Gson().toJson(new Message(error.getErrorMessage(), false)));


    }

    //parametri attesi : email, durata, corso, courseid, discount, startday(oggi) checkboxato,
    private ErrorCodes parseParams(HttpServletRequest req, HttpServletResponse res)
    {
        ErrorCodes error = ErrorCodes.OK;
        String trainee = null;
        String courseName = null;
        String courseEditionId = null;
        String discount = null;
        String duration = null;

        Integer courseEditionIdInteger = null;
        Integer discountInteger = null;
        Integer durationInteger = null;
        try
        {
            trainee = req.getParameter("trainee");
            courseName = req.getParameter("course_name");

            courseEditionId = req.getParameter("course_edition_id");
            discount = req.getParameter("discount");//0-100
            duration = req.getParameter("duration"); //7-30

            if(trainee == null || "".equals(trainee) ||
                    courseName == null || "".equals(courseName) || courseEditionId == null || "".equals(courseEditionId) || duration == null || "".equals(duration))
            {
                error = ErrorCodes.INVALID_FIELDS;
            }else
            {
                if(discount != null && !"".equals(discount))
                {
                    discountInteger = Integer.parseInt(discount);
                    if(discountInteger < 0 || discountInteger > 100)
                        error = ErrorCodes.INVALID_FIELDS;
                }

                if(error == ErrorCodes.OK)
                {
                    courseEditionIdInteger = Integer.parseInt(courseEditionId);
                    durationInteger = Integer.parseInt(duration);

                    //integrity check
                    if(durationInteger != 7 && durationInteger != 30 && durationInteger != 90 && durationInteger != 180 && durationInteger != 365)
                        error = ErrorCodes.INVALID_FIELDS;
                }
            }


        }catch (NumberFormatException e)
        {
            error = ErrorCodes.INVALID_FIELDS;
        }

        return  error;
    }
}
