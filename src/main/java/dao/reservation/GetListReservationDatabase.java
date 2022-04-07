package dao.reservation;

import constants.Constants;
import resource.Reservation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
*
Return a list of (future) reservations given a date and a user
*
*/

public class GetListReservationDatabase {

    private static final Logger logger = LogManager.getLogger("fatjon_freskina_appender");

    private static final String STATEMENT = """
            SELECT * 
            FROM reservation
            WHERE trainee = ?
            AND lecturedate > ?
            ORDER BY lecturedate ASC;
            """;

    private final Connection con;
    private final Reservation reservation;


    public GetListReservationDatabase(final Connection con, final Reservation reservation) {
        this.con = con;
        this.reservation = reservation;

    }

    public List<Reservation> execute() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        final List<Reservation> reservations = new ArrayList<Reservation>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, reservation.getTrainee());
            pstmt.setDate(2, reservation.getLectureDate());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                reservations.add(new Reservation(rs.getString(Constants.RESERVATION_TRAINEE), rs.getString(Constants.RESERVATION_LECTUREROOM), rs.getDate(Constants.RESERVATION_LECTUREDATE), rs.getTimestamp(Constants.RESERVATION_LECTURESTARTTIME)));
            }

            logger.debug("[INFO] GetListReservationDatabase - %s - Query successfully done.\n".formatted(
                    new Timestamp(System.currentTimeMillis())));
        } catch (SQLException exc) {
            logger.error("[INFO] GetListReservationDatabase - %s - An exception occurred during query execution.\n%s\n".
                    formatted(new Timestamp(System.currentTimeMillis()), exc.getMessage()));
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            con.close();
        }
        return reservations;
    }
}
