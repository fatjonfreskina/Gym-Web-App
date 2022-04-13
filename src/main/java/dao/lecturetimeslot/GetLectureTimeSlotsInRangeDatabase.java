package dao.lecturetimeslot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

/**
 * @author Harjot Singh
 */
public class GetLectureTimeSlotsInRangeDatabase {
  private static final String STATEMENT = "SELECT * FROM lecturetimeslot WHERE date >= ? AND date <= ? ORDER BY date, starttime ASC";

  private final Connection connection;
  private static final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = "gwa.dao.lecturetimeslot.GetLTSInRangeD: ";
  private final Date from, to;

  public GetLectureTimeSlotsInRangeDatabase(final Connection connection, final Date from, final Date to) {
    this.connection = connection;
    this.from = from;
    this.to = to;
  }

  public List<LectureTimeSlot> execute() throws SQLException {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<LectureTimeSlot> result = new ArrayList<>();
    try {
      ps = connection.prepareStatement(STATEMENT);
      ps.setDate(1, from);
      ps.setDate(2, to);

      rs = ps.executeQuery();
      logger.debug(loggerClass + "query executed successfully, retrieving data...");
      while (rs.next()) {
        String roomName = rs.getString(Constants.LECTURETIMESLOT_ROOMNAME);
        Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
        Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
        int courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
        String courseName = rs.getString(Constants.LECTURETIMESLOT_COURSENAME);
        String substitution = rs.getString(Constants.LECTURETIMESLOT_SUBSTITUTION);
        LectureTimeSlot lts = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);

        logger.trace(loggerClass + "Retrieved " + lts);
        result.add(lts);
      }
    } catch (SQLException ex) {
      logger.error(loggerClass + ex.getMessage());
      throw ex;
    } finally {
      if (rs != null) rs.close();
      if (ps != null) ps.close();
      connection.close();
    }
    return result;
  }
}
