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
public class GetLectureTimeSlotsByCourseDatabase {
  private static final String STATEMENT = "SELECT * FROM lecturetimeslot WHERE courseEditionId = ? and courseName = ?";
  private final Connection connection;
  private final LectureTimeSlot lectureTimeSlot;
  private static final Logger logger = LogManager.getLogger("harjot_singh_logger");

  public GetLectureTimeSlotsByCourseDatabase(final Connection connection, final LectureTimeSlot lectureTimeSlot) {
    this.connection = connection;
    this.lectureTimeSlot = lectureTimeSlot;
  }

  public List<LectureTimeSlot> execute() throws SQLException {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<LectureTimeSlot> result = new ArrayList<>();
    try {
      ps = connection.prepareStatement(STATEMENT);
      ps.setInt(1, lectureTimeSlot.getCourseEditionId());
      ps.setString(2, lectureTimeSlot.getCourseName());

      rs = ps.executeQuery();

      logger.debug("gwa.dao.GetLTSByCourseD: query executed successfully, retrieving data...");
      while (rs.next()) {
        String roomName = rs.getString(Constants.LECTURETIMESLOT_ROOMNAME);
        Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
        Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
        int courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
        String courseName = rs.getString(Constants.LECTURETIMESLOT_COURSENAME);
        String substitution = rs.getString(Constants.LECTURETIMESLOT_SUBSTITUTION);
        LectureTimeSlot lts = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);

        logger.trace("gwa.dao.GetLTSByCourseD: Retrieved " + lts);
        result.add(lts);
      }
    } catch (SQLException ex) {
      logger.error("gwa.dao.GetLTSByCourseD: " + ex.getMessage());
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
