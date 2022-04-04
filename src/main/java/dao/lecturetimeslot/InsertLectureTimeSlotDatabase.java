package dao.lecturetimeslot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

import java.sql.*;

public class InsertLectureTimeSlotDatabase {

  private final String STATEMENT = "" +
      "INSERT INTO lecturetimeslot(roomname, date, starttime, courseeditionid,coursename,substitution)" +
      "VALUES (?, ?, ?, ?, ?, ?) ";

  private final LectureTimeSlot lts;
  private final Connection connection;
  private static final Logger logger = LogManager.getLogger("harjot_singh_appender");

  public InsertLectureTimeSlotDatabase(final Connection connection, final LectureTimeSlot lts) {
    this.connection = connection;
    this.lts = lts;
  }

  public LectureTimeSlot execute() throws SQLException {
    PreparedStatement ps = null;
    ResultSet rs = null;

    LectureTimeSlot insertedLTS = null;
    try {
      ps = connection.prepareStatement(STATEMENT);
      ps.setString(1, lts.getRoomName());
      ps.setDate(2, lts.getDate());
      ps.setTime(3, lts.getStartTime());
      ps.setInt(4, lts.getCourseEditionId());
      ps.setString(5, lts.getCourseName());
      ps.setString(6, lts.getSubstitution());

      rs = ps.executeQuery();
      if(rs.next()) {
        String roomName = rs.getString("roomname");
        Date date = rs.getDate("date");
        Time startTime = rs.getTime("starttime");
        int courseEditionId = rs.getInt("courseeditionid");
        String courseName = rs.getString("coursename");
        String substitution = rs.getString("substitution");
        insertedLTS = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);

        logger.debug("[DEBUG] gwa.dao.InsertLTSD - %s - %s Inserted successfully.\n".formatted(
            new Timestamp(System.currentTimeMillis()), insertedLTS));
      }
    } catch (SQLException ex) {
      logger.error("[ERROR] gwa.dao.InsertLTSD - %s - Exception during insertion.\n%s\n".
          formatted(new Timestamp(System.currentTimeMillis()), ex.getMessage()));
      throw ex;
    } finally {
      if (rs != null)
        rs.close();
      if (ps != null)
        ps.close();
      connection.close();
    }
    return insertedLTS;
  }
}
