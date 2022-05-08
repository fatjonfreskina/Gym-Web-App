package dao.reservation;

import resource.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DAO class used to insert a reservation into the database
 *
 * @author Fatjon Freskina
 */

public class InsertReservationDatabase {

    private static final String STATEMENT = """
            INSERT INTO reservation (trainee, lectureroom, lecturedate, lecturestarttime)
            VALUES (?,?,?,?);
            """;

    private final Connection con;
    private final Reservation reservation;

    /**
     * Constructor for this class
     *
     * @param con         the connection to the database
     * @param reservation the reservation object that needs to be inserted
     */

    public InsertReservationDatabase(final Connection con, final Reservation reservation) {
        this.con = con;
        this.reservation = reservation;
    }

    /**
     * Executes the sql statement which inserts the reservation into the database
     *
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public void execute() throws SQLException {
        try (PreparedStatement stm = con.prepareStatement(STATEMENT)) {
            stm.setString(1, reservation.getTrainee());
            stm.setString(2, reservation.getRoom());
            stm.setDate(3, reservation.getLectureDate());
            stm.setTime(4, reservation.getLectureStartTime());
            stm.execute();
        } finally {
            con.close();
        }
    }
}
