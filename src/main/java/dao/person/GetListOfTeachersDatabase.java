package dao.person;

import constants.Constants;
import org.checkerframework.common.reflection.qual.NewInstance;
import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * This DAO is used to GET the list of the instructors from the database
 *
 * @author Francesco Caldivezzi
* */
public class GetListOfTeachersDatabase {

    /**
     * The SELECT query to be executed
     */
    private static final String STATEMENT = "SELECT * FROM person join personroles ON personroles.person = person.email WHERE role = 'trainer'";

    /**
     * The connection to the database
     */
    private final Connection connection;

    /**
     *
     * Parametric contructor
     *
     * @param connection the connection to the database
     */
    public GetListOfTeachersDatabase(final Connection connection)
    {
        this.connection = connection;
    }

    /**
     *
     * Executes the select
     *
     * @return a list containing Person objects that matched the query
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<Person> execute() throws SQLException
    {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Person> result = new ArrayList<>();
        try {
            stm = connection.prepareStatement(STATEMENT);

            rs = stm.executeQuery();

            while (rs.next())
            {
                result.add(new Person(
                        rs.getString(Constants.PERSON_EMAIL),
                        rs.getString(Constants.PERSON_NAME),
                        rs.getString(Constants.PERSON_SURNAME),
                        rs.getString(Constants.PERSON_PSW), //password
                        rs.getString(Constants.PERSON_TAXCODE),
                        rs.getDate(Constants.PERSON_BIRTHDATE),
                        rs.getString(Constants.PERSON_TELEPHONE),
                        rs.getString(Constants.PERSON_ADDRESS),
                        rs.getString(Constants.PERSON_AVATARPATH)
                ));
            }
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
