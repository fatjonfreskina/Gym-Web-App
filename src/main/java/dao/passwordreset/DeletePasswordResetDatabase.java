package dao.passwordreset;

import resource.PasswordReset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Deletes a password reset into the database
 *
 * @author Riccardo Forzan
 */
public class DeletePasswordResetDatabase {

    /**
     * The query to execute on the database
     */
    private static final String STATEMENT = "DELETE FROM passwordreset WHERE token = ?";

    /**
     * Connection to the database
     */
    private final Connection con;

    /**
     * Object to delete
     */
    private final PasswordReset passwordReset;

    /**
     * Parametric constructor
     *
     * @param con           connection to the database
     * @param passwordReset password reset instance to delete
     */
    public DeletePasswordResetDatabase(final Connection con, final PasswordReset passwordReset) {
        this.con = con;
        this.passwordReset = passwordReset;
    }

    /**
     * Executes the delete query
     *
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public void execute() throws SQLException {
        try (PreparedStatement stm = con.prepareStatement(STATEMENT)) {
            stm.setString(1, passwordReset.getToken());
            stm.execute();
        } finally {
            con.close();
        }
    }
}