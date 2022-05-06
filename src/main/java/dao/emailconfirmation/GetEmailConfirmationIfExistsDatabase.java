package dao.emailconfirmation;

import resource.EmailConfirmation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Gets the EmailConfirmation object associated to a person
 *
 * @author Andrea Pasin
 */
public class GetEmailConfirmationIfExistsDatabase {

    /**
     * Query to execute on the database
     */
    private static final String STATEMENT = "SELECT * FROM emailconfirmation WHERE person = ?";

    /**
     * Database connection
     */
    private final Connection connection;

    /**
     * EmailConfirmation object
     */
    private final EmailConfirmation emailConfirmation;

    /**
     * Constructor for this class
     *
     * @param connection        the connection to the database
     * @param emailConfirmation the email of the person to check if exists
     */
    public GetEmailConfirmationIfExistsDatabase(final Connection connection, final EmailConfirmation emailConfirmation) {
        this.connection = connection;
        this.emailConfirmation = emailConfirmation;
    }

    /**
     * Gets the EmailConfirmation object associated to the person
     *
     * @return EmailConfirmation associated to the person (if any)
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public EmailConfirmation execute() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        EmailConfirmation result = null;
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1, emailConfirmation.getPerson());
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
