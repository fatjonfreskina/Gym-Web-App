package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

import java.sql.*;
public class DeleteLectureTimeSlotDatabase {
  private static final String STATEMENT = "DELETE FROM lecturetimeslot WHERE roomname=? AND date=? AND starttime=? RETURNING *;";
  private final LectureTimeSlot lts;
  private LectureTimeSlot deletedLts;
  private final Connection connection;
  private static final Logger logger = LogManager.getLogger("harjot_singh_appender");

  public DeleteLectureTimeSlotDatabase(Connection connection, LectureTimeSlot lts) {
    this.connection = connection;
    this.lts = lts;
  }

  public LectureTimeSlot deleteLectureTimeSlot() throws SQLException {
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
      ps = connection.prepareStatement(STATEMENT);
      ps.setString(1, lts.getRoomName());
      ps.setDate(2, lts.getDate());
      ps.setTime(3, lts.getStartTime());

      rs = ps.executeQuery();
      logger.debug("[DEBUG] gwa.dao.DeleteLTSD - %s - query executed successfully\n".formatted(
          new Timestamp(System.currentTimeMillis())));
      if (rs.next()) {
        String roomName = rs.getString("roomname");
        Date date = rs.getDate("date");
        Time startTime = rs.getTime("starttime");
        int courseEditionId = rs.getInt("courseeditionid");
        String courseName = rs.getString("coursename");
        String substitution = rs.getString("substitution");
        deletedLts = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);

        logger.debug("[DEBUG] gwa.dao.DeleteLTSD - %s -  Deleted Successfully: %s.\n".formatted(
            new Timestamp(System.currentTimeMillis()), deletedLts.toString()));
      }else
        logger.debug("[DEBUG] gwa.dao.DeleteLTSD - %s - Following LectureTimeSlot not found: %s.\n".formatted(
            new Timestamp(System.currentTimeMillis()), lts.toString()));
    } catch (SQLException ex) {
      logger.error("[ERROR] gwa.dao.DeleteLTSD - %s - Exception:\n%s\n".
          formatted(new Timestamp(System.currentTimeMillis()), ex.getMessage()));
      throw ex;
    } finally {
      if (rs != null)
        rs.close();
      if (ps != null)
        ps.close();
      connection.close();
    }
    return deletedLts;
  }
}
