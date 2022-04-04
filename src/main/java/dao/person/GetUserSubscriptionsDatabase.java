package dao.person;

import resource.Person;
import resource.Subscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Returns all the subscriptions for a given user
 * @author Riccardo Forzan
 */
public class GetUserSubscriptionsDatabase {

    private static final String STATEMENT =
            "SELECT * FROM subscription WHERE trainee = ?";
    private final Connection con;
    private final Person trainee;
    /**
     * Parametric constructor of the class
     * @param con JDBC connection to the database
     */
    public GetUserSubscriptionsDatabase(final Connection con,final Person trainee)
    {
        this.con = con;
        this.trainee = trainee;
    }

    /**
     * Retrieves all the subscriptions associated to a user
     * @return List of all the subscriptions associated to the given trainee
     * @throws SQLException
     */
    public List<Subscription> execute () throws SQLException {
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
                //Read all the parameters
                int courseEditionID = resultSet.getInt("courseeditionid");
                String courseName = resultSet.getString("coursename");
                int duration = resultSet.getInt("duration");
                Date startDay = resultSet.getDate("startday");
                int discount = resultSet.getInt("discount");
                String trainee_email = resultSet.getString("trainee");
                //Create the object
                Subscription tmp = new Subscription(courseEditionID,courseName,duration,startDay,discount,trainee_email);
                //Add the object to the return list
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
