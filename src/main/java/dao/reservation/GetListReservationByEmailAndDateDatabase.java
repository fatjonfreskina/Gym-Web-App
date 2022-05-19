package dao.reservation;

import constants.Constants;
import resource.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class used to get the list of reservations by an email and a date
 *
 * @author Fatjon Freskina, Marco Alessio
 */
public class GetListReservationByEmailAndDateDatabase
{
    /**
     * The query statement, select the fields of lecture time slot given a trainee and a date greater and smaller
     * than the passed one
     */
    private static final String STATEMENT = """
            SELECT lectureroom, lecturedate, lecturestarttime
            FROM reservation
            WHERE trainee = ? AND (lecturedate >= ? AND lecturedate <= ?)
            ORDER BY lecturedate ASC, lecturestarttime ASC;
            """;

    private final Connection con;
    private final String email;
    private final Date fromDate;
    private final Date toDate;

    /**
     * Constructor for this class
     *
     * @param con      the connection to the database
     * @param email    the email parameter
     * @param fromDate the date, lower bound
     * @param toDate   the date, higher bound
     */
    public GetListReservationByEmailAndDateDatabase(final Connection con, final String email,
                                                    final Date fromDate, final Date toDate) {
        this.con = con;
        this.email = email;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    /**
     * Executes the sql statement
     *
     * @return the list containing Reservation object that matched the query; if no match returns null
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<Reservation> execute() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        final List<Reservation> reservations = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, email);
            pstmt.setDate(2, fromDate);
            pstmt.setDate(3, toDate);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                final String room = rs.getString(Constants.RESERVATION_LECTUREROOM);
                final Date date = rs.getDate(Constants.RESERVATION_LECTUREDATE);
                final Time startTime = rs.getTime(Constants.RESERVATION_LECTURESTARTTIME);

                reservations.add(new Reservation(room, date, startTime));
            }
        } finally {
            if (rs != null)
                rs.close();

            if (pstmt != null)
                pstmt.close();

            con.close();
        }

        return reservations;
    }
}
