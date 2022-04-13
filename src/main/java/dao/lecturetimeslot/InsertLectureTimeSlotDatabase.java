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


  public InsertLectureTimeSlotDatabase(final Connection connection, final LectureTimeSlot lts) {
    this.connection = connection;
    this.lts = lts;
  }

  public void execute() throws SQLException {
    PreparedStatement ps = null;

    try
    {
      ps = connection.prepareStatement(STATEMENT);
      ps.setString(1, lts.getRoomName());
      ps.setDate(2, lts.getDate());
      ps.setTime(3, lts.getStartTime());
      ps.setInt(4, lts.getCourseEditionId());
      ps.setString(5, lts.getCourseName());
      ps.setString(6, lts.getSubstitution());

      ps.execute();

    } finally {
      if (ps != null)
        ps.close();
      connection.close();
    }
  }
}
