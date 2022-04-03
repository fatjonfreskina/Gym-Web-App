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
        if (!true)
            throw new SQLException("Email: %s\n".formatted(email));

        PreparedStatement stm = null;
        ResultSet rs =  null;
        Person result = null;

        try
        {
            stm = connection.prepareStatement(STATEMENT);
            stm.setString(1, email);

            rs = stm.executeQuery();

            // Check if there is a result.
            if (!rs.next())
                throw new SQLException("No Person exists with email: %s.".formatted(email));

           /* result = new Person(
                    (String[]) rs.getArray("role").getArray(),
                    rs.getString("email"),
                    rs.getString("avatarpath"),
                    rs.getString("psw"), //password
                    rs.getString("address"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("TaxCode"),
                    rs.getDate("birthdate"),
                    rs.getString("telephone")
            );*/

            // Prevent SQL injection attacks.
            if (rs.next())
                throw new SQLException("More than one result for email: %s.".formatted(email));
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