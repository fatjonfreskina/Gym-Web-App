package dao.lecturetimeslot;

import constants.Constants;
import resource.CourseEdition;
import resource.LectureTimeSlot;

import java.sql.*;
import java.util.List;


/**
 * @author Francesco Caldivezzi
 * */
public class GetLastLecureForLectureTimeSlotDatabase
{
    private static final String STATEMENT = "SELECT date,courseeditionid " +
            "FROM lecturetimeslot " +
            "WHERE courseeditionid = ? and date = (SELECT MAX (date) FROM lecturetimeslot)";

    private final Connection connection;
    private final CourseEdition courseEdition;
    public GetLastLecureForLectureTimeSlotDatabase(final Connection connection, CourseEdition courseEdition)
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
            ps.setInt(1,courseEdition.getId());
            rs = ps.executeQuery();


            if (rs.next())
            {
                Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
                Integer courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
                result= new LectureTimeSlot(null, date, null, courseEditionId, null, null);
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
