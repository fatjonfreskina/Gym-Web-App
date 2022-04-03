package dao;

import resource.Person;
import resource.Subscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Riccardo Forzan
 */
public class GetAllUsersDatabase {

    private static final String STATEMENT = "SELECT * FROM gwa.person";
    private final Connection con;

    public GetAllUsersDatabase(Connection con) {
        this.con = con;
    }

    public List<Person> execute () throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Person> list = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            //Execute the query
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                //Read all the parameters
                String email = resultSet.getString("email");
                String[] role = (String[]) resultSet.getArray("role").getArray();
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String psw = resultSet.getString("psw");
                String taxcode = resultSet.getString("taxcode");
                Date birthdate = resultSet.getDate("birthdate");
                String telephone = resultSet.getString("telephone");
                String address = resultSet.getString("address");
                String avatarpath = resultSet.getString("avatarpath");
                //Create the object
                //Person tmp = new Person(role,email,avatarpath,psw,address,name,surname,taxcode,birthdate,telephone);
                //Add the object to the return list
                //list.add(tmp);
            }
        }
        finally {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }

        return list;
    }

}
