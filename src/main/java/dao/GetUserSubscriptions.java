package dao;

import resource.Person;
import resource.Subscription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Returns all the subscriptions for a given user
 */
public class GetUserSubscriptions {

    private static final String STATEMENT =
            "SELECT * FROM subscription WHERE trainee = ?";
    private final Connection con;

    public GetUserSubscriptions(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves all the subscriptions associated to a user
     * @param trainee
     * @return List of all the subscriptions associated to the given trainee
     * @throws SQLException
     */
    public List<Subscription> GetUserSubscriptions (Person trainee) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Subscription> list = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            //Inject the ID of the trainee into the query
            preparedStatement.setString( 1, trainee.getEmail());
            //Execute the query
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                Subscription tmp = null;

                // TODO: Implement parameter reading

                list.add(tmp);

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
