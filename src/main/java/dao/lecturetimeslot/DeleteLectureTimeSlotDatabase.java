package dao.lecturetimeslot;

import constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

import java.sql.*;

/**
 * @author Harjot Singh
 */
public class DeleteLectureTimeSlotDatabase {
  private static final String STATEMENT = "DELETE FROM lecturetimeslot WHERE roomname=? AND date=? AND starttime=? RETURNING *;";
  private final LectureTimeSlot lts;
  private LectureTimeSlot deletedLts;
  private final Connection connection;
  private static final Logger logger = LogManager.getLogger("harjot_singh_logger");

  public DeleteLectureTimeSlotDatabase(final Connection connection, final LectureTimeSlot lts) {
    this.connection = connection;
    this.lts = lts;
  }

  public LectureTimeSlot execute() throws SQLException {
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = connection.prepareStatement(STATEMENT);
      ps.setString(1, lts.getRoomName());
      ps.setDate(2, lts.getDate());
      ps.setTime(3, lts.getStartTime());

      rs = ps.executeQuery();
      logger.debug("gwa.dao.DeleteLTSD: query executed successfully");
      if (rs.next()) {
        String roomName = rs.getString(Constants.LECTURETIMESLOT_ROOMNAME);
        Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
        Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
        int courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
        String courseName = rs.getString(Constants.LECTURETIMESLOT_COURSENAME);
        String substitution = rs.getString(Constants.LECTURETIMESLOT_SUBSTITUTION);
        deletedLts = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);

        logger.debug("gwa.dao.DeleteLTSD: %s DELETED SUCCESSFULLY.".formatted(new Timestamp(System.currentTimeMillis()), deletedLts.toString()));
      } else
        logger.debug("gwa.dao.DeleteLTSD: %s NOT FOUND.".formatted(new Timestamp(System.currentTimeMillis()), lts.toString()));
    } catch (SQLException ex) {
      logger.error("gwa.dao.DeleteLTSD: %s".formatted(new Timestamp(System.currentTimeMillis()), ex.getMessage()));
      throw ex;
    } finally {
      if (rs != null) rs.close();
      if (ps != null) ps.close();
      connection.close();
    }
    return deletedLts;
  }
}
