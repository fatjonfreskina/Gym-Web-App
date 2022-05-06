package dao.lecturetimeslot;

import constants.Constants;
import resource.LectureTimeSlot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This DAO is used to get a list of lecture time slots for a course
 *
 * @author Harjot Singh
 */
public class GetLectureTimeSlotsByCourseDatabase {

    /**
     * The SELECT query to be executed
     */
    private static final String STATEMENT = "SELECT * FROM lecturetimeslot WHERE courseEditionId = ? and courseName = ?";

    /**
     * The connection to the database
     */
    private final Connection connection;

    /**
     * The LectureTimeSlot object to retrieve
     */
    private final LectureTimeSlot lectureTimeSlot;

    /**
     * Parametric constructor
     *
     * @param connection      to the database
     * @param lectureTimeSlot lectureTimeSlot object to be retrieved
     */
    public GetLectureTimeSlotsByCourseDatabase(final Connection connection, final LectureTimeSlot lectureTimeSlot) {
        this.connection = connection;
        this.lectureTimeSlot = lectureTimeSlot;
    }

    /**
     * Execute the query
     *
     * @return a list containing LectureTimeSlot objects that matched the query
     * @throws SQLException * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<LectureTimeSlot> execute() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<LectureTimeSlot> result = new ArrayList<>();
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setInt(1, lectureTimeSlot.getCourseEditionId());
            ps.setString(2, lectureTimeSlot.getCourseName());

            rs = ps.executeQuery();

            while (rs.next()) {
                String roomName = rs.getString(Constants.LECTURETIMESLOT_ROOMNAME);
                Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
                Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
                int courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
                String courseName = rs.getString(Constants.LECTURETIMESLOT_COURSENAME);
                String substitution = rs.getString(Constants.LECTURETIMESLOT_SUBSTITUTION);
                LectureTimeSlot lts = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);

                result.add(lts);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            connection.close();
        }
        return result;
    }
}
