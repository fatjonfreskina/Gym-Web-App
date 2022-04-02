package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.PasswordReset;

import java.sql.*;

public class InsertPasswordResetDatabase
{
    private static final Logger logger = LogManager.getLogger("marco_alessio_appender");

    private static final String STATEMENT =
            "INSERT INTO gwa.passwordreset (token, expirationdate, person) VALUES (?, ?, ?)";

    private final Connection con;
    private final PasswordReset passwordReset;

    public InsertPasswordResetDatabase(Connection con, PasswordReset passwordReset)
    {
        this.con = con;
        this.passwordReset = passwordReset;
    }

    public void create() throws SQLException
    {

        try (PreparedStatement stm = con.prepareStatement(STATEMENT))
        {
            stm.setString(1, passwordReset.getToken());
            stm.setTimestamp(2, passwordReset.getExpirationDate());
            stm.setString(3, passwordReset.getPerson());
            stm.execute();

            logger.debug("[INFO] InsertPasswordResetDatabase - %s - Insertion successfully done.\n".formatted(
                    new Timestamp(System.currentTimeMillis())));
        } catch (SQLException exc)
        {
            logger.error("[INFO] InsertPasswordResetDatabase - %s - An exception occurred during insertion.\n%s\n".
                    formatted(new Timestamp(System.currentTimeMillis()), exc.getMessage()));

            throw exc;
        } finally
        {
            con.close();
        }
    }
}