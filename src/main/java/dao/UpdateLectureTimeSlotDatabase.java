package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

import java.sql.*;

public class UpdateLectureTimeSlotDatabase {
  private final String STATEMENT =
      "UPDATE gwa.lecturetimeslot " +
          "SET courseeditionid = ?," +
          "coursename = ?," +
          "substitution = ?" +
          "WHERE roomname = ?" +
          "AND date = ?" +
          "AND starttime = ?" +
          "RETURNING *;";
  private final LectureTimeSlot lts;
  private final Connection connection;
  private static final Logger logger = LogManager.getLogger("harjot_singh_appender");

  public UpdateLectureTimeSlotDatabase(Connection connection, LectureTimeSlot lts) {
    this.connection = connection;
    this.lts = lts;
  }

  public LectureTimeSlot execute() throws SQLException {
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
      logger.debug("[DEBUG] gwa.dao.UpdateLTSD - %s - query executed successfully\n".formatted(
          new Timestamp(System.currentTimeMillis())));
      if (rs.next()) {
        String roomName = rs.getString("roomname");
        Date date = rs.getDate("date");
        Time startTime = rs.getTime("starttime");
        int courseEditionId = rs.getInt("courseeditionid");
        String courseName = rs.getString("coursename");
        String substitution = rs.getString("substitution");
        updatedLts = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);

        logger.debug("[DEBUG] gwa.dao.UpdateLTSD - %s - Updated Successfully: %s.\n".formatted(
            new Timestamp(System.currentTimeMillis()), updatedLts.toString()));
      }else
        logger.debug("[DEBUG] gwa.dao.UpdateLTSD - %s - Following LectureTimeSlot not found: %s.\n".formatted(
            new Timestamp(System.currentTimeMillis()), lts.toString()));
    } catch (SQLException ex) {
      logger.error("[ERROR] gwa.dao.UpdateLTSD - %s - Exception during insertion.\n%s\n".
          formatted(new Timestamp(System.currentTimeMillis()), ex.getMessage()));

      throw ex;
    } finally {
      if (rs != null)
        rs.close();
      if (ps != null)
        ps.close();
      connection.close();
    }

    return updatedLts;
  }
}
