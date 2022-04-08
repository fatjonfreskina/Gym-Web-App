package dao.lecturetimeslot;

import constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

import java.sql.*;

/**
 * @author Harjot Singh
 */
public class UpdateLectureTimeSlotDatabase {
  private final String STATEMENT = "UPDATE lecturetimeslot " + "SET courseeditionid = ?," + "coursename = ?," + "substitution = ?" + "WHERE roomname = ?" + "AND date = ?" + "AND starttime = ?" + "RETURNING *;";
  private final LectureTimeSlot lts;
  private final Connection connection;
  private static final Logger logger = LogManager.getLogger("harjot_singh_logger");

  public UpdateLectureTimeSlotDatabase(final Connection connection, final LectureTimeSlot lts) {
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
      logger.debug("gwa.dao.UpdateLTSD: query executed successfully");
      if (rs.next()) {
        String roomName = rs.getString(Constants.LECTURETIMESLOT_ROOMNAME);
        Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
        Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
        int courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
        String courseName = rs.getString(Constants.LECTURETIMESLOT_COURSENAME);
        String substitution = rs.getString(Constants.LECTURETIMESLOT_SUBSTITUTION);
        updatedLts = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);

        logger.debug("gwa.dao.UpdateLTSD: Updated Successfully %s.".formatted(updatedLts.toString()));
      } else logger.debug("gwa.dao.UpdateLTSD: %s NOT FOUND.".formatted(lts.toString()));
    } catch (SQLException ex) {
      logger.error("gwa.dao.UpdateLTSD: %s.".formatted(ex.getMessage()));
      throw ex;
    } finally {
      if (rs != null) rs.close();
      if (ps != null) ps.close();
      connection.close();
    }

    return updatedLts;
  }
}
