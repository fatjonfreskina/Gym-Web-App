package dao.subscription;

import resource.LectureTimeSlot;
import java.sql.*;

/**
 * DAO class used to get the expiration date of the subscription from a lecture
 * @author Riccardo Tumiati
 */
public class GetSubscriptionExpirationByLTSDatabase {

    private final String STATEMENT = """
            SELECT startday+ (duration || ' day')::interval AS expiration
            FROM lecturetimeslot AS lts JOIN subscription AS s ON (lts.courseeditionid = s.courseeditionid AND lts.coursename = s.coursename)
            WHERE s.trainee = ? AND lts.roomname = ? AND lts.date = ? AND lts.starttime = ?
            """;

    private final Connection conn;

    private final LectureTimeSlot lts;

    private final String email;

    /**
     * Constructor for this class
     * @param conn  the database connection
     * @param lts  the lecture
     * @param email  the trainee's email address
     */
    public GetSubscriptionExpirationByLTSDatabase(Connection conn, LectureTimeSlot lts, String email){
        this.conn = conn;
        this.lts = lts;
        this.email = email;
    }

    /**
     * Executes the sql statement retrieving the subscription expiration date from a given lecture
     * @return the subscription expiration date
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public Date execute() throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Date expiration = null;

        try {
            stm = conn.prepareStatement(STATEMENT);
            stm.setString(1, email);
            stm.setString(2, lts.getRoomName());
            stm.setDate(3, lts.getDate());
            stm.setTime(4,lts.getStartTime());
            rs = stm.executeQuery();

            while (rs.next()) {
                expiration = rs.getDate("expiration");
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            conn.close();
        }
        return expiration;
    }
}
