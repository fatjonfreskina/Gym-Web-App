package dao.person;

import constants.Constants;
import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Retrieves from the database a person given the email
 *
 * @author Riccardo Forzan
 * @author Francesco Caldivezzi
 */
public class GetPersonByEmailDatabase {

    /**
     * Query to be executed
     */
    private static final String STATEMENT = "SELECT * FROM person WHERE email = ?";

    /**
     * Connection to the database
     */
    private final Connection connection;

    /**
     * Email field
     */
    private final String email;

    /**
     * Parametric constructor
     * @param connection database connection object
     * @param email email to retrieve the user
     */
    public GetPersonByEmailDatabase(final Connection connection, final String email) {
        this.connection = connection;
        this.email = email;
    }

    /**
     * Executes the query
     * @return Person object associated with the given email address
     * @throws SQLException thrown if something goes wrong while querying the database
     */
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