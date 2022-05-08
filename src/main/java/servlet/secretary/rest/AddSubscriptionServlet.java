package servlet.secretary.rest;

import com.google.gson.Gson;
import constants.Codes;
import dao.courseedition.GetEndOrInitialDateCourseEditionDatabase;
import dao.person.InsertPersonSubscriptionDatabase;
import dao.subscription.GetFreeSubscriptionByTraineeAndCourseDatabase;
import dao.subscription.GetValidSubscriptionByCourseAndTraineeDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.*;
import servlet.AbstractRestServlet;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Rest servlet used to add a subscription for a user
 *
 * @author Francesco Caldivezzi
 */
public class AddSubscriptionServlet extends AbstractServlet
{
    /**
     * Handles a post request by adding a subscription for a given user
     * @param req  the request
     * @param resp  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Codes error = Codes.OK;
        String trainee = null;
        String courseName = null;
        Integer courseEditionId = null;
        Integer discount = null;
        Integer duration= null;

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");

        if((error = parseParams(req,resp)) == Codes.OK)
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

                //data finisci corso //dataodierna + duration
                Date startingDateCourse = new GetEndOrInitialDateCourseEditionDatabase(
                        getDataSource().getConnection(),new CourseEdition(courseEditionId,courseName)).executeMin();

                Date endingDateCourse = new GetEndOrInitialDateCourseEditionDatabase(
                        getDataSource().getConnection(),new CourseEdition(courseEditionId,courseName)).executeMax();

                Date startSubscription = startingDateCourse;
                if(!startingDateCourse.after((new Date(System.currentTimeMillis()))))
                {
                    startSubscription = new Date(System.currentTimeMillis());

                }

                if(duration == 7)
                {
                    List<Subscription> lst =  new GetFreeSubscriptionByTraineeAndCourseDatabase(getDataSource().getConnection(),new Course(courseName,null),new Person(trainee,null,null,null,null,null,null,null,null)).execute();
                    if(!lst.isEmpty())
                        error = Codes.FREE_TRIAL_ALREADY_DONE;
                    //look if it is
                    //GetFreeSuscriptionByTrainee
                    //GetValidSubscrtio By CourseandTrainee
                }else
                {
                    List<Subscription> lst = new GetValidSubscriptionByCourseAndTraineeDatabase(getDataSource().getConnection(),
                            new CourseEdition(courseEditionId,courseName),new Person(trainee,null,
                            null,null,null,null,null,null,null)).execute();
                    if(!lst.isEmpty())
                        error = Codes.OVELAPPING_SUBSCRIPTIONS;
                    //datafinecorso - datainizio >= duration

                    if(( duration > DAYS.between(startSubscription.toLocalDate(),endingDateCourse.toLocalDate())))
                        error = Codes.TYPE_SUBSCRIPTION_INVALID;
                }


                if(error == Codes.OK)
                {
                    discount = discount == null ? 0 : discount;
                    new InsertPersonSubscriptionDatabase(getDataSource().getConnection(),
                            new Subscription(courseEditionId,courseName,duration,startSubscription,discount,trainee)).execute();
                }

            } catch (SQLException | NamingException e) {
                error = Codes.INTERNAL_ERROR;
            }
        }

        if(error == Codes.OK)
            out.print(new Gson().toJson(new Message(error.getErrorMessage(),false)));
            //sendDataResponse(resp,new Message(error.getErrorMessage(),false));
        else
            out.print(new Gson().toJson(new Message(error.getErrorMessage(),true)));
            //sendErrorResponse(resp,error);

        out.flush();
        out.close();
    }

    /**
     * Checks if the different parameters are well formatted
     *
     * @param req  the request
     * @param res  the response
     * @return  a confirmation/error message
     */
    //parametri attesi : email, durata, corso, courseid, discount, startday(oggi) checkboxato,
    private Codes parseParams(HttpServletRequest req, HttpServletResponse res)
    {
        Codes error = Codes.OK;
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
                error = Codes.INVALID_FIELDS;
            }else
            {
                if(discount != null && !"".equals(discount))
                {
                    discountInteger = Integer.parseInt(discount);
                    if(discountInteger < 0 || discountInteger > 100)
                        error = Codes.INVALID_FIELDS;
                }

                if(error == Codes.OK)
                {
                    courseEditionIdInteger = Integer.parseInt(courseEditionId);
                    durationInteger = Integer.parseInt(duration);

                    //integrity check
                    if(durationInteger != 7 && durationInteger != 30 && durationInteger != 90 && durationInteger != 180 && durationInteger != 365)
                        error = Codes.INVALID_FIELDS;
                }
            }


        }catch (NumberFormatException e)
        {
            error = Codes.INVALID_FIELDS;
        }

        return  error;
    }
}
