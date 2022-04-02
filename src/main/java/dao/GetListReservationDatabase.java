package dao;

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

public final class GetListReservationDatabase 
{

    private static final Logger logger = LogManager.getLogger("fatjon_freskina_appender");

    private static final String STATEMENT = """
        SELECT * 
        FROM reservation
        WHERE trainee = ?
        AND lecturedate > ?
        ORDER BY lecturedate ASC;
        """;

    private final Connection con;

    private final String trainee;

    private final Date lectureDate;

    public GetListReservationDatabase(final Connection con, final String trainee, final Date lectureDate)
    {
        this.con = con;
        this.trainee = trainee;
        this.lectureDate = lectureDate;
    }

    public List<Reservation> listReservationDatabase() throws SQLException
    {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        final List<Reservation> reservations = new ArrayList<Reservation>();

        try
        {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, trainee);
            pstmt.setDate(2, lectureDate);

            rs = pstmt.executeQuery();

            while (rs.next())
            {
                reservations.add(new Reservation(rs.getString("trainee"), rs.getString("lectureroom"),
                 rs.getDate("lecturedate"), rs.getTimestamp("lecturestarttme")));
            }

            logger.debug("[INFO] GetListReservationDatabase - %s - Query successfully done.\n".formatted(
                    new Timestamp(System.currentTimeMillis())));
        }
        catch (SQLException exc)
        {
            logger.error("[INFO] GetListReservationDatabase - %s - An exception occurred during query execution.\n%s\n".
             formatted(new Timestamp(System.currentTimeMillis()), exc.getMessage()));
        }
        
        finally 
        {
            if (rs != null) 
            {
                rs.close();
            }
            if (pstmt != null) 
            {
                pstmt.close();
            }
            con.close();
        }
        return reservations;
    }
}
