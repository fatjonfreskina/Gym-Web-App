package dao.person;

import resource.Person;
import resource.Subscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Riccardo Forzan
 */
public class GetAllUsersDatabase {

    private static final String STATEMENT = "SELECT * FROM person";
    private final Connection con;

    public GetAllUsersDatabase(Connection con) {
        this.con = con;
    }

    public List<Person> execute () throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Person> list = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            //Execute the query
            rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                list.add(new Person(rs.getString("email"),rs.getString("name"),rs.getString("surname"),
                        rs.getString("psw"),rs.getString("taxcode"),rs.getDate("birthdate"),
                        rs.getString("telephone"),rs.getString("address"),rs.getString("avatarpath")));
            }
        }
        finally {
            if (rs != null)
                rs.close();
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }

        return list;
    }

}
