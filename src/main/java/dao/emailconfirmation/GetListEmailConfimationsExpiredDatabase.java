package dao.emailconfirmation;

import resource.EmailConfirmation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gets all the EmailConfirmation object from the database which are expired
 *
 * @author Francesco Caldivezzi
 */
public class GetListEmailConfimationsExpiredDatabase {

    /**
     * Query to execute on the database
     */
    private static final String STATEMENT = "select * from emailconfirmation where expirationdate <= ?";

    /**
     * Database connection
     */
    private final Connection connection;

    /**
     * Date parameter
     */
    private Timestamp date;

    /**
     * @param connection database connection
     * @param date       maximum date
     */
    public GetListEmailConfimationsExpiredDatabase(final Connection connection, Timestamp date) {
        this.connection = connection;
        this.date = date;
    }

    /**
     * Gets the list of all expired EmailConfirmation entries
     *
     * @return list of all expired EmailConfirmation entries
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<EmailConfirmation> execute() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<EmailConfirmation> result = null;
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setTimestamp(1, date);
            rs = ps.executeQuery();

            result = new ArrayList<>();
            while (rs.next())
                result.add(new EmailConfirmation(rs.getString("person"), rs.getString("token"), rs.getTimestamp("expirationdate")));

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
