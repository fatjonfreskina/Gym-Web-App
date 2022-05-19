package dao.lecturetimeslot;

import constants.Constants;
import resource.LectureTimeSlot;
import resource.Person;

import java.sql.*;


/**
 * Retrieves the LectureTimeSlot that is being held in this precise moment for a given course id
 *
 * @author Andrea Pasin
 */
public class GetLectureTimeSlotNowSubstitutionDatabase {
    /**
     * The SELECT query to be executed
     */
    private static final String STATEMENT = """
            SELECT * FROM lecturetimeslot WHERE date = CURRENT_DATE 
            and starttime <= CURRENT_TIME and substitution = ? and (starttime + INTERVAL '2 hour') >= CURRENT_TIME;
            """;
    /**
     * The connection to the database
     */
    private final Connection connection;

    /**
     * Substitution
     */
    private final Person substitution;

    /**
     * Parametric constructor
     *
     * @param connection      the connection to the database
     */
    public GetLectureTimeSlotNowSubstitutionDatabase(final Connection connection, final Person substitution) {
        this.connection = connection;
        this.substitution = substitution;
    }

    /**
     * Executes the query
     *
     * @return LectureTimeSlot object that matched the query
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public LectureTimeSlot execute() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        LectureTimeSlot result = null;
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1,substitution.getEmail());
            rs = ps.executeQuery();

            if (rs.next()) {
                String roomName = rs.getString(Constants.LECTURETIMESLOT_ROOMNAME);
                Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
                Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
                int courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
                String courseName = rs.getString(Constants.LECTURETIMESLOT_COURSENAME);
                String substitution = rs.getString(Constants.LECTURETIMESLOT_SUBSTITUTION);
                result = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            connection.close();
        }
        return result;
    }
}