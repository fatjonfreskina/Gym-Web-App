package dao.reservation;

import constants.Constants;
import resource.Reservation;

import java.sql.*;

/**
 * Given a user and a lecture time slot, delete from the database the Reservation associated
 *
 * @author Fatjon Freskina
 * @author Harjot Singh
 */
public class DeleteReservationDatabase {

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

    /**
     * @param con         the connection to the database
     * @param reservation the reservation object
     */
    public DeleteReservationDatabase(final Connection con, final Reservation reservation) {
        this.con = con;
        this.reservation = reservation;
    }

    /**
     * Executes the sql statement to get the reservation
     *
     * @return the Reservation object that has been removed
     * @throws SQLException is thrown if something goes wrong while querying the database
     */

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

            }
        } finally {
            if (rs != null) rs.close();
            con.close();
        }
        return deletedReservation;
    }
}

