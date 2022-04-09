package dao.lecturetimeslot;

import constants.Constants;
import resource.LectureTimeSlot;

import java.sql.*;

/**
 * Retrieves the LectureTimeSlot that is being held in this precise moment for a given course id
 *
 * @author Andrea Pasin
 */
public class GetLectureTimeSlotByCourseEditionIdNowDatabase {
    private static final String STATEMENT = "SELECT * FROM lecturetimeslot WHERE date = CURRENT_DATE " +
            "and starttime <= CURRENT_TIME " +
            "and (starttime + INTERVAL '2 hour') >= CURRENT_TIME " +
            "and courseeditionid= ?;";
    private final Connection connection;
    private final LectureTimeSlot lectureTimeSlot;


    public GetLectureTimeSlotByCourseEditionIdNowDatabase(final Connection connection, final LectureTimeSlot lectureTimeSlot) {
        this.connection = connection;
        this.lectureTimeSlot = lectureTimeSlot;
    }

    public LectureTimeSlot execute() throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        LectureTimeSlot result = null;
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setInt(1, lectureTimeSlot.getCourseEditionId());

            rs = ps.executeQuery();


            if (rs.next()) {
                String roomName = rs.getString(Constants.LECTURETIMESLOT_ROOMNAME);
                Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
                Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
                int courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
                String courseName = rs.getString(Constants.LECTURETIMESLOT_COURSENAME);
                String substitution = rs.getString(Constants.LECTURETIMESLOT_SUBSTITUTION);
                result = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);
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
