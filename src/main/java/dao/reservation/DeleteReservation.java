package dao.reservation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.Reservation;

import java.sql.*;

/**
 * Given a user and a lecture time slot, delete from the database the Reservation associated
 *
 * @author Fatjon Freskina
 */
public class DeleteReservation {
    private static final Logger logger = LogManager.getLogger("fatjon_freskina_appender");
    private static final String STATEMENT = """
            DELETE FROM reservation 
            WHERE trainee = ? 
            AND lectureroom = ? 
            AND lecture date = ? 
            AND lecturestarttime = ?;
            """;
    private final Connection con;
    private final Reservation reservation;

    public DeleteReservation(final Connection con, final Reservation reservation) {
        this.con = con;
        this.reservation = reservation;
    }

    public void execute() throws SQLException {

        try (PreparedStatement pstmt = con.prepareStatement(STATEMENT)) {
            pstmt.setString(1, reservation.getTrainee());
            pstmt.setString(2, reservation.getRoom());
            pstmt.setDate(3, reservation.getLectureDate());
            pstmt.setTime(4, reservation.getLectureStartTime());
            pstmt.execute();
        } catch (SQLException exc) {
            logger.error("[INFO] DeleteReservation.java - %s - An exception occurred during query execution.\n%s\n".formatted(new Timestamp(System.currentTimeMillis()), exc.getMessage()));
            throw exc;
        } finally {
            con.close();
        }

    }
}

