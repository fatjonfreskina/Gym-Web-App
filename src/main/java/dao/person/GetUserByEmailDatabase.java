package dao.person;

import constants.Constants;
import resource.Person;

import java.sql.*;

/**
 * @author Riccardo Forzan
 * @author Riccardo Tumiati
 * @author Francesco Caldivezzi
 */
public class GetUserByEmailDatabase {
    private static final String STATEMENT = "SELECT * FROM person WHERE email = ?";

    private final Connection connection;
    private final String email;

    public GetUserByEmailDatabase(final Connection connection, final String email) {
        this.connection = connection;
        this.email = email;
    }

    public Person execute() throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Person result = null;
        try {
            stm = connection.prepareStatement(STATEMENT);
            stm.setString(1, email);

            rs = stm.executeQuery();

            if (rs.next())
                result = new Person(
                        rs.getString(Constants.PERSON_EMAIL),
                        rs.getString(Constants.PERSON_NAME),
                        rs.getString(Constants.PERSON_SURNAME),
                        rs.getString(Constants.PERSON_PSW), //password
                        rs.getString(Constants.PERSON_TAXCODE),
                        rs.getDate(Constants.PERSON_BIRTHDATE),
                        rs.getString(Constants.PERSON_TELEPHONE),
                        rs.getString(Constants.PERSON_ADDRESS),
                        rs.getString(Constants.PERSON_AVATARPATH)
                );
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