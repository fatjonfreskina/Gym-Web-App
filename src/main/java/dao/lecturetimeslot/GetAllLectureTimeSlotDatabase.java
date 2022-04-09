package dao.lecturetimeslot;

import constants.Constants;
import resource.LectureTimeSlot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Riccardo Forzan
 */
public class GetAllLectureTimeSlotDatabase {
    private static final String STATEMENT = "SELECT * FROM lecturetimeslot";
    private final Connection connection;

    public GetAllLectureTimeSlotDatabase(final Connection connection) {
        this.connection = connection;
    }

    public List<LectureTimeSlot> execute() throws SQLException {
        ArrayList<LectureTimeSlot> resultSet = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(STATEMENT); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String roomName = rs.getString(Constants.LECTURETIMESLOT_ROOMNAME);
                Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
                Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
                int courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
                String courseName = rs.getString(Constants.LECTURETIMESLOT_COURSENAME);
                String substitution = rs.getString(Constants.LECTURETIMESLOT_SUBSTITUTION);
                resultSet.add(new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution));
            }
        } finally {
            connection.close();
        }
        return resultSet;
    }

}
