package dao.lecturetimeslot;

import constants.Constants;
import resource.LectureTimeSlot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This DAO is used to get a list of lecture time slots by passing the room
 *
 * @author Harjot Singh
 */
public class GetLectureTimeSlotsByRoomNameDatabase {

    /**
     * The SELECT query to be executed
     */
    private static final String STATEMENT = "SELECT * FROM lecturetimeslot WHERE roomname = ?";

    /**
     * The name of the room
     */
    private final String roomName;

    /**
     * The connection to the database
     */
    private final Connection connection;

    /**
     * Parametric constructor
     *
     * @param connection to the database
     * @param roomName   to pass to the query
     */
    public GetLectureTimeSlotsByRoomNameDatabase(final Connection connection, final String roomName) {
        this.connection = connection;
        this.roomName = roomName;
    }

    /**
     * Execute the query
     *
     * @return a list containing LectureTimeSlot objects that matched the query
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<LectureTimeSlot> execute() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<LectureTimeSlot> result = new ArrayList<>();
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1, roomName);

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
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            connection.close();
        }
        return result;
    }
}
