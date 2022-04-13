package dao.passwordreset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.PasswordReset;

import java.sql.*;

/**
 * @author Marco Alessio
 */
public class InsertPasswordResetDatabase {
    private static final Logger logger = LogManager.getLogger("marco_alessio_appender");

    private static final String STATEMENT =
            "INSERT INTO passwordreset (token, expirationdate, person) VALUES (?, ?, ?)";

    private final Connection con;
    private final PasswordReset passwordReset;

    public InsertPasswordResetDatabase(final Connection con, final PasswordReset passwordReset) {
        this.con = con;
        this.passwordReset = passwordReset;
    }

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