package dao.person;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetUserByTaxCodeDatabase {
    private static final String STATEMENT = "SELECT * FROM person WHERE taxcode = ?";

    private final Connection connection;
    private final Person person;

    public GetUserByTaxCodeDatabase(final Connection connection, final Person person) {
        this.connection = connection;
        this.person = person;
    }

    public Person execute() throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Person result = null;

        try {
            stm = connection.prepareStatement(STATEMENT);
            stm.setString(1, person.getTaxcode());
            rs = stm.executeQuery();

            if (rs.next())
                result = new Person(rs.getString("email"), rs.getString("name"), rs.getString("surname"),
                        rs.getString("psw"), rs.getString("taxcode"), rs.getDate("birthdate"),
                        rs.getString("telephone"), rs.getString("address"), rs.getString("avatarpath"));
        } finally {
            if (stm != null)
                stm.close();
            if (rs != null)
                rs.close();
            connection.close();
        }
        return result;
    }
}
