package dao.lecturetimeslot;

import resource.LectureTimeSlot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * This DAO is used to update a lecture time slot
 *
 * @author Harjot Singh
 * @author Riccardo Forzan
 */
public class UpdateLectureTimeSlotDatabase {

    /**
     * The UPDATE query to be executed
     */
    private final String STATEMENT = """
    UPDATE lecturetimeslot SET roomname = ?, date = ?, starttime = ?, courseeditionid = ?, coursename = ?, substitution = ? 
    WHERE roomname = ? AND date = ? AND starttime = ?
    """;

    /**
     * The old lecture time slot to be updated
     */
    private final LectureTimeSlot oldLTS;

    /**
     * The new lecture time slot to be inserted
     */
    private final LectureTimeSlot newLTS;

    /**
     * The connection to the databasse
     */
    private final Connection connection;

    /**
     *
     * Parametric constructor
     *
     * @param connection the connection to the database
     * @param oldLTS the old lecture time slot to be updated
     * @param newLTS the new lecture time slot to be inserted
     */
    public UpdateLectureTimeSlotDatabase(final Connection connection, final LectureTimeSlot oldLTS, final LectureTimeSlot newLTS) {
        this.connection = connection;
        this.oldLTS = oldLTS;
        this.newLTS = newLTS;
    }

    /**
     *
     * Execute the update
     *
     * @throws SQLException
     */
    public void execute() throws SQLException {
        PreparedStatement ps = null;
        LectureTimeSlot updatedLts = null;
        try {
            ps = connection.prepareStatement(STATEMENT);

            ps.setString(1, newLTS.getRoomName());
            ps.setDate(2, newLTS.getDate());
            ps.setTime(3, newLTS.getStartTime());
            ps.setInt(4, newLTS.getCourseEditionId());
            ps.setString(5, newLTS.getCourseName());
            ps.setString(6, newLTS.getSubstitution());

            //Old primary key
            ps.setString(7, oldLTS.getRoomName());
            ps.setDate(8, oldLTS.getDate());
            ps.setTime(9, oldLTS.getStartTime());

            ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            connection.close();
        }
    }

}
