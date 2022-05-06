package dao.lecturetimeslot;

import constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This DAO is used to get a list of lecture time slots by passing a range of Dates
 *
 * @author Harjot Singh
 */
public class GetLectureTimeSlotsByDateDatabase {

  /**
   * The query to be executed
   */
  // Can not use == to compare dates therefore use <= and >= instead
  private static final String STATEMENT = "SELECT * FROM lecturetimeslot WHERE date >= ? and date <= ?";

  /**
   * Date to insert in the query
   */
  private final Date date;

  /**
   * The connection to the database
   */
  private final Connection connection;

  /**
   *
   * Parametric constructor
   *
   * @param connection to the database
   * @param date to be inserted in the query
   */
  public GetLectureTimeSlotsByDateDatabase(final Connection connection, final Date date) {
    this.connection = connection;
    this.date = date;
  }

  /**
   *
   * Execute the query
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
      ps.setDate(1, date);
      ps.setDate(2, date);

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
