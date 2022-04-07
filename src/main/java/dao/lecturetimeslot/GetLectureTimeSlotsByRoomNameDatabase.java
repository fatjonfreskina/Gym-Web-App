package dao.lecturetimeslot;

import constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Harjot Singh
 */
public class GetLectureTimeSlotsByRoomNameDatabase {
    private static final String STATEMENT = "SELECT * FROM lecturetimeslot WHERE roomname = ?";
    private final String roomName;

    private final Connection connection;
    private static final Logger logger = LogManager.getLogger("harjot_singh_appender");

    public GetLectureTimeSlotsByRoomNameDatabase(final Connection connection, final String roomName) {
        this.connection = connection;
        this.roomName = roomName;
    }

    public List<LectureTimeSlot> execute() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<LectureTimeSlot> result = new ArrayList<>();
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1, roomName);

            rs = ps.executeQuery();
            logger.debug("[DEBUG] gwa.dao.GetLTSByDateD - %s - query executed successfully, retrieving data...\n".formatted(
                    new Timestamp(System.currentTimeMillis())));

            while (rs.next()) {
                String roomName = rs.getString(Constants.LECTURETIMESLOT_ROOMNAME);
                Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
                Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
                int courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
                String courseName = rs.getString(Constants.LECTURETIMESLOT_COURSENAME);
                String substitution = rs.getString(Constants.LECTURETIMESLOT_SUBSTITUITION);
                LectureTimeSlot lts = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);

                logger.debug("[DEBUG] gwa.dao.GetLTSByDateD - %s - Retrieved: %s.\n".formatted(
                        new Timestamp(System.currentTimeMillis()), lts.toString()));
                result.add(lts);
            }
        } catch (SQLException ex) {
            logger.error("[ERROR] gwa.dao.GetLTSByDateD - %s - Exception:\n%s\n".
                    formatted(new Timestamp(System.currentTimeMillis()), ex.getMessage()));
            throw ex;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            connection.close();
        }
        return result;
    }
}
