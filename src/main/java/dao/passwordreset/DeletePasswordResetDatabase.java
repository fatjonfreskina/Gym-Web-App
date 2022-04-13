package dao.passwordreset;

import resource.PasswordReset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Riccardo Forzan
 */
public class DeletePasswordResetDatabase {

    private static final String STATEMENT = "DELETE FROM passwordreset WHERE token = ?";

    private final Connection con;
    private final PasswordReset passwordReset;

    public DeletePasswordResetDatabase(final Connection con, final PasswordReset passwordReset) {
        this.con = con;
        this.passwordReset = passwordReset;
    }

    public void execute() throws SQLException {
        try (PreparedStatement stm = con.prepareStatement(STATEMENT)) {
            stm.setString(1, passwordReset.getToken());
            stm.execute();
        } finally {
            con.close();
        }
    }
}