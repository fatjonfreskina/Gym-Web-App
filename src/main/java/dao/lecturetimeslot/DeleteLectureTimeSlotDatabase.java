package dao.lecturetimeslot;

import resource.LectureTimeSlot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This DAO is used to delete a lectureTimeSlot from the database by passing the room, date and time
 *
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
     * Parametric constructor
     *
     * @param connection database connection
     * @param lts        Lecture time slot object to remove
     */
    public DeleteLectureTimeSlotDatabase(final Connection connection, final LectureTimeSlot lts) {
        this.connection = connection;
        this.lts = lts;
    }

    /**
     * Executes the statement to remove the object from the database
     *
     * @throws SQLException is thrown if something goes wrong while querying the database
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
