package dao.person;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.Person;
import utils.InputValidation;

import java.sql.*;

public class GetUserByEmailDatabase
{
    private static final Logger LOGGER = LogManager.getLogger("marco_alessio_appender");
    private static final String STATEMENT = "SELECT * FROM person WHERE email = ?";

    private final Connection connection;
    private final String email;

    public GetUserByEmailDatabase(final Connection connection, final String email)
    {
        this.connection = connection;
        this.email = email;
    }

    public Person execute() throws SQLException
    {
        PreparedStatement stm = null;
        ResultSet rs =  null;
        Person result = null;
        try
        {
            stm = connection.prepareStatement(STATEMENT);
            stm.setString(1, email);

            rs = stm.executeQuery();

            if(rs.next())
                result = new Person(
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("psw"), //password
                        rs.getString("taxCode"),
                        rs.getDate("birthDate"),
                        rs.getString("telephone"),
                        rs.getString("address"),
                        rs.getString("avatarPath")
                        );
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