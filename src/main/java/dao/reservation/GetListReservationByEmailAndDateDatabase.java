package dao.reservation;

import constants.Constants;
import jakarta.servlet.http.HttpSession;
import resource.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Return a list of reservations of a user within a given date interval.
 * It requires as input parameters:
 * <ul>
 *     <li>The email address of the user.</li>
 *     <li>The start date of the interval.</li>
 *     <li>The end date of the interval.</li>
 * </ul>
 *
 * @author Fatjon Freskina, Marco Alessio
 */
public class GetListReservationByEmailAndDateDatabase
{
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

    public GetListReservationByEmailAndDateDatabase(final Connection con, final String email,
                                                    final Date fromDate, final Date toDate)
    {
        this.con = con;
        this.email = email;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public List<Reservation> execute() throws SQLException
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        final List<Reservation> reservations = new ArrayList<>();

        try
        {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, email);
            pstmt.setDate(2, fromDate);
            pstmt.setDate(3, toDate);

            rs = pstmt.executeQuery();

            while (rs.next())
            {
                final String room = rs.getString(Constants.RESERVATION_LECTUREROOM);
                final Date date = rs.getDate(Constants.RESERVATION_LECTUREDATE);
                final Time startTime = rs.getTime(Constants.RESERVATION_LECTURESTARTTIME);

                reservations.add(new Reservation(room, date, startTime));
            }
        }
        finally
        {
            if (rs != null)
                rs.close();

            if (pstmt != null)
                pstmt.close();

            con.close();
        }

        return reservations;
    }
}
