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
 * @author Francesco Caldivezzi
* */
public class GetListOfTeachersDatabase
{
    private static final String STATEMENT = "SELECT * FROM person join personroles ON personroles.person = person.email WHERE role = 'trainer'";

    private final Connection connection;

    public GetListOfTeachersDatabase(final Connection connection)
    {
        this.connection = connection;
    }

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
