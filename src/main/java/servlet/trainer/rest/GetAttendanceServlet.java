package servlet.trainer.rest;

import com.google.gson.Gson;
import constants.ErrorCodes;
import dao.lecturetimeslot.GetLectureTimeSlotByCourseEditionIdNowDatabase;
import dao.reservation.GetListReservationByLectureDatabase;
import dao.subscription.GetValidSubscriptionsByCourseDatabase;
import dao.teaches.GetTeachesByTrainerDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.*;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Pasin
 */
public class GetAttendanceServlet extends AbstractServlet {
    private final Logger logger = LogManager.getLogger("andrea_pasin_logger");
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Person> personList=null;
        HttpSession session = req.getSession(false);
        String trainerEmail = session.getAttribute("email").toString();
        List<Teaches> teaches=null;
        ErrorCodes error = ErrorCodes.OK;

        //Retrieve the courses that Trainer teaches
        try{
            teaches=new GetTeachesByTrainerDatabase(getDataSource().getConnection(),new Person(trainerEmail)).execute();
        }
        catch(SQLException | NamingException e){
            error=ErrorCodes.INTERNAL_ERROR;
            String messageJson = new Gson().toJson(new Message(error.getErrorMessage(), true));
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            out.print(messageJson);
            return;
        }
        if(teaches==null){
            error=ErrorCodes.NO_COURSES_TAUGHT;
            String messageJson = new Gson().toJson(new Message(error.getErrorMessage(), true));
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            out.print(messageJson);
            return;
        }
        logger.info("TEACHES"+teaches.get(0));
        //Get the lecture that should be held now
        List<LectureTimeSlot> lectureTimeSlots=new ArrayList<>();
        try{
            for(Teaches t:teaches){
                LectureTimeSlot l=new GetLectureTimeSlotByCourseEditionIdNowDatabase(getDataSource().getConnection(),new LectureTimeSlot(t.getCourseEdition(),t.getCourseName())).execute();
                if(l!=null){
                    lectureTimeSlots.add(l);
                }
            }
        }
        catch(SQLException | NamingException e){
            error=ErrorCodes.INTERNAL_ERROR;
            String messageJson = new Gson().toJson(new Message(error.getErrorMessage(), true));
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            out.print(messageJson);
            return;
        }
        if(lectureTimeSlots.isEmpty()){
            error=ErrorCodes.NO_COURSES_HELD_NOW;
            String messageJson = new Gson().toJson(new Message(error.getErrorMessage(), true));
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            out.print(messageJson);
            return;
        }
        if(lectureTimeSlots.size()>1){
            error=ErrorCodes.OVERLAPPING;
            String messageJson = new Gson().toJson(new Message(error.getErrorMessage(), true));
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            out.print(messageJson);
            return;
        }
        LectureTimeSlot lectureHeldNow=lectureTimeSlots.get(0);

        //Get the list of reservation for the lecture now and their corresponding trainees
        List<Reservation> reservations=new ArrayList<>();
        try{
            reservations=new GetListReservationByLectureDatabase(getDataSource().getConnection(),new Reservation(lectureHeldNow.getRoomName(),
                    lectureHeldNow.getDate(),lectureHeldNow.getStartTime())).execute();
        }
        catch(SQLException | NamingException e){
            error=ErrorCodes.INTERNAL_ERROR;
            String messageJson = new Gson().toJson(new Message(error.getErrorMessage(), true));
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            out.print(messageJson);
            return;
        }

        //Get the subscriptions for the course held now and their corresponding trainees
        List<Subscription> subscriptions=new ArrayList<>();
        try{
            subscriptions=new GetValidSubscriptionsByCourseDatabase(getDataSource().getConnection(),new CourseEdition(lectureHeldNow.getCourseEditionId(),
                    lectureHeldNow.getCourseName())).execute();
        }
        catch(SQLException | NamingException e){
            error=ErrorCodes.INTERNAL_ERROR;
            String messageJson = new Gson().toJson(new Message(error.getErrorMessage(), true));
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            out.print(messageJson);
            return;
        }

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String lectureJson = new Gson().toJson(lectureHeldNow);
        String reservationsJson = new Gson().toJson(reservations);
        String subscriptionsJson = new Gson().toJson(subscriptions);
        out.println(lectureJson);
        out.println(reservationsJson);
        out.println(subscriptionsJson);
    }
}
