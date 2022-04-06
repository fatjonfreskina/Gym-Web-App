package dao.person;

import resource.Person;
import resource.TypeOfRoles;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetUserRolesDatabase {

    private static final String STATEMENT = "SELECT * FROM personroles JOIN person ON personroles.person = person.email WHERE email = ?";

    private final Connection connection;
    private final Person person;

    public GetUserRolesDatabase(final Connection connection, final Person person) {
        this.connection = connection;
        this.person = person;
    }

    public List<TypeOfRoles> execute() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<TypeOfRoles> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(STATEMENT);
            preparedStatement.setString(1, person.getEmail());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                list.add(new TypeOfRoles(resultSet.getString("role")));
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            connection.close();
        }
        return list;
    }
/* //TODO Ok inserire qua o creare un Dao a parte?
    public String executeForHighestRole() throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result;
        List<TypeOfRoles> list = new ArrayList<>();

        try
        {
            preparedStatement = connection.prepareStatement(STATEMENT);
            preparedStatement.setString(1, person.getEmail());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                list.add(new TypeOfRoles(resultSet.getString("role")));
            }

        } finally {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            connection.close();
        }
        // Check the highest role
        if (list.contains("secretary"))
        {
            result = "secretary";
            return result;
        } else if (list.contains("trainer")){
            result = "trainer";
            return result;
        } else if (list.contains("trainee")) {
            result = "trainee";
            return result;
        } else {
            result = "No role for this user";
            return result;
        }
        }
*/

}
