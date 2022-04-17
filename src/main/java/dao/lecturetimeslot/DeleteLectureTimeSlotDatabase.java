package dao.lecturetimeslot;

import constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

import java.sql.*;

/**
 * @author Harjot Singh
 * @author Riccardo Forzan
 */
public class DeleteLectureTimeSlotDatabase {
    private static final String STATEMENT = "DELETE FROM lecturetimeslot WHERE roomname=? AND date=? AND starttime=?";
    private final LectureTimeSlot lts;
    private final Connection connection;

    public DeleteLectureTimeSlotDatabase(final Connection connection, final LectureTimeSlot lts) {
        this.connection = connection;
        this.lts = lts;
    }

    public void execute() throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(STATEMENT)) {
            ps.setString(1, lts.getRoomName());
            ps.setDate(2, lts.getDate());
            ps.setTime(3, lts.getStartTime());
            ps.executeQuery();
        } finally {
            connection.close();
        }
    }
}
