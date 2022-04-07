package dao.passwordreset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.PasswordReset;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Get all password reset that belongs to the user specified to the query and that are still valid
 * (expiration time not yet reached).
 *
 * @author Riccardo Forzan
 * @authon Marco Alessio
 */
public class GetPasswordResetDatabase {
    private static final Logger logger = LogManager.getLogger("marco_alessio_appender");

    private static final String STATEMENT = """
            SELECT person, expirationdate
            FROM passwordreset
            WHERE token = ?
            """;

    private final Connection con;

    private final String token;

    public GetPasswordResetDatabase(final Connection con, final String token) {
        this.con = con;
        this.token = token;
    }

    public PasswordReset execute() throws SQLException {

        PasswordReset item = null;

        PreparedStatement stm = null;
        ResultSet rs = null;

        stm = con.prepareStatement(STATEMENT);
        stm.setString(1, token);
        rs = stm.executeQuery();
        if (rs.next()) {
            final String person = rs.getString("person");
            final Timestamp expDate = rs.getTimestamp("expirationdate");
            item = new PasswordReset(token, expDate, person);
        }
        return item;
    }

}