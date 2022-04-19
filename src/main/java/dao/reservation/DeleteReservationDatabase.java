package dao.reservation;

import constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.Reservation;

import java.sql.*;

/**
 * Given a user and a lecture time slot, delete from the database the Reservation associated
 *
 * @author Fatjon Freskina
 * @author Harjot Singh
 */
public class DeleteReservationDatabase {
  private static final Logger logger = LogManager.getLogger("fatjon_freskina_logger");
  private static final String STATEMENT = """
      DELETE FROM reservation 
      WHERE trainee = ? 
      AND lectureroom = ? 
      AND lecturedate = ? 
      AND lecturestarttime = ?
      RETURNING *;
      """;
  private final Connection con;
  private final Reservation reservation;

  public DeleteReservationDatabase(final Connection con, final Reservation reservation) {
    this.con = con;
    this.reservation = reservation;
  }

  public Reservation execute() throws SQLException {
    Reservation deletedReservation = null;
    ResultSet rs = null;
    try (PreparedStatement pstmt = con.prepareStatement(STATEMENT)) {
      pstmt.setString(1, reservation.getTrainee());
      pstmt.setString(2, reservation.getRoom());
      pstmt.setDate(3, reservation.getLectureDate());
      pstmt.setTime(4, reservation.getLectureStartTime());
      rs = pstmt.executeQuery();
      if (rs.next()) {
        String trainee = rs.getString(Constants.RESERVATION_TRAINEE);
        Date date = rs.getDate(Constants.RESERVATION_LECTUREDATE);
        Time startTime = rs.getTime(Constants.RESERVATION_LECTURESTARTTIME);
        String room = rs.getString(Constants.RESERVATION_LECTUREROOM);
        deletedReservation = new Reservation(trainee, room, date, startTime);

        logger.debug("gwa.dao.DeleteReservationD: %s DELETED SUCCESSFULLY.".formatted(new Timestamp(System.currentTimeMillis()), deletedReservation.toString()));
      } else
        logger.debug("gwa.dao.DeleteReservationD: %s NOT FOUND.".formatted(new Timestamp(System.currentTimeMillis()), reservation.toString()));
    } catch (SQLException ex) {
      logger.error("gwa.dao.DeleteReservationD: " + ex.getMessage());
      throw ex;
    } finally {
      if (rs != null) rs.close();
      con.close();
    }
    return deletedReservation;
  }
}

