package dao.lecturetimeslot;

import constants.Constants;
import resource.CourseEdition;
import resource.LectureTimeSlot;

import java.sql.*;

/**
 * @author Francesco Caldivezzi
 * */
public class GetListTimeScheduleForLectureTimeSlotDatabase
{
    private final String STATEMENT = "select distinct to_char(date,'Dy') as date,starttime,courseeditionid " +
            "from lecturetimeslot join courseedition on lecturetimeslot.courseeditionid=courseedition.id and lecturetimeslot.coursename=courseedition.coursename " +
            "where date >= current_date and courseedition.coursename = ? " +
            "group by date,starttime,courseeditionid" +
            "order by courseeditionid desc";


    private final Connection connection;
    private final CourseEdition courseEdition;

    public GetListTimeScheduleForLectureTimeSlotDatabase(final Connection connection, CourseEdition courseEdition)
    {
        this.connection = connection;
        this.courseEdition = courseEdition;
    }

    public LectureTimeSlot execute() throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        LectureTimeSlot result = null;
        try
        {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1,courseEdition.getCourseName());
            rs = ps.executeQuery();


            if(rs.next())
            {
                Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
                Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
                Integer courseEditionId = rs.getInt(Constants.COURSEEDITION_ID);
                result = new LectureTimeSlot(null, date, startTime, courseEditionId, null, null);
            }
        }  finally
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
