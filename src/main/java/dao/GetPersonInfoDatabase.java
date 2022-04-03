package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.Person;

import java.sql.*;

public class GetPersonInfoDatabase
{
    private static final Logger LOGGER = LogManager.getLogger("marco_alessio_appender");
    private static final String STATEMENT = "SELECT * FROM gwa.person WHERE email = ?";

    private final Connection connection;
    private final String email;

    public GetPersonInfoDatabase(final Connection connection, final String email)
    {
        this.connection = connection;
        this.email = email;
    }

    public Person execute() throws SQLException
    {
        PreparedStatement stm = null;
        ResultSet rs =  null;
        Person result;

        try
        {
            stm = connection.prepareStatement(STATEMENT);
            stm.setString(1, email);

            rs = stm.executeQuery();

            // Find the size of the result set.
            rs.last();
            final int size = rs.getRow();

            // Check if there is a result.
            if (size == 0)
                throw new SQLException("No Person exists with email: %s.".formatted(email));

            // Prevent SQL injection attacks.
            if (size > 1)
                throw new SQLException("More than one result for email: %s.".formatted(email));

            // Reset the ResultSet to the first (and only) item.
            rs.first();
            rs.next();

            result = new Person(
                    (Integer[]) rs.getArray("role").getArray(),
                    rs.getString("email"),
                    rs.getString("avatarpath"),
                    rs.getString("password"),
                    rs.getString("address"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("TaxCode"),
                    rs.getDate("birthdate"),
                    rs.getString("telephone")
            );
        }
        catch (SQLException exc)
        {
            LOGGER.error("[INFO] GetPersonInfoDatabase - %s - An exception occurred during query execution.\n%s\n".
                    formatted(new Timestamp(System.currentTimeMillis()), exc.getMessage()));

            throw exc;
        }
        finally
        {
            if (stm != null)
                stm.close();

            if (rs != null)
                rs.close();

            connection.close();
        }

        return result;
    }
}