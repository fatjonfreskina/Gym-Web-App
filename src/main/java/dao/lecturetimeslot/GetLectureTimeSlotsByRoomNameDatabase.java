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
  private static final Logger logger = LogManager.getLogger("harjot_singh_logger");

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
      logger.debug("gwa.dao.GetLTSByDateD: query executed successfully, retrieving data...");

      while (rs.next()) {
        String roomName = rs.getString(Constants.LECTURETIMESLOT_ROOMNAME);
        Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
        Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
        int courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
        String courseName = rs.getString(Constants.LECTURETIMESLOT_COURSENAME);
        String substitution = rs.getString(Constants.LECTURETIMESLOT_SUBSTITUTION);
        LectureTimeSlot lts = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);

        logger.debug("gwa.dao.GetLTSByDateD: Retrieved %s.".formatted(lts.toString()));
        result.add(lts);
      }
    } catch (SQLException ex) {
      logger.error("gwa.dao.GetLTSByDateD: %s".formatted(ex.getMessage()));
      throw ex;
    } finally {
      if (rs != null) rs.close();
      if (ps != null) ps.close();
      connection.close();
    }
    return result;
  }
}
