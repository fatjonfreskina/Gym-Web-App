package servlet.secretary;

import com.google.gson.Gson;
import constants.Constants;
import constants.ErrorCodes;
import dao.courseedition.GetAvailableCourses;
import dao.courseedition.InsertCourseEditionDatabase;
import dao.lecturetimeslot.GetLectureTimeSlotByRoomDateStartTimeDatabase;
import dao.lecturetimeslot.InsertLectureTimeSlotDatabase;
import dao.person.GetListOfTeachersDatabase;
import dao.room.GetListRoomsDatabase;
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
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


/**
 * @author Francesco Caldivezzi
 * */
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
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        Message message = null;
        ErrorCodes error = ErrorCodes.OK;
        if((error = parseParams(req,res)) == ErrorCodes.OK) {
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
                    LocalDate event = dateFirstEvent.toLocalDate();
                    Date pointerMonday = monday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.MONDAY))) : null;
                    Date pointerTuesday = tuesday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.TUESDAY))) : null;
                    Date pointerWednesday = wednesday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY))) : null;
                    Date pointerThursday = thursday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.THURSDAY))) : null;
                    Date pointerFriday = friday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.FRIDAY))) : null;
                    Date pointerSaturday = saturday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))) : null;
                    Date pointerSunday = sunday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.SUNDAY))) : null;

                    Calendar c = Calendar.getInstance();

                    boolean fail = false;


                    for (int i = 0; i < weeks && !fail; i++) {
                        if (pointerMonday != null && !fail) {
                            for (Time hour : monday) {
                                LectureTimeSlot l = new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection()
                                        , new LectureTimeSlot(room, pointerMonday, hour, id, courseName, null)).execute();
                                if (l != null) {
                                    fail = true;
                                    break;
                                }
                            }

                            //update date to next week
                            c.setTime(pointerMonday);
                            c.add(Calendar.DATE, 7);
                            pointerMonday = new Date(c.getTimeInMillis());
                        }

                        if (pointerTuesday != null && !fail) {
                            for (Time hour : tuesday) {
                                LectureTimeSlot l = new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection(), new LectureTimeSlot(room, pointerTuesday, hour, id, courseName, null)).execute();
                                if (l != null) {
                                    fail = true;
                                    break;
                                }
                            }
                            //update date to next week
                            c.setTime(pointerTuesday);
                            c.add(Calendar.DATE, 7);
                            pointerTuesday = new Date(c.getTimeInMillis());
                        }

                        if (pointerWednesday != null && !fail) {
                            for (Time hour : wednesday) {
                                LectureTimeSlot l = new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection(), new LectureTimeSlot(room, pointerWednesday, hour, id, courseName, null)).execute();
                                if (l != null) {
                                    fail = true;
                                    break;
                                }
                            }

                            //update date to next week
                            c.setTime(pointerWednesday);
                            c.add(Calendar.DATE, 7);
                            pointerWednesday = new Date(c.getTimeInMillis());
                        }

                        if (pointerThursday != null && !fail) {
                            for (Time hour : thursday) {
                                LectureTimeSlot l = new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection(), new LectureTimeSlot(room, pointerThursday, hour, id, courseName, null)).execute();
                                if (l != null) {
                                    fail = true;
                                    break;
                                }
                            }

                            //update date to next week
                            c.setTime(pointerThursday);
                            c.add(Calendar.DATE, 7);
                            pointerThursday = new Date(c.getTimeInMillis());
                        }


                        if (pointerFriday != null && !fail) {
                            for (Time hour : friday) {
                                LectureTimeSlot l = new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection(), new LectureTimeSlot(room, pointerFriday, hour, id, courseName, null)).execute();
                                if (l != null) {
                                    fail = true;
                                    break;
                                }
                            }

                            //update date to next week
                            c.setTime(pointerFriday);
                            c.add(Calendar.DATE, 7);
                            pointerFriday = new Date(c.getTimeInMillis());
                        }

                        if (pointerSaturday != null && !fail) {
                            for (Time hour : saturday) {
                                LectureTimeSlot l = new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection(), new LectureTimeSlot(room, pointerSaturday, hour, id, courseName, null)).execute();
                                if (l != null) {
                                    fail = true;
                                    break;
                                }
                            }

                            //update date to next week
                            c.setTime(pointerSaturday);
                            c.add(Calendar.DATE, 7);
                            pointerSaturday = new Date(c.getTimeInMillis());
                        }

                        if (pointerSunday != null && !fail) {
                            for (Time hour : sunday) {
                                LectureTimeSlot l = new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection(), new LectureTimeSlot(room, pointerSunday, hour, id, courseName, null)).execute();
                                if (l != null) {
                                    fail = true;
                                    break;
                                }
                            }

                            //update date to next week
                            c.setTime(pointerSunday);
                            c.add(Calendar.DATE, 7);
                            pointerSunday = new Date(c.getTimeInMillis());
                        }
                    }


                    if (!fail) {
                        pointerMonday = monday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.MONDAY))) : null;
                        pointerTuesday = tuesday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.TUESDAY))) : null;
                        pointerWednesday = wednesday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY))) : null;
                        pointerThursday = thursday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.THURSDAY))) : null;
                        pointerFriday = friday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.FRIDAY))) : null;
                        pointerSaturday = saturday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))) : null;
                        pointerSunday = sunday != null ? Date.valueOf(event.with(TemporalAdjusters.next(DayOfWeek.SUNDAY))) : null;
                        c = Calendar.getInstance();

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
                    } else
                        error = ErrorCodes.OVERLAPPING;
                } else
                    error = ErrorCodes.INTERNAL_ERROR;

            } catch (NamingException | SQLException exception) {
                error = ErrorCodes.INTERNAL_ERROR;
            }

            if (error.getErrorCode() == ErrorCodes.OK.getErrorCode()) {
                message = new Message(error.getErrorMessage(), false);
            } else {
                message = new Message(error.getErrorMessage(), true);
            }
        }

        String messageJson = new Gson().toJson(message);
        PrintWriter out = res .getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("utf-8");
        out.print(messageJson);

    }

    public ErrorCodes parseParams(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        ErrorCodes error = ErrorCodes.OK;

        String courseName = null;
        String teacher = null;
        String room = null;

        String subscriptionType30 = null;
        String subscriptionType90 = null;
        String subscriptionType180 = null;
        String subscriptionType365 = null;

        String[] monaday = null;
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

            monaday = req.getParameterValues("monday"); //ok
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

            if(monaday == null && tuesday == null && wednesday == null && thursday==null && friday == null && saturday == null && sunday == null)
                error = ErrorCodes.EMPTY_INPUT_FIELDS;
            else if(cost30 == null && cost90 == null && cost180 == null && cost365 == null && "".equals(cost30) && "".equals(cost90) && "".equals(cost180) && "".equals(cost365))
                error = ErrorCodes.EMPTY_INPUT_FIELDS;
            else if(subscriptionType30 == null && subscriptionType90 == null && subscriptionType180 == null && subscriptionType365 == null || "".equals(subscriptionType30) || "".equals(subscriptionType90) || "".equals(subscriptionType180) || "".equals(subscriptionType365))
                error = ErrorCodes.EMPTY_INPUT_FIELDS;
            else if(courseName == null || dateFirstEvent == null || teacher == null && weeks == null || "".equals(dateFirstEvent) || "".equals(courseName) || "".equals(teacher) || "".equals(weeks) || room == null || "".equals(room))
                error = ErrorCodes.EMPTY_INPUT_FIELDS;

            //if(courseName == null || teacher == null || subscriptionType30)
        } catch (IllegalArgumentException e) //Either Telephone isn't a telephone or birthDate isn't a Date
        {
            error = ErrorCodes.INVALID_FIELDS;
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

        if(error == ErrorCodes.OK)
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

                if(monaday != null)
                {
                    mondayTime = new Time[monaday.length];
                    for(int i=0; i< mondayTime.length; i++)
                        mondayTime[i] = Time.valueOf(monaday[i]);
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

                if(!dateFirstEventDate.after(new Date(System.currentTimeMillis())))
                {
                    error = ErrorCodes.INVALID_FIELDS;
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
                        error = ErrorCodes.INVALID_FIELDS;
                }
            }catch (Exception e)
            {
                error = ErrorCodes.INVALID_FIELDS;
            }
        }
        //Time monday
        return error;
    }




}