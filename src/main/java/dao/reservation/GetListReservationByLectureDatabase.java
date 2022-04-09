package dao.reservation;

import constants.Constants;
import resource.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Pasin
 */
public class GetListReservationByLectureDatabase {

    private static final String STATEMENT = "SELECT * FROM reservation WHERE lecturedate = ? and lectureroom= ? and lecturestarttime= ?";

    private final Connection con;
    private final Reservation reservation;


    public GetListReservationByLectureDatabase(final Connection con, final Reservation reservation) {
        this.con = con;
        this.reservation = reservation;

    }

    public List<Reservation> execute() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        final List<Reservation> reservations = new ArrayList<Reservation>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setDate(1, reservation.getLectureDate());
            pstmt.setString(2, reservation.getRoom());
            pstmt.setTime(3, reservation.getLectureStartTime());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                reservations.add(new Reservation(rs.getString(Constants.RESERVATION_TRAINEE), rs.getString(Constants.RESERVATION_LECTUREROOM), rs.getDate(Constants.RESERVATION_LECTUREDATE), rs.getTime(Constants.RESERVATION_LECTURESTARTTIME)));
            }
        }
        finally {
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
