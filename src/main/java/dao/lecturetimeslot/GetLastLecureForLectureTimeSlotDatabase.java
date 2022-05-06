package dao.lecturetimeslot;

import constants.Constants;
import resource.CourseEdition;
import resource.LectureTimeSlot;

import java.sql.*;
import java.util.List;

/**
 *
 * This DAO is used to get the last lecture for a lecture time slot from the database
 *
 * @author Francesco Caldivezzi
 */
public class GetLastLecureForLectureTimeSlotDatabase {

    /**
     * The SELECT query to be executed
     */
    private static final String STATEMENT = """
        SELECT max(date) as date,courseeditionid FROM lecturetimeslot WHERE courseeditionid = ?
        group by courseeditionid
        """;

    /**
     * The connection to the database
     */
    private final Connection connection;

    /**
     * The CourseEdition object
     */
    private final CourseEdition courseEdition;

    /**
     *
     * Parametric constructor
     *
     * @param connection the connection to the database
     * @param courseEdition the course CourseEdition to be retrieved
     */
    public GetLastLecureForLectureTimeSlotDatabase(final Connection connection, CourseEdition courseEdition)
    {
        this.connection = connection;
        this.courseEdition = courseEdition;
    }

    /**
     *
     * Executes the statement
     *
     * @return LectureTimeSlot object that matched the query
     * @throws SQLException
     */
    public LectureTimeSlot execute() throws SQLException {
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
