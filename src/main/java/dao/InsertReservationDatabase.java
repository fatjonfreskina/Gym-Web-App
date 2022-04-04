package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.Reservation;

import java.sql.*;

public class InsertReservationDatabase 
{
    private static final Logger logger = LogManager.getLogger("fatjon_freskina_appender");
    
    private static final String STATEMENT = """
            INSERT INTO reservation (trainee, lectureroom, lecturedate, lecturestarttime)
            VALUES (?,?,?,?);
            """;
    
    private final Connection con;
    private final Reservation reservation;

    public InsertReservationDatabase (final Connection con, final Reservation reservation)
    {
        this.con = con;
        this.reservation = reservation;
    }

    public void execute() throws SQLException
    {
        try (PreparedStatement stm = con.prepareStatement(STATEMENT))
        {
            stm.setString(1, reservation.getTrainee());
            stm.setString(2, reservation.getRoom());
            stm.setDate(3, reservation.getLectureDate());
            stm.setTimestamp(4, reservation.getLectureStartTime());

            stm.execute();
            logger.error("[INFO] InsertReservationDatabase - %s - Insertion successfully done.\n".
                formatted(new Timestamp(System.currentTimeMillis())));
            
        } catch (SQLException exc)
        {
        logger.error("[INFO] InsertReservationDatabase - %s - An exception occurred during insertion.\n%s\n".
            formatted(new Timestamp(System.currentTimeMillis()), exc.getMessage()));

            throw exc;
        } finally
        {
            con.close();
        }
    }
}
