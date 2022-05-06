package dao.passwordreset;

import resource.PasswordReset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Marco Alessio
 */
public class InsertPasswordResetDatabase {

    /**
     * Query to be exedcuted on the database
     */
    private static final String STATEMENT =
            "INSERT INTO passwordreset (token, expirationdate, person) VALUES (?, ?, ?)";

    /**
     * Database connection object
     */
    private final Connection con;

    /**
     * PasswordReset object to be inserted in the database
     */
    private final PasswordReset passwordReset;

    /**
     * Parametric constructor
     *
     * @param con           database connection
     * @param passwordReset PasswordReset object to be inserted in the database
     */
    public InsertPasswordResetDatabase(final Connection con, final PasswordReset passwordReset) {
        this.con = con;
        this.passwordReset = passwordReset;
    }

    /**
     * Execute the query
     *
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public void execute() throws SQLException {
        try (PreparedStatement stm = con.prepareStatement(STATEMENT)) {
            stm.setString(1, passwordReset.getToken());
            stm.setTimestamp(2, passwordReset.getExpirationDate());
            stm.setString(3, passwordReset.getPerson());
            stm.execute();
        } finally {
            con.close();
        }
    }
}