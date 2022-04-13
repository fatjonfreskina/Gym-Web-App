package dao.reservation;

import constants.Constants;
import resource.LectureTimeSlot;
import resource.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Pasin
 * @author Harjot Singh
 */
public class GetListReservationByLectureDatabase {

    private static final String STATEMENT = "SELECT * FROM reservation WHERE lecturedate = ? and lectureroom= ? and lecturestarttime= ?";

    private final Connection con;
    private final LectureTimeSlot lectureTimeSlot;


    public GetListReservationByLectureDatabase(final Connection con, final LectureTimeSlot lectureTimeSlot) {
        this.con = con;
        this.lectureTimeSlot = lectureTimeSlot;
    }

    public List<Reservation> execute() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        final List<Reservation> reservations = new ArrayList<Reservation>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setDate(1, lectureTimeSlot.getDate());
            pstmt.setString(2, lectureTimeSlot.getRoomName());
            pstmt.setTime(3, lectureTimeSlot.getStartTime());

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
