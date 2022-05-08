package servlet.secretary;

import com.google.gson.Gson;
import constants.Codes;
import constants.Constants;
import dao.courseedition.DeleteCourseEditionDatabase;
import dao.courseedition.GetAvailableCoursesDatabase;
import dao.courseedition.InsertCourseEditionDatabase;
import dao.lecturetimeslot.GetLectureTimeSlotByRoomDateStartTimeDatabase;
import dao.lecturetimeslot.InsertLectureTimeSlotDatabase;
import dao.person.GetListOfTeachersDatabase;
import dao.person.GetNumberLectureTeacherByTeacherDateTimeDatabase;
import dao.room.GetListRoomsDatabase;
import dao.subscriptiontype.InsertSubscriptionTypeDatabase;
import dao.teaches.InsertTeachesDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.*;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


/**
 * Servlet used by a secretary to add a course to the list of courses
 * taught in a period of time
 *
 * @author Francesco Caldivezzi
 * */
public class AddCoursesServlet extends AbstractServlet
{

    /**
     * Handles the get request by retrieving all the possible rooms, courses and teachers
     * in order to later add a specific course according to these parameters
     *
     * @param request  the request
     * @param response  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //request.getRequestDispatcher(Constants.PATH_SECRETARY_ADD_COURSES).forward(request,response);
        //All the courses?
        //Tutti insegnati
        //Tutte le stanze
        Codes error = Codes.OK;

        List<Room> rooms = null;
        List<Course> courses = null;
        List<Person> teachers = null;
        try
        {
            rooms = (new GetListRoomsDatabase(getDataSource().getConnection()).execute());
            request.setAttribute("rooms",rooms);
            courses = (new GetAvailableCoursesDatabase(getDataSource().getConnection())).execute();
            request.setAttribute("courses",courses);
            teachers = (new GetListOfTeachersDatabase(getDataSource().getConnection())).execute();
            //stabilire se solo email?? Informazioni pericolose
            request.setAttribute("teachers",teachers);
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
            request.getRequestDispatcher(Constants.PATH_SECRETARY_ADD_COURSES).forward(request,response);
        }

    }

    /**
     * Handles the post request by adding a course to the database
     *
     * @param req  the request
     * @param res  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        Message message = null;
        Codes error = Codes.OK;
        if((error = parseParams(req,res)) == Codes.OK) {
            //Add course and lecture time slots
            String courseName = null;
            String teacher = null;
            String room = null;
            Integer subscriptionType30 = null;
            Integer subscriptionType90 = null;
            Integer subscriptionType180 = null;
            Integer subscriptionType365 = null;
            Time[] monday = null;
            Time[] tuesday = null;
            Time[] wednesday = null;
            Time[] thursday = null;
            Time[] friday = null;
            Time[] saturday = null;
            Time[] sunday = null;
            Integer weeks = null;
            Date dateFirstEvent = null;
            Integer cost30 = null;
            Integer cost90 = null;
            Integer cost180 = null;
            Integer cost365 = null;

            courseName = req.getParameter("course_name"); //ok
            teacher = req.getParameter("teacher");
            room = req.getParameter("room");

            if (req.getParameter("subscription_type_30") != null && !"".equals(req.getParameter("subscription_type_30")))
                subscriptionType30 = Integer.parseInt(req.getParameter("subscription_type_30")); // ok
            if (req.getParameter("subscription_type_90") != null && !"".equals(req.getParameter("subscription_type_90")))
                subscriptionType90 = Integer.parseInt(req.getParameter("subscription_type_90"));
            if (req.getParameter("subscription_type_180") != null && !"".equals(req.getParameter("subscription_type_180")))
                subscriptionType180 = Integer.parseInt(req.getParameter("subscription_type_180"));
            if (req.getParameter("subscription_type_365") != null && !"".equals(req.getParameter("subscription_type_365")))
                subscriptionType365 = Integer.parseInt(req.getParameter("subscription_type_365"));


            String[] m = req.getParameterValues("monday"); //ok
            String[] t = req.getParameterValues("tuesday"); //ok
            String[] w = req.getParameterValues("wednesday"); //ok
            String[] th = req.getParameterValues("thursday"); //ok
            String[] f = req.getParameterValues("friday"); //ok
            String[] sa = req.getParameterValues("saturday"); //ok
            String[] su = req.getParameterValues("sunday"); //ok


            if (m != null) {
                monday = new Time[m.length];
                for (int i = 0; i < monday.length; i++)
                    monday[i] = Time.valueOf(m[i]);
            }

            if (t != null) {
                tuesday = new Time[t.length];
                for (int i = 0; i < tuesday.length; i++)
                    tuesday[i] = Time.valueOf(t[i]);
            }

            if (w != null) {
                wednesday = new Time[w.length];
                for (int i = 0; i < wednesday.length; i++)
                    wednesday[i] = Time.valueOf(w[i]);
            }

            if (th != null) {
                thursday = new Time[th.length];
                for (int i = 0; i < thursday.length; i++)
                    thursday[i] = Time.valueOf(th[i]);
            }
            if (f != null) {
                friday = new Time[f.length];
                for (int i = 0; i < friday.length; i++)
                    friday[i] = Time.valueOf(f[i]);
            }

            if (sa != null) {
                saturday = new Time[sa.length];
                for (int i = 0; i < saturday.length; i++)
                    saturday[i] = Time.valueOf(sa[i]);
            }

            if (su != null) {
                sunday = new Time[su.length];
                for (int i = 0; i < sunday.length; i++)
                    sunday[i] = Time.valueOf(su[i]);
            }


            weeks = Integer.parseInt(req.getParameter("weeks"));

            dateFirstEvent = Date.valueOf(req.getParameter("date_first_event")); //ok


            if (req.getParameter("cost_30") != null && !"".equals(req.getParameter("cost_30")))
                cost30 = Integer.parseInt(req.getParameter("cost_30")); //ok
            if (req.getParameter("cost_90") != null && !"".equals(req.getParameter("cost_90")))
                cost90 = Integer.parseInt(req.getParameter("cost_90"));
            if (req.getParameter("cost_180") != null && !"".equals(req.getParameter("cost_180")))
                cost180 = Integer.parseInt(req.getParameter("cost_180"));
            if (req.getParameter("cost_365") != null && !"".equals(req.getParameter("cost_365")))
                cost365 = Integer.parseInt(req.getParameter("cost_365"));


            //1° add new courseedition

            try {
                Integer id = (new InsertCourseEditionDatabase(getDataSource().getConnection(), new CourseEdition(-1, courseName)).execute());

                if (id != null) {
                    //need to find the first day after the specified date

                    boolean fail =
                            overlappingLectures(room,id,courseName,dateFirstEvent,weeks,monday,tuesday,wednesday,thursday,friday,saturday,sunday) ||
                            overlappingTeacherLectures(teacher,dateFirstEvent,weeks,monday,tuesday,wednesday,thursday,friday,saturday,sunday);

                    if (!fail) {
                        LocalDate event = dateFirstEvent.toLocalDate();
                        Date pointerMonday = monday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.MONDAY))) : null;
                        Date pointerTuesday = tuesday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.TUESDAY))) : null;
                        Date pointerWednesday = wednesday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY))) : null;
                        Date pointerThursday = thursday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.THURSDAY))) : null;
                        Date pointerFriday = friday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.FRIDAY))) : null;
                        Date pointerSaturday = saturday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))) : null;
                        Date pointerSunday = sunday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.SUNDAY))) : null;
                        Calendar c = Calendar.getInstance();

                        for (int i = 0; i < weeks; i++) {
                            if (pointerMonday != null) {
                                for (Time hour : monday)
                                    new InsertLectureTimeSlotDatabase(getDataSource().getConnection(), new LectureTimeSlot(room, pointerMonday, hour, id, courseName, null)).execute();
                                //update date to next week
                                c.setTime(pointerMonday);
                                c.add(Calendar.DATE, 7);
                                pointerMonday = new Date(c.getTimeInMillis());
                            }

                            if (pointerTuesday != null) {
                                for (Time hour : tuesday)
                                    new InsertLectureTimeSlotDatabase(getDataSource().getConnection(), new LectureTimeSlot(room, pointerTuesday, hour, id, courseName, null)).execute();
                                //update date to next week
                                c.setTime(pointerTuesday);
                                c.add(Calendar.DATE, 7);
                                pointerTuesday = new Date(c.getTimeInMillis());
                            }

                            if (pointerWednesday != null) {
                                for (Time hour : wednesday)
                                    new InsertLectureTimeSlotDatabase(getDataSource().getConnection(), new LectureTimeSlot(room, pointerWednesday, hour, id, courseName, null)).execute();
                                //update date to next week
                                c.setTime(pointerWednesday);
                                c.add(Calendar.DATE, 7);
                                pointerWednesday = new Date(c.getTimeInMillis());
                            }

                            if (pointerThursday != null) {
                                for (Time hour : thursday)
                                    new InsertLectureTimeSlotDatabase(getDataSource().getConnection(), new LectureTimeSlot(room, pointerThursday, hour, id, courseName, null)).execute();
                                //update date to next week
                                c.setTime(pointerThursday);
                                c.add(Calendar.DATE, 7);
                                pointerThursday = new Date(c.getTimeInMillis());
                            }


                            if (pointerFriday != null) {
                                for (Time hour : friday)
                                    new InsertLectureTimeSlotDatabase(getDataSource().getConnection(), new LectureTimeSlot(room, pointerFriday, hour, id, courseName, null)).execute();
                                //update date to next week
                                c.setTime(pointerFriday);
                                c.add(Calendar.DATE, 7);
                                pointerFriday = new Date(c.getTimeInMillis());
                            }

                            if (pointerSaturday != null) {
                                for (Time hour : saturday)
                                    new InsertLectureTimeSlotDatabase(getDataSource().getConnection(), new LectureTimeSlot(room, pointerSaturday, hour, id, courseName, null)).execute();
                                //update date to next week
                                c.setTime(pointerSaturday);
                                c.add(Calendar.DATE, 7);
                                pointerSaturday = new Date(c.getTimeInMillis());
                            }

                            if (pointerSunday != null) {
                                for (Time hour : sunday)
                                    new InsertLectureTimeSlotDatabase(getDataSource().getConnection(), new LectureTimeSlot(room, pointerSunday, hour, id, courseName, null)).execute();
                                //update date to next week
                                c.setTime(pointerSunday);
                                c.add(Calendar.DATE, 7);
                                pointerSunday = new Date(c.getTimeInMillis());
                            }
                        }
                        new InsertSubscriptionTypeDatabase(getDataSource().getConnection(),new SubscriptionType(id,courseName,7,0)).execute();
                        if(cost30 != null)
                            new InsertSubscriptionTypeDatabase(getDataSource().getConnection(),new SubscriptionType(id,courseName,30,cost30)).execute();
                        if(cost90 != null)
                            new InsertSubscriptionTypeDatabase(getDataSource().getConnection(),new SubscriptionType(id,courseName,90,cost90)).execute();
                        if(cost180 != null)
                            new InsertSubscriptionTypeDatabase(getDataSource().getConnection(),new SubscriptionType(id,courseName,180,cost180)).execute();
                        if(cost365 != null)
                            new InsertSubscriptionTypeDatabase(getDataSource().getConnection(),new SubscriptionType(id,courseName,365,cost365)).execute();

                        //Insert into teaches
                        new InsertTeachesDatabase(getDataSource().getConnection(),new CourseEdition(id,courseName),new Person(teacher)).execute();

                    } else {
                        error = Codes.OVERLAPPING_COURSES;
                        new DeleteCourseEditionDatabase(getDataSource().getConnection(),new CourseEdition(id,courseName)).execute();
                    }
                } else
                    error = Codes.INTERNAL_ERROR;

            } catch (NamingException | SQLException exception) {
                error = Codes.INTERNAL_ERROR;
                exception.printStackTrace();
            }



            if (error.getErrorCode() == Codes.OK.getErrorCode()) {
                message = new Message(error.getErrorMessage(), false);
            } else {
                message = new Message(error.getErrorMessage(), true);
            }
        }else
            message = new Message(error.getErrorMessage(),true);

        String messageJson = new Gson().toJson(message);
        PrintWriter out = res .getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("utf-8");
        out.print(messageJson);

    }
    /**
     * Checks if the different parameters are well formatted
     *
     * @param req  the request
     * @param res  the response
     * @return  a confirmation/error message
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    private Codes parseParams(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        Codes error = Codes.OK;

        String courseName = null;
        String teacher = null;
        String room = null;

        String subscriptionType30 = null;
        String subscriptionType90 = null;
        String subscriptionType180 = null;
        String subscriptionType365 = null;

        String[] monday = null;
        String[] tuesday = null;
        String[] wednesday = null;
        String[] thursday = null;
        String[] friday = null;
        String[] saturday = null;
        String[] sunday = null;
        String weeks = null;

        String dateFirstEvent = null;


        String cost30 = null;
        String cost90 = null;
        String cost180 = null;
        String cost365 = null;

        try
        {
            courseName = req.getParameter("course_name"); //ok
            teacher = req.getParameter("teacher");
            room = req.getParameter("room");

            subscriptionType30 = req.getParameter("subscription_type_30"); // ok
            subscriptionType90 = req.getParameter("subscription_type_90");
            subscriptionType180 = req.getParameter("subscription_type_180");
            subscriptionType365 = req.getParameter("subscription_type_365");

            monday = req.getParameterValues("monday"); //ok
            tuesday = req.getParameterValues("tuesday");
            wednesday = req.getParameterValues("wednesday");
            thursday = req.getParameterValues("thursday");
            friday = req.getParameterValues("friday");
            saturday = req.getParameterValues("saturday");
            sunday = req.getParameterValues("sunday");
            weeks =  req.getParameter("weeks");

            dateFirstEvent = req.getParameter("date_first_event"); //ok

            cost30 = req.getParameter("cost_30"); //ok
            cost90 = req.getParameter("cost_90");
            cost180 = req.getParameter("cost_180");
            cost365 = req.getParameter("cost_365");

            if(monday == null && tuesday == null && wednesday == null && thursday==null && friday == null && saturday == null && sunday == null)
                error = Codes.EMPTY_INPUT_FIELDS;
            else if(cost30 == null && cost90 == null && cost180 == null && cost365 == null && "".equals(cost30) && "".equals(cost90) && "".equals(cost180) && "".equals(cost365))
                error = Codes.EMPTY_INPUT_FIELDS;
            else if(subscriptionType30 == null && subscriptionType90 == null && subscriptionType180 == null && subscriptionType365 == null || "".equals(subscriptionType30) || "".equals(subscriptionType90) || "".equals(subscriptionType180) || "".equals(subscriptionType365))
                error = Codes.EMPTY_INPUT_FIELDS;
            else if(courseName == null || dateFirstEvent == null || teacher == null && weeks == null || "".equals(dateFirstEvent) || "".equals(courseName) || "".equals(teacher) || "".equals(weeks) || room == null || "".equals(room))
                error = Codes.EMPTY_INPUT_FIELDS;

            //if(courseName == null || teacher == null || subscriptionType30)
        } catch (IllegalArgumentException e) //Either Telephone isn't a telephone or birthDate isn't a Date
        {
            error = Codes.INVALID_FIELDS;
        }

        //conversions :
        Date dateFirstEventDate = null;
        Integer weeksInteger = null;
        Integer cost30Integer = null;
        Integer cost90Integer = null;
        Integer cost180Integer = null;
        Integer cost365Integer = null;

        Integer subscriptionType30Integer = null;
        Integer subscriptionType90Integer = null;
        Integer subscriptionType180Integer = null;
        Integer subscriptionType365Integer = null;

        Time[] mondayTime = null;
        Time[] tuesdayTime = null;
        Time[] wednesdayTime = null;
        Time[] thursdayTime = null;
        Time[] fridayTime = null;
        Time[] saturdayTime = null;
        Time[] sundayTime = null;

        if(error == Codes.OK)
        {

            try
            {
                weeksInteger = Integer.parseInt(weeks);
                dateFirstEventDate = Date.valueOf(dateFirstEvent);
                if(cost30 != null && !"".equals(cost30))
                    cost30Integer = Integer.parseInt(cost30);
                if(cost90 != null && !"".equals(cost90))
                    cost90Integer = Integer.parseInt(cost90);
                if(cost180 != null && !"".equals(cost180))
                    cost180Integer = Integer.parseInt(cost180);
                if(cost365 != null && !"".equals(cost365))
                    cost365Integer = Integer.parseInt(cost365);

                if(!Objects.equals(subscriptionType30, "") && subscriptionType30 != null)
                    subscriptionType30Integer = Integer.parseInt(subscriptionType30);
                if(!Objects.equals(subscriptionType90, "") && subscriptionType90 != null)
                    subscriptionType90Integer = Integer.parseInt(subscriptionType90);
                if(!Objects.equals(subscriptionType180, "") && subscriptionType180 != null)
                    subscriptionType180Integer = Integer.parseInt(subscriptionType180);
                if(!Objects.equals(subscriptionType365, "") && subscriptionType365 != null)
                    subscriptionType365Integer = Integer.parseInt(subscriptionType365);

                if(monday != null)
                {
                    mondayTime = new Time[monday.length];
                    for(int i=0; i< mondayTime.length; i++)
                        mondayTime[i] = Time.valueOf(monday[i]);
                }

                if(tuesday!= null)
                {
                    tuesdayTime = new Time[tuesday.length];
                    for(int i=0; i< tuesdayTime.length; i++)
                        tuesdayTime[i] = Time.valueOf(tuesday[i]);
                }

                if(wednesday!= null)
                {
                    wednesdayTime = new Time[wednesday.length];
                    for(int i=0; i< wednesdayTime.length; i++)
                        wednesdayTime[i] = Time.valueOf(wednesday[i]);
                }

                if(thursday!= null)
                {
                    thursdayTime = new Time[thursday.length];
                    for(int i=0; i< thursdayTime.length; i++)
                        thursdayTime[i] = Time.valueOf(thursday[i]);
                }
                if(friday!= null)
                {
                    fridayTime = new Time[friday.length];
                    for(int i=0; i< fridayTime.length; i++)
                        fridayTime[i] = Time.valueOf(friday[i]);
                }

                if(saturday!= null)
                {
                    saturdayTime = new Time[saturday.length];
                    for(int i=0; i< saturdayTime.length; i++)
                        saturdayTime[i] = Time.valueOf(saturday[i]);
                }

                if(sunday!= null)
                {
                    sundayTime = new Time[sunday.length];
                    for(int i=0; i< sundayTime.length; i++)
                        sundayTime[i] = Time.valueOf(sunday[i]);
                }

                if(dateFirstEventDate.toLocalDate().isBefore((new Date(System.currentTimeMillis()).toLocalDate())))
                {
                    error = Codes.INVALID_FIELDS;
                }else
                {

                    if(cost30Integer != null && subscriptionType30Integer != null)
                    {
                        //ok

                    }else if(cost90Integer != null && subscriptionType90Integer != null)
                    {
                        //ok
                    }else if(cost180Integer != null && subscriptionType180Integer != null)
                    {

                    }else if(cost365Integer != null && subscriptionType365Integer != null)
                    {

                    }else
                        error = Codes.INVALID_FIELDS;
                }
            }catch (Exception e)
            {
                error = Codes.INVALID_FIELDS;
            }
        }
        //Time monday
        return error;
    }


    /**
     * Checks if there are overlapping lectures: there can't be 2 courses held in the same classroom
     * at the same time on the same day.
     *
     * @param roomName  the room
     * @param courseEditionId  the course edition
     * @param courseName  the name of the course
     * @param dateFirstEvent  the date of the first lecture of a given course
     * @param weeks  the number of weeks the given course lasts
     * @param monday  the times of lectures on monday
     * @param tuesday  the times of lectures tuesday
     * @param wednesday  the times of lectures wednesday
     * @param thursday  the times of lectures thursday
     * @param friday  the times of lectures friday
     * @param saturday  the times of lectures saturday
     * @param sunday  the times of lectures sunday
     * @return  true if there are overlapping lectures, false otherwise
     * @throws NamingException if it was not possible to access the datasource
     * @throws SQLException if there is an error concerning the database
     */
    private boolean overlappingLectures(String roomName, int courseEditionId, String courseName,Date dateFirstEvent,int weeks,
                                        Time[] monday,Time[] tuesday,Time[] wednesday,Time[] thursday,Time[] friday,Time[] saturday,Time[] sunday) throws NamingException,SQLException
    {
        boolean fail = false;
        LocalDate event = dateFirstEvent.toLocalDate();
        Date pointerMonday = monday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.MONDAY))) : null;
        Date pointerTuesday = tuesday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.TUESDAY))) : null;
        Date pointerWednesday = wednesday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY))) : null;
        Date pointerThursday = thursday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.THURSDAY))) : null;
        Date pointerFriday = friday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.FRIDAY))) : null;
        Date pointerSaturday = saturday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))) : null;
        Date pointerSunday = sunday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.SUNDAY))) : null;
        Calendar calendar = Calendar.getInstance();


        for (int i = 0; i < weeks; i++)
        {
            fail = overlapLecture(pointerMonday, monday, roomName, courseEditionId, courseName);
            if(fail)
                break;
            else if (pointerMonday != null)
            {
                calendar.setTime(pointerMonday);
                calendar.add(Calendar.DATE, 7);
                pointerMonday = new Date(calendar.getTimeInMillis());
            }

            fail = overlapLecture(pointerTuesday, tuesday, roomName, courseEditionId, courseName);
            if(fail)
                break;
            else if (pointerTuesday != null)
            {
                calendar.setTime(pointerTuesday);
                calendar.add(Calendar.DATE, 7);
                pointerTuesday = new Date(calendar.getTimeInMillis());
            }

            fail = overlapLecture(pointerWednesday, wednesday, roomName, courseEditionId, courseName);
            if(fail)
                break;
            else if (pointerWednesday != null)
            {
                calendar.setTime(pointerWednesday);
                calendar.add(Calendar.DATE, 7);
                pointerWednesday = new Date(calendar.getTimeInMillis());
            }

            fail = overlapLecture(pointerThursday, thursday, roomName, courseEditionId, courseName);
            if(fail)
                break;
            else if (pointerThursday != null)
            {
                calendar.setTime(pointerThursday);
                calendar.add(Calendar.DATE, 7);
                pointerThursday = new Date(calendar.getTimeInMillis());
            }

            fail = overlapLecture(pointerFriday, friday, roomName, courseEditionId, courseName);
            if(fail)
                break;
            else if (pointerFriday != null)
            {
                calendar.setTime(pointerFriday);
                calendar.add(Calendar.DATE, 7);
                pointerFriday = new Date(calendar.getTimeInMillis());
            }

            fail = overlapLecture(pointerSaturday, saturday, roomName, courseEditionId, courseName);
            if(fail)
                break;
            else if (pointerSaturday != null)
            {
                calendar.setTime(pointerSaturday);
                calendar.add(Calendar.DATE, 7);
                pointerSaturday = new Date(calendar.getTimeInMillis());
            }

            fail = overlapLecture(pointerSunday, sunday, roomName, courseEditionId, courseName);
            if(fail)
                break;
            else if (pointerSunday != null)
            {
                calendar.setTime(pointerSunday);
                calendar.add(Calendar.DATE, 7);
                pointerSunday = new Date(calendar.getTimeInMillis());
            }
        }
        return fail;

    }

    private boolean overlapLecture(Date date,Time[] hours, String roomName, int courseEditionId, String courseName) throws NamingException,SQLException
    {
        boolean fail = false;

        if(date != null)
        {
            for(Time hour : hours)
            {
                LectureTimeSlot l = new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection(),
                        new LectureTimeSlot(roomName, date, hour, courseEditionId, courseName, null)).execute();
                if(l != null)
                {
                    fail = true;
                    break;
                }
            }
        }
        return fail;
    }

    /**
     * Checks if there are overlapping lectures. The Trainer cannot be teaching 2 courses
     * at the same time on the same day
     * @param teacher the trainer
     * @param dateFirstEvent  the date of the first lecture of a given course
     * @param weeks  the number of weeks the given course lasts
     * @param monday  the times of lectures on monday
     * @param tuesday  the times of lectures tuesday
     * @param wednesday  the times of lectures wednesday
     * @param thursday  the times of lectures thursday
     * @param friday  the times of lectures friday
     * @param saturday  the times of lectures saturday
     * @param sunday  the times of lectures sunday
     * @return  true if there are overlapping lectures, false otherwise
     * @throws NamingException if it was not possible to access the datasource
     * @throws SQLException if there is an error concerning the database
     */

    private boolean overlappingTeacherLectures(String teacher, Date dateFirstEvent, int weeks,
                                               Time[] monday,Time[] tuesday,Time[] wednesday,Time[] thursday,Time[] friday,Time[] saturday,Time[] sunday) throws NamingException,SQLException
    {
        boolean fail = false;
        LocalDate event = dateFirstEvent.toLocalDate();
        Date pointerMonday = monday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.MONDAY))) : null;
        Date pointerTuesday = tuesday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.TUESDAY))) : null;
        Date pointerWednesday = wednesday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY))) : null;
        Date pointerThursday = thursday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.THURSDAY))) : null;
        Date pointerFriday = friday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.FRIDAY))) : null;
        Date pointerSaturday = saturday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))) : null;
        Date pointerSunday = sunday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.SUNDAY))) : null;
        Calendar calendar = Calendar.getInstance();


        for (int i = 0; i < weeks; i++)
        {
            fail = overlapTeacherLecture(pointerMonday, monday, teacher);
            if(fail)
                break;
            else if (pointerMonday != null)
            {
                calendar.setTime(pointerMonday);
                calendar.add(Calendar.DATE, 7);
                pointerMonday = new Date(calendar.getTimeInMillis());
            }

            fail = overlapTeacherLecture(pointerTuesday, tuesday, teacher);
            if(fail)
                break;
            else if (pointerTuesday != null)
            {
                calendar.setTime(pointerTuesday);
                calendar.add(Calendar.DATE, 7);
                pointerTuesday = new Date(calendar.getTimeInMillis());
            }

            fail = overlapTeacherLecture(pointerWednesday, wednesday, teacher);
            if(fail)
                break;
            else if (pointerWednesday != null)
            {
                calendar.setTime(pointerWednesday);
                calendar.add(Calendar.DATE, 7);
                pointerWednesday = new Date(calendar.getTimeInMillis());
            }

            fail = overlapTeacherLecture(pointerThursday, thursday, teacher);
            if(fail)
                break;
            else if (pointerThursday != null)
            {
                calendar.setTime(pointerThursday);
                calendar.add(Calendar.DATE, 7);
                pointerThursday = new Date(calendar.getTimeInMillis());
            }

            fail = overlapTeacherLecture(pointerFriday, friday, teacher);
            if(fail)
                break;
            else if (pointerFriday != null)
            {
                calendar.setTime(pointerFriday);
                calendar.add(Calendar.DATE, 7);
                pointerFriday = new Date(calendar.getTimeInMillis());
            }

            fail = overlapTeacherLecture(pointerSaturday, saturday, teacher);
            if(fail)
                break;
            else if (pointerSaturday != null)
            {
                calendar.setTime(pointerSaturday);
                calendar.add(Calendar.DATE, 7);
                pointerSaturday = new Date(calendar.getTimeInMillis());
            }

            fail = overlapTeacherLecture(pointerSunday, sunday, teacher);
            if(fail)
                break;
            else if (pointerSunday != null)
            {
                calendar.setTime(pointerSunday);
                calendar.add(Calendar.DATE, 7);
                pointerSunday = new Date(calendar.getTimeInMillis());
            }
        }
        return fail;

    }

    private boolean overlapTeacherLecture(Date date,Time[] hours, String teacher) throws NamingException,SQLException
    {
        boolean fail = false;

        if(date != null)
        {
            for(Time hour : hours)
            {
                int count = new GetNumberLectureTeacherByTeacherDateTimeDatabase(getDataSource().getConnection(),
                        new Person(teacher,null,null,null,null,null,null,null,null),
                        new LectureTimeSlot(null,date,hour,null,null,null))
                        .execute();
                if(count > 0)
                {
                    fail = true;
                    break;
                }
            }
        }
        return fail;
    }
}