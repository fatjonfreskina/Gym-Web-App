package dao.lecturetimeslot;

import constants.Constants;
import resource.CourseEdition;
import resource.LectureTimeSlot;
import resource.view.GeneralWeekHours;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This DAO is used to get the time schedule for a lecture time slot
 *
 * @author Francesco Caldivezzi
 * */
public class GetListTimeScheduleForLectureTimeSlotDatabase
{
    /**
     * The SELECT query to be executed
     */
    private final String STATEMENT = "select distinct to_char(date,'Dy') as date,starttime,courseeditionid " +
            "from lecturetimeslot join courseedition on lecturetimeslot.courseeditionid=courseedition.id and lecturetimeslot.coursename=courseedition.coursename " +
            "where date >= current_date and courseedition.coursename = ? " +
            "group by date,starttime,courseeditionid " +
            "order by courseeditionid desc";

    /**
     * The connection to the database
     */
    private final Connection connection;

    /**
     * The course edition to pass for the query
     */
    private final CourseEdition courseEdition;

    /**
     *
     * Parametric constructor
     *
     * @param connection the connection to the database
     * @param courseEdition the course edition that needs to be passed to the query
     */
    public GetListTimeScheduleForLectureTimeSlotDatabase(final Connection connection, CourseEdition courseEdition)
    {
        this.connection = connection;
        this.courseEdition = courseEdition;
    }

    /**
     *
     * Execute the query
     *
     * @return a list of GeneralWeekHours object that matched the query
     * @throws SQLException
     */
    public List<GeneralWeekHours> execute() throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<GeneralWeekHours> result = new ArrayList<>();
        try
        {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1,courseEdition.getCourseName());
            rs = ps.executeQuery();


            while(rs.next())
            {
                String day = rs.getString(Constants.LECTURETIMESLOT_DATE);
                Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
                Integer courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
                result.add(new GeneralWeekHours(day,startTime,courseEditionId));
            }
        } finally
        {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            connection.close();
        }
        return result;
    }



}
