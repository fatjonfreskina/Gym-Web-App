package dao.person;

import constants.Constants;
import resource.Person;
import resource.TypeOfRoles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Returns a list of roles associated to a person
 *
 * @author Francesco Caldivezzi
 */
public class GetPersonRolesDatabase {

    /**
     * Query to be executed
     */
    private static final String STATEMENT = "SELECT * FROM personroles JOIN person ON personroles.person = person.email WHERE email = ?";

    /**
     * Connection to the database
     */
    private final Connection connection;

    /**
     * Person to get the roles from
     */
    private final Person person;

    /**
     * Parametric constructor
     *
     * @param connection database connection
     * @param person     person to get the roles from
     */
    public GetPersonRolesDatabase(final Connection connection, final Person person) {
        this.connection = connection;
        this.person = person;
    }

    /**
     * Executes the query
     *
     * @return roles corresponding to the given person
     * @throws SQLException thrown if something goes wrong while querying the database
     */
    public List<TypeOfRoles> execute() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<TypeOfRoles> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(STATEMENT);
            preparedStatement.setString(1, person.getEmail());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                list.add(new TypeOfRoles(resultSet.getString(Constants.PERSONROLES_ROLE)));
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            connection.close();
        }
        return list;
    }

}
