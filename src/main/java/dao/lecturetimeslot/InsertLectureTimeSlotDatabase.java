package dao.lecturetimeslot;

import resource.LectureTimeSlot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This DAO is used to insert a lecture time slot in the database
 *
 * @author Harjot Singh
 */
public class InsertLectureTimeSlotDatabase {

    /**
     * The INSERT query to be executed
     */
    private final String STATEMENT = "INSERT INTO lecturetimeslot(roomname, date, starttime, courseeditionid,coursename,substitution)" + "VALUES (?, ?, ?, ?, ?, ?) ";

    /**
     * The LectureTimeSlot object
     */
    private final LectureTimeSlot lts;

    /**
     * The connection to the database
     */
    private final Connection connection;

    /**
     * Parametric constructor
     *
     * @param connection the connection to the database
     * @param lts        the lecture time slot to be inserted
     */
    public InsertLectureTimeSlotDatabase(final Connection connection, final LectureTimeSlot lts) {
        this.connection = connection;
        this.lts = lts;
    }

    /**
     * Execute the insert
     *
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public void execute() throws SQLException {

      try (PreparedStatement ps = connection.prepareStatement(STATEMENT)) {
        ps.setString(1, lts.getRoomName());
        ps.setDate(2, lts.getDate());
        ps.setTime(3, lts.getStartTime());
        ps.setInt(4, lts.getCourseEditionId());
        ps.setString(5, lts.getCourseName());
        ps.setString(6, lts.getSubstitution());

        ps.execute();

      } finally {
        connection.close();
      }
    }
}
