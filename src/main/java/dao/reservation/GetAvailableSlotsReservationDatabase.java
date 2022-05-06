package dao.reservation;

import resource.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class used to get the available slots for a lecture
 * @author Riccardo Tumiati
 * */

public class GetAvailableSlotsReservationDatabase {
    /**
     * The query statement: aims to find the available slots for a lecture time slot
     */
    private static final String STATEMENT = "SELECT (slots - reservations) as available_slots"+
            " FROM room JOIN"+
                " (SELECT lectureroom, count(*) AS reservations"+
                " FROM reservation"+
                " WHERE lecturedate = ? AND lectureroom = ? AND lecturestarttime = ?"+
                " GROUP BY lectureroom, lecturedate, lecturestarttime) AS res "+
            " ON (room.name = res.lectureroom)";
    private final Connection con;
    private final Reservation reservation;

    /**
     * Constructor for this class
     * @param con the connection to the database
     * @param reservation the reservation object
     */
    public GetAvailableSlotsReservationDatabase(final Connection con, final Reservation reservation) {
        this.con = con;
        this.reservation = reservation;
    }

    /**
     * Executes the sql statements retrieving the available slots
     * @return the available slots as integer number
     * @throws SQLException if the are
     */
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
