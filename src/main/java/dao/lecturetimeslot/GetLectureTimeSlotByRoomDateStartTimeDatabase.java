package dao.lecturetimeslot;

import constants.Constants;
import resource.LectureTimeSlot;

import java.sql.*;

/**
 * This DAO is used to get the lecture time slot from the database by passing the room,date and start time
 *
 * @author Francesco Caldivezzi
 */
public class GetLectureTimeSlotByRoomDateStartTimeDatabase {

    /**
     * The SELECT query to be executed
     */
    private static final String STATEMENT = "SELECT * FROM lecturetimeslot WHERE roomname = ? and date = ? and starttime=?";

    /**
     * The connection to the database
     */
    private final Connection connection;

    /**
     * The LectureTimeSlot object to be re
     */
    private final LectureTimeSlot lectureTimeSlot;

    /**
     * Parametric constructor
     *
     * @param connection      to the database
     * @param lectureTimeSlot object to be passed to the query
     */
    public GetLectureTimeSlotByRoomDateStartTimeDatabase(final Connection connection, final LectureTimeSlot lectureTimeSlot) {
        this.connection = connection;
        this.lectureTimeSlot = lectureTimeSlot;
    }

    /**
     * Execute the query
     *
     * @return LectureTimeSlot object that matched the  query
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public LectureTimeSlot execute() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        LectureTimeSlot result = null;
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1, lectureTimeSlot.getRoomName());
            ps.setDate(2, lectureTimeSlot.getDate());
            ps.setTime(3, lectureTimeSlot.getStartTime());
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
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            connection.close();
        }
        return result;
    }


}
