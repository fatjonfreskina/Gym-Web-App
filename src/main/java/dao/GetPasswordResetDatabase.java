package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.PasswordReset;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Get all password reset that belongs to the user specified to the query and that are still valid
 * (expiration time not yet reached).
 */
public class GetPasswordResetDatabase
{
    private static final Logger logger = LogManager.getLogger("marco_alessio_appender");

    private static final String STATEMENT = """
            SELECT pr.token
            FROM passwordreset as pr
            WHERE pr.person = ? AND pr.expirationdate <= ?
            """;

    private final Connection con;

    private final PasswordReset passwordReset;

    public GetPasswordResetDatabase(final Connection con, final PasswordReset passwordReset)
    {
        this.con = con;
        this.passwordReset = passwordReset;
    }

    public List<PasswordReset> execute() throws SQLException
    {
        List<PasswordReset> result = new ArrayList<>();

        PreparedStatement stm = null;
        ResultSet rs = null;

        try
        {
            stm = con.prepareStatement(STATEMENT);
            stm.setString(1, passwordReset.getPerson());
            stm.setTimestamp(2, passwordReset.getExpirationDate());

            rs = stm.executeQuery();
            while (rs.next())
            {
                final String token = rs.getString("token");
                final Timestamp expDate = rs.getTimestamp("expirationDate");
                final String person = rs.getString("person");
                result.add(new PasswordReset(token, expDate, person));
            }

            logger.debug("[INFO] GetPasswordResetDatabase - %s - Query successfully done.\n".formatted(
                    new Timestamp(System.currentTimeMillis())));
        }
        catch (SQLException exc)
        {
            logger.error("[INFO] GetPasswordResetDatabase - %s - An exception occurred during query execution.\n%s\n".
                    formatted(new Timestamp(System.currentTimeMillis()), exc.getMessage()));

            throw exc;
        }
        finally
        {
            if (stm != null)
                stm.close();

            if (rs != null)
                rs.close();

            con.close();
        }

        return result;
    }
}