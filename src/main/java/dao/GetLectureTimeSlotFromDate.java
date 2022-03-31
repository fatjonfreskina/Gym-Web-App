package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetLectureTimeSlotFromDate {
  private static final String STATEMENT = "SELECT * FROM lecturetimeslot WHERE date >= ? and date <= ?";
                                          // Can not use == to compare dates therefore use <= and >= instead
  private final Date date;

  private final Connection connection;
  private static final Logger logger = LogManager.getLogger("harjot_singh_appender");

  public GetLectureTimeSlotFromDate(final Connection connection, Date date) {
    this.connection = connection;
    this.date = date;
  }

  public List<LectureTimeSlot> getLectureTimeSlotFromDate() throws SQLException {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<LectureTimeSlot> result = new ArrayList<>();
    try {
      ps = connection.prepareStatement(STATEMENT);
      ps.setDate(1, date);
      ps.setDate(2, date);

      rs = ps.executeQuery();
      logger.debug("[DEBUG] gwa.dao.GetLTSFromDate - %s - query executed successfully, retrieving data...\n".formatted(
          new Timestamp(System.currentTimeMillis())));
      while (rs.next()) {
        String roomName = rs.getString("roomname");
        Date date = rs.getDate("date");
        Time startTime = rs.getTime("starttime");
        int courseEditionId = rs.getInt("courseeditionid");
        String courseName = rs.getString("coursename");
        String substitution = rs.getString("substitution");
        LectureTimeSlot lts = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);

        logger.debug("[DEBUG] gwa.dao.GetLTSFromDate - %s - Retrieved: %s.\n".formatted(
            new Timestamp(System.currentTimeMillis()), lts.toString()));
        result.add(lts);
      }
    } catch (SQLException ex) {
      logger.error("[ERROR] gwa.dao.GetLTSFromDate - %s - Exception:\n%s\n".
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
