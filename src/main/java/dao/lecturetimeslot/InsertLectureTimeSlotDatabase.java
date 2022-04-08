package dao.lecturetimeslot;

import constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

import java.sql.*;

/**
 * @author Harjot Singh
 */
public class InsertLectureTimeSlotDatabase {

  private final String STATEMENT = "INSERT INTO lecturetimeslot(roomname, date, starttime, courseeditionid,coursename,substitution)" + "VALUES (?, ?, ?, ?, ?, ?) ";

  private final LectureTimeSlot lts;
  private final Connection connection;
  private static final Logger logger = LogManager.getLogger("harjot_singh_logger");

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
      if (rs.next()) {
        String roomName = rs.getString(Constants.LECTURETIMESLOT_ROOMNAME);
        Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
        Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
        int courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
        String courseName = rs.getString(Constants.LECTURETIMESLOT_COURSENAME);
        String substitution = rs.getString(Constants.LECTURETIMESLOT_SUBSTITUTION);
        insertedLTS = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);

        logger.debug("gwa.dao.InsertLTSD: %s Inserted successfully.".formatted(insertedLTS));
      }
    } catch (SQLException ex) {
      logger.error("gwa.dao.InsertLTSD: %s.".formatted(ex.getMessage()));
      throw ex;
    } finally {
      if (rs != null) rs.close();
      if (ps != null) ps.close();
      connection.close();
    }
    return insertedLTS;
  }
}
