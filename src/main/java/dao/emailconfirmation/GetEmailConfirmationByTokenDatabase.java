package dao.emailconfirmation;

import resource.EmailConfirmation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Francesco Caldivezzi
 */
public class GetEmailConfirmationByTokenDatabase {

    /**
     * Query to execute on the database
     */
    private static final String STATEMENT = "SELECT * FROM emailconfirmation WHERE token = ?";

    /**
     * Database connection
     */
    private final Connection connection;

    /**
     * EmailConfirmation object
     */
    private final EmailConfirmation emailConfirmation;

    /**
     * Parametric constructor
     *
     * @param connection        database connection
     * @param emailConfirmation EmailConfirmation object from which the token is retrieved
     */
    public GetEmailConfirmationByTokenDatabase(final Connection connection, final EmailConfirmation emailConfirmation) {
        this.connection = connection;
        this.emailConfirmation = emailConfirmation;
    }

    /**
     * Retrieves an EmailConfirmation object from the database given the token associated
     *
     * @return EmailConfirmation object with the token associated
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public EmailConfirmation execute() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        EmailConfirmation result = null;
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1, emailConfirmation.getToken());
            rs = ps.executeQuery();

            if (rs.next())
                result = new EmailConfirmation(rs.getString("person"),
                        rs.getString("token"), rs.getTimestamp("expirationdate"));
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
