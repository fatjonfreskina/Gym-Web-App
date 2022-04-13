package dao.lecturetimeslot;

import constants.Constants;
import resource.CourseEdition;
import resource.LectureTimeSlot;
import resource.view.GeneralWeekHours;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Francesco Caldivezzi
 * */
public class GetListTimeScheduleForLectureTimeSlotDatabase
{
    private final String STATEMENT = "select distinct to_char(date,'Dy') as date,starttime,courseeditionid " +
            "from lecturetimeslot join courseedition on lecturetimeslot.courseeditionid=courseedition.id and lecturetimeslot.coursename=courseedition.coursename " +
            "where date >= current_date and courseedition.coursename = ? " +
            "group by date,starttime,courseeditionid " +
            "order by courseeditionid desc";


    private final Connection connection;
    private final CourseEdition courseEdition;

    public GetListTimeScheduleForLectureTimeSlotDatabase(final Connection connection, CourseEdition courseEdition)
    {
        this.connection = connection;
        this.courseEdition = courseEdition;
    }

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
