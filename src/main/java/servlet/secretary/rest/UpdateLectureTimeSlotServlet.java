package servlet.secretary.rest;

import com.google.gson.Gson;
import constants.Codes;
import constants.DateTimeFormats;
import dao.lecturetimeslot.GetLectureTimeSlotByRoomDateStartTimeDatabase;
import dao.lecturetimeslot.UpdateLectureTimeSlotDatabase;
import dao.person.GetNumberLectureTeacherByTeacherDateTimeDatabase;
import dao.person.GetPersonByEmailDatabase;
import dao.person.GetTrainerOfLectureTimeSlotDatabase;
import dao.reservation.GetAllPeopleInReservationTimeSlotDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.LectureTimeSlot;
import resource.Message;
import resource.Person;
import resource.Reservation;
import servlet.AbstractServlet;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Rest servlet used to update the lecture time slot if a trainer cannot hold
 * a lecture on a specific day and time
 *
 * @author Riccardo Forzan
 */
public class UpdateLectureTimeSlotServlet extends AbstractServlet {

    /**
     * Handles the post request by updating the lecture time/day
     * and sending a confirmation/error message
     *
     * @param request  the request
     * @param response  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Try to perform the operation
        Message message = performUpdate(request);

        //Output the message returned by the execution of the operation
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        //Print the message (JSON encoded) regarding the status of the operation
        out.println(new Gson().toJson(message));
        out.flush();
        out.close();

    }

    /**
     * Updates the lecture time/date
     * @param request  the request
     * @return  a confirmation/error message
     */
    private Message performUpdate(HttpServletRequest request) {

        //Parse the old parameters
        String oldRoomName = request.getParameter("oldRoomName");
        Date oldDate;
        try {
            oldDate = DateTimeFormats.dateConvert(
                    DateTimeFormats.dateFormat.parse(request.getParameter("oldDate")));
        } catch (ParseException e) {
            return new Message(Codes.INVALID_DATE.getErrorMessage(), true);
        }
        LocalTime oldLocalTime = LocalTime.parse(request.getParameter("oldStartTime"),
                DateTimeFormatter.ofPattern(DateTimeFormats.timeFormatPattern));
        //Validate the date, it must have minutes equal to 0 and seconds equal to 0
        if (!isDateValid(oldLocalTime)) {
            return new Message(Codes.INVALID_DATE.getErrorMessage(), true);
        }
        Time oldStartTime = Time.valueOf(oldLocalTime);

        //Parse the new parameters
        String newRoomName = request.getParameter("newRoomName");
        Date newDate;
        try {
            newDate = DateTimeFormats.dateConvert(
                    DateTimeFormats.dateFormat.parse(request.getParameter("newDate")));
        } catch (ParseException e) {
            return new Message(Codes.INVALID_DATE.getErrorMessage(), true);
        }

        var dest  =request.getParameter("newStartTime").substring(0,8);

        LocalTime newLocalTime = LocalTime.parse(request.getParameter("newStartTime").substring(0,8),
                DateTimeFormatter.ofPattern(DateTimeFormats.timeFormatPattern));
        //Validate the date, it must have minutes equal to 0 and seconds equal to 0
        if (!isDateValid(newLocalTime)) {
            return new Message(Codes.INVALID_DATE.getErrorMessage(), true);
        }
        Time newStartTime = Time.valueOf(newLocalTime);

        Date actual = new Date(System.currentTimeMillis());
        //Check the date of the lesson you are trying to move is after the current date in millis
        if (oldDate.before(actual)) {
            return new Message(Codes.INVALID_DATE.getErrorMessage(), true);
        }
        //Check the new date of the lesson you are trying to move is after the current date in millis
        if (newDate.before(actual)) {
            return new Message(Codes.INVALID_DATE.getErrorMessage(), true);
        }

        //Check the lecture starts in the range 8:00:00 to 20:00:00
        LocalTime openingTime = LocalTime.parse("08:00:00",
                DateTimeFormatter.ofPattern(DateTimeFormats.timeFormatPattern));
        LocalTime closingTime = LocalTime.parse("20:00:00",
                DateTimeFormatter.ofPattern(DateTimeFormats.timeFormatPattern));

        if (newLocalTime.isBefore(openingTime) || newLocalTime.isAfter(closingTime)) {
            return new Message(Codes.INVALID_DATE.getErrorMessage(), true);
        }

        LectureTimeSlot lectureTimeSlot;
        LectureTimeSlot updatedLectureTimeSlot;

        //Try to update the LectureTimeSlot
        try {

            //Get the LectureTimeSlot
            lectureTimeSlot = new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection(),
                    new LectureTimeSlot(oldRoomName, oldDate, oldStartTime, null, null, null))
                    .execute();
            //Create a new LectureTimeSlot updating the substitution field
            updatedLectureTimeSlot = new LectureTimeSlot(newRoomName, newDate, newStartTime,
                    lectureTimeSlot.getCourseEditionId(), lectureTimeSlot.getCourseName(),
                    lectureTimeSlot.getSubstitution());

            Person trainer = new GetTrainerOfLectureTimeSlotDatabase(getDataSource().getConnection(), lectureTimeSlot).execute();

            if (trainer == null) {
                return new Message(Codes.INTERNAL_ERROR.getErrorMessage(), true);
            }

            //Check the new Date and Time in which you want to move the lecture does not do any overlap
            if (isOverlappingLecture(updatedLectureTimeSlot) || isOverlappingTrainer(updatedLectureTimeSlot, trainer)) {
                return new Message(Codes.OVERLAPPING_COURSES.getErrorMessage(), true);
            }

            //Update the LectureTimeSlot
            new UpdateLectureTimeSlotDatabase(getDataSource().getConnection(), lectureTimeSlot, updatedLectureTimeSlot)
                    .execute();
        } catch (SQLException | NamingException e) {
            return new Message(Codes.INTERNAL_ERROR.getErrorMessage(), true);
        }

        //Notify all the subscribed people
        Reservation reservation = new Reservation(newRoomName, newDate, newStartTime);

        //Try to notify all the users
        try {
            List<Person> personList = new GetAllPeopleInReservationTimeSlotDatabase(getDataSource().getConnection(), reservation).execute();
            //Send a mail to each user
            for (Person p : personList) {
                Person person = new GetPersonByEmailDatabase(getDataSource().getConnection(), p.getEmail()).execute();
                //TODO: unlock mail sending          MailTypes.mailForTrainerChanged(person, lectureTimeSlot, notes);
            }
            return new Message(Codes.OK.getErrorMessage(), false);
        } catch (SQLException | NamingException e) {
            return new Message(Codes.INTERNAL_ERROR.getErrorMessage(), true);
        }

    }

    /**
     * Checks the validity of a date
     * @param time  the time without time-zone
     * @return  true if the date is valid, false otherwise
     */
    private boolean isDateValid(LocalTime time) {
        return time.getMinute() == 0 && time.getSecond() == 0;
    }

    /**
     * Checks if there are lectures held in the same class, at the same time, on the
     * same day
     *
     * @param updatedLectureTimeSlot  the lecture to be updated
     * @return  true if there are overlapping lectures, false otherwise
     * @throws NamingException
     * @throws SQLException
     */
    private boolean isOverlappingLecture(LectureTimeSlot updatedLectureTimeSlot)
            throws NamingException, SQLException {
        LectureTimeSlot lectureTimeSlot =
                new GetLectureTimeSlotByRoomDateStartTimeDatabase(getDataSource().getConnection(), updatedLectureTimeSlot)
                        .execute();
        return lectureTimeSlot != null;
    }

    /**
     * Checks if a trainer has to hold lectures at the same time, on the same day
     *
     * @param updatedLectureTimeSlot  the lecture to be updated
     * @param trainer  the trainer
     * @return  true if there are overlapping lectures, false otherwise
     * @throws NamingException
     * @throws SQLException
     */
    private boolean isOverlappingTrainer(LectureTimeSlot updatedLectureTimeSlot, Person trainer)
            throws NamingException, SQLException {
        var count = new GetNumberLectureTeacherByTeacherDateTimeDatabase(getDataSource().getConnection(),
                trainer, updatedLectureTimeSlot).execute();
        return count != 0;
    }

}
