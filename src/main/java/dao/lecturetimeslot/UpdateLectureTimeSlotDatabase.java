package dao.lecturetimeslot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Harjot Singh
 * @author Riccardo Forzan
 */
public class UpdateLectureTimeSlotDatabase {
    private final String STATEMENT = """
    UPDATE lecturetimeslot SET roomname = ?, date = ?, starttime = ?, courseeditionid = ?, coursename = ?, substitution = ? 
    WHERE roomname = ? AND date = ? AND starttime = ?
    """;
    private final LectureTimeSlot oldLTS;
    private final LectureTimeSlot newLTS;
    private final Connection connection;

    public UpdateLectureTimeSlotDatabase(final Connection connection, final LectureTimeSlot oldLTS, final LectureTimeSlot newLTS) {
        this.connection = connection;
        this.oldLTS = oldLTS;
        this.newLTS = newLTS;
    }

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
