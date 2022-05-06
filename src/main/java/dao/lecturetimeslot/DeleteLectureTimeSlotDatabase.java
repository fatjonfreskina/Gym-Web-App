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
    /**
     * Query to execute on the database
     */
    private static final String STATEMENT = "DELETE FROM lecturetimeslot WHERE roomname=? AND date=? AND starttime=?";
    /**
     * LectureTimeSlot object to remove from the database
     */
    private final LectureTimeSlot lts;
    /**
     * Database connection
     */
    private final Connection connection;

    /**
     * @param connection database connection
     * @param lts Lecture time slot object to remove
     */
    public DeleteLectureTimeSlotDatabase(final Connection connection, final LectureTimeSlot lts) {
        this.connection = connection;
        this.lts = lts;
    }

    /**
     * Executes the statement to remove the object from the database
     * @throws SQLException
     */
    public void execute() throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(STATEMENT)) {
            ps.setString(1, lts.getRoomName());
            ps.setDate(2, lts.getDate());
            ps.setTime(3, lts.getStartTime());
            ps.executeUpdate();
        } finally {
            connection.close();
        }
    }
}
