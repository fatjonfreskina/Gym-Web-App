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
 */
public class UpdateLectureTimeSlotDatabase {
    private static final Logger logger = LogManager.getLogger("harjot_singh_logger");
    private final String STATEMENT = "UPDATE lecturetimeslot SET courseeditionid = ?, coursename = ?, substitution = ? WHERE roomname = ? AND date = ? AND starttime = ?";
    private final LectureTimeSlot lts;
    private final Connection connection;

    public UpdateLectureTimeSlotDatabase(final Connection connection, final LectureTimeSlot lts) {
        this.connection = connection;
        this.lts = lts;
    }

    public void execute() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        LectureTimeSlot updatedLts = null;
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setInt(1, lts.getCourseEditionId());
            ps.setString(2, lts.getCourseName());
            ps.setString(3, lts.getSubstitution());

            ps.setString(4, lts.getRoomName());
            ps.setDate(5, lts.getDate());
            ps.setTime(6, lts.getStartTime());

            rs = ps.executeQuery();
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            connection.close();
        }
    }

}
