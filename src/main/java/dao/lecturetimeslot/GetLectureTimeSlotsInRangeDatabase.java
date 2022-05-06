package dao.lecturetimeslot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;
import resource.LectureTimeSlot;

/**
 * This DAO is used to get an ordered by date list of lecture time slots in a range of dates
 *
 * @author Harjot Singh
 */
public class GetLectureTimeSlotsInRangeDatabase {

  /**
   * The SELECT query to be executed
   */
  private static final String STATEMENT = "SELECT * FROM lecturetimeslot WHERE date >= ? AND date <= ? ORDER BY date, starttime ASC";

  /**
   * The connection to the database
   */
  private final Connection connection;

  /**
   * Range of dates
   */
  private final Date from, to;

  /**
   *
   * Parametric constructor
   *
   * @param connection the connection to the database
   * @param from the date from which to look for
   * @param to the date up to look for
   */
  public GetLectureTimeSlotsInRangeDatabase(final Connection connection, final Date from, final Date to) {
    this.connection = connection;
    this.from = from;
    this.to = to;
  }

  /**
   *
   * Executes the query
   *
   * @return a list containing LectureTimeSlot objects that matched the query
   * @throws SQLException
   */
  public List<LectureTimeSlot> execute() throws SQLException {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<LectureTimeSlot> result = new ArrayList<>();
    try {
      ps = connection.prepareStatement(STATEMENT);
      ps.setDate(1, from);
      ps.setDate(2, to);

      rs = ps.executeQuery();

      while (rs.next()) {
        String roomName = rs.getString(Constants.LECTURETIMESLOT_ROOMNAME);
        Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
        Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
        int courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
        String courseName = rs.getString(Constants.LECTURETIMESLOT_COURSENAME);
        String substitution = rs.getString(Constants.LECTURETIMESLOT_SUBSTITUTION);
        LectureTimeSlot lts = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);

        result.add(lts);
      }
    } finally {
      if (rs != null) rs.close();
      if (ps != null) ps.close();
      connection.close();
    }
    return result;
  }
}
