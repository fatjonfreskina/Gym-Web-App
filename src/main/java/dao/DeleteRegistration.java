package dao;

import resource.Reservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;

/*
*
Given a user and a lecture time slot, delete from the database the Reservation associated
*
*/


public class DeleteRegistration {

    private static final Logger logger = LogManager.getLogger("fatjon_freskina_appender");

    private static final String STATEMENT = """
        DELETE FROM reservation 
        WHERE trainee = ? 
        AND lectureroom = ? 
        AND lecture date = ? 
        AND lecturestarttime = ?;
        """;

    private final Connection con;

    private final String user;
    private final String room;
    private final Date lectureDate;
    private final Timestamp startTime;

    public DeleteRegistration(final Connection con, final String user, final String room, final Date lecturDate, final Timestamp starttime)
    {
        this.con = con;
        this.user = user;
        this.room = room;
        this.lectureDate = lecturDate;
        this.startTime = starttime;
    }

    public void deleteReservation() throws SQLException
    {
        PreparedStatement pstmt = null;
        //Do we need a resultSet object with a Delete?..
        try
        {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, user);
            pstmt.setString(2, room);
            pstmt.setDate(3, lectureDate);
            pstmt.setTimestamp(4, startTime);

            pstmt.executeQuery();

            
        }
    }



    
}
