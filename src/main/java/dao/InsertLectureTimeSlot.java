package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

import java.sql.*;

public class InsertLectureTimeSlot {
  private final String STATEMENT = "INSERT INTO lecturetimeslot(roomname, date, starttime, courseeditionid,coursename,substitution) VALUES (?, ?, ?, ?, ?, ?)";
  private final LectureTimeSlot lts;
  private final Connection connection;
  private static final Logger logger = LogManager.getLogger("harjot_singh_appender");

  public InsertLectureTimeSlot(Connection connection, LectureTimeSlot lts) {
    this.connection = connection;
    this.lts = lts;
  }

  public void insertMedicalCertificate() throws SQLException {
    PreparedStatement ps = null;
    try {
      ps = connection.prepareStatement(STATEMENT);
      ps.setString(1, lts.getRoomName());
      ps.setDate(2, lts.getDate());
      ps.setTime(3, lts.getStartTime());
      ps.setInt(4, lts.getCourseEditionId());
      ps.setString(5, lts.getCourseName());
      ps.setString(6, lts.getSubstitution());

      ps.execute();
      logger.debug("[DEBUG] dao.InsertLTS - %s - %s Inserted successfully.\n".formatted(
          new Timestamp(System.currentTimeMillis()),lts));
    } catch (SQLException ex) {
      logger.error("[ERROR] dao.InsertLTS - %s - Exception during insertion.\n%s\n".
          formatted(new Timestamp(System.currentTimeMillis()), ex.getMessage()));

      throw ex;
    } finally {
      if (ps != null)
        ps.close();
      connection.close();
    }
  }
}
