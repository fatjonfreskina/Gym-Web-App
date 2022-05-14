package dao.person;

import constants.Constants;
import resource.Person;
import resource.Subscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Returns all the subscriptions for a given user
 *
 * @author Riccardo Forzan
 */
public class GetPersonSubscriptionsDatabase {

    /**
     * Query to be executed
     */
    private static final String STATEMENT =
            "SELECT * FROM subscription WHERE trainee = ?";

    /**
     * Connection to the database
     */
    private final Connection con;

    /**
     * Person to get the subscriptions from
     */
    private final Person trainee;

    /**
     * Parametric constructor of the class
     *
     * @param con JDBC connection to the database
     * @param trainee the trainee
     */
    public GetPersonSubscriptionsDatabase(final Connection con, final Person trainee) {
        this.con = con;
        this.trainee = trainee;
    }

    /**
     * Retrieves all the subscriptions associated to a user
     *
     * @return List of all the subscriptions associated to the given trainee
     * @throws SQLException if there is an issue concerning the database
     */
    public List<Subscription> execute() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Subscription> list = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            //Inject the ID of the trainee into the query
            preparedStatement.setString(1, trainee.getEmail());
            //Execute the query
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //Read all the parameters
                int courseEditionID = resultSet.getInt(Constants.SUBSCRIPTION_COURSEEDITIONID);
                String courseName = resultSet.getString(Constants.SUBSCRIPTION_COURSENAME);
                int duration = resultSet.getInt(Constants.SUBSCRIPTION_DURATION);
                Date startDay = resultSet.getDate(Constants.SUBSCRIPTION_STARTDAY);
                int discount = resultSet.getInt(Constants.SUBSCRIPTION_DISCOUNT);
                String trainee_email = resultSet.getString(Constants.SUBSCRIPTION_TRAINEE);
                //Create the object
                Subscription tmp = new Subscription(courseEditionID, courseName, duration, startDay, discount, trainee_email);
                //Add the object to the return list
                list.add(tmp);
            }
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }

        return list;
    }

}
