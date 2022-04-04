package dao.person;

import resource.Subscription;

import java.sql.*;

/**
 * Inserts a new subscription in the database
 * @author Riccardo Forzan
 */
public class InsertUserSubscriptionDatabase {

    private static final String STATEMENT =
            "INSERT INTO subscription (courseeditionid, coursename, duration, startday, discount, trainee) VALUES (?,?,?,?,?,?);";
    private final Connection con;
    private final Subscription subscription;

    /**
     * Parametric constructor for the class
     * @param con JDBC database connection
     * @param subscription instance of Subscription {@link resource.Subscription} to add in the database
     */
    public InsertUserSubscriptionDatabase(final Connection con, final Subscription subscription) {
        this.con = con;
        this.subscription = subscription;
    }

    /**
     * Inserts this object into the database
     * @throws SQLException if a database access error occurs
     */
    public void execute() throws SQLException {

        try (PreparedStatement preparedStatement = con.prepareStatement(STATEMENT)) {
            preparedStatement.setInt(1, subscription.getCourseEditionID());
            preparedStatement.setString(2, subscription.getCourseName());
            preparedStatement.setInt(3, subscription.getDuration());
            preparedStatement.setDate(4, subscription.getStartDay());
            preparedStatement.setInt(5, subscription.getDiscount());
            preparedStatement.setString(6, subscription.getTrainee());
            preparedStatement.execute();

        } finally {
            con.close();
        }

    }

}
