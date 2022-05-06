package dao.passwordreset;

import constants.Constants;
import resource.PasswordReset;

import java.sql.*;

/**
 * Get all password reset that belongs to the user specified to the query and that are still valid
 * (expiration time not yet reached).
 *
 * @author Riccardo Forzan
 * @author Marco Alessio
 */
public class GetPasswordResetDatabase {

    /**
     * Select statement to execute on the database
     */
    private static final String STATEMENT = """
            SELECT person, expirationdate
            FROM passwordreset
            WHERE token = ?
            """;

    /**
     * Connection to the database
     */
    private final Connection con;

    /**
     * Token from which information will be retrieved
     */
    private final String token;

    /**
     * Parametric constructor
     *
     * @param con   database connection
     * @param token password reset token
     */
    public GetPasswordResetDatabase(final Connection con, final String token) {
        this.con = con;
        this.token = token;
    }

    /**
     * Gets a password reset from the database
     *
     * @return PasswordReset object associated with the token string given
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public PasswordReset execute() throws SQLException {

        PasswordReset item = null;

        PreparedStatement stm = null;

        stm = con.prepareStatement(STATEMENT);
        stm.setString(1, token);
        try (ResultSet rs = stm.executeQuery()) {
            if (rs.next()) {
                final String person = rs.getString(Constants.PASSWORDRESET_PERSON);
                final Timestamp expDate = rs.getTimestamp(Constants.PASSWORDRESET_EXPIRATIONDATE);
                item = new PasswordReset(token, expDate, person);
            }
        } finally {
            con.close();
        }
        return item;
    }

}