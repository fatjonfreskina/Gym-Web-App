package dao.reservation;

import constants.Constants;
import resource.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Riccardo Tumiati
 * */

public class GetAvailableSlotsReservation {
    private static final String STATEMENT = "SELECT (slots - reservations) as available_slots"+
            " FROM room JOIN"+
                " (SELECT lectureroom, count(*) AS reservations"+
                " FROM reservation"+
                " WHERE lecturedate = ? AND lectureroom = ? AND lecturestarttime = ?"+
                " GROUP BY lectureroom, lecturedate, lecturestarttime) AS res "+
            " ON (room.name = res.lectureroom)";
    private final Connection con;
    private final Reservation reservation;


    public GetAvailableSlotsReservation(final Connection con, final Reservation reservation) {
        this.con = con;
        this.reservation = reservation;
    }

    public int execute() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int availables = -1;
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setDate(1, reservation.getLectureDate());
            preparedStatement.setString(2, reservation.getRoom());
            preparedStatement.setTime(3, reservation.getLectureStartTime());
            //Execute the query
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                availables = rs.getInt("available_slots");
            }
        } finally {
            if (rs != null) rs.close();
            if (preparedStatement != null) preparedStatement.close();
            con.close();
        }

        return availables;
    }
}
