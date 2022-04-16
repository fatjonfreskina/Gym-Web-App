package dao.person;

import constants.Constants;
import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Francesco Caldivezzi
 */
public class GetPersonByTaxCodeDatabase {
    private static final String STATEMENT = "SELECT * FROM person WHERE taxcode = ?";

    private final Connection connection;
    private final Person person;

    public GetPersonByTaxCodeDatabase(final Connection connection, final Person person) {
        this.connection = connection;
        this.person = person;
    }

    public Person execute() throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Person result = null;

        try {
            stm = connection.prepareStatement(STATEMENT);
            stm.setString(1, person.getTaxCode());
            rs = stm.executeQuery();

            if (rs.next())
                result = new Person(rs.getString(Constants.PERSON_EMAIL), rs.getString(Constants.PERSON_NAME), rs.getString(Constants.PERSON_SURNAME),
                        rs.getString(Constants.PERSON_PSW), rs.getString(Constants.PERSON_TAXCODE), rs.getDate(Constants.PERSON_BIRTHDATE),
                        rs.getString(Constants.PERSON_TELEPHONE), rs.getString(Constants.PERSON_ADDRESS), rs.getString(Constants.PERSON_AVATARPATH));
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
