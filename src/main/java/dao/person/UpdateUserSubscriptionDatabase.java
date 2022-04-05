package dao.person;

import resource.Subscription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Updates a subscription in the database
 *
 * @author Riccardo Forzan
 */
public class UpdateUserSubscriptionDatabase {

    private static final String STATEMENT =
            "UPDATE subscription SET startday = ?, discount = ?, trainee = ? WHERE courseeditionid = ?, coursename = ?, duration = ?;";
    private final Connection con;
    private final Subscription subscription;

    /**
     * Parametric constructor for the class
     *
     * @param con          JDBC database connection
     * @param subscription instance of Subscription {@link Subscription} to update in the database
     */
    public UpdateUserSubscriptionDatabase(final Connection con, final Subscription subscription) {
        this.con = con;
        this.subscription = subscription;
    }

    /**
     * Updates a subscription object in the database
     *
     * @throws SQLException if a database access error occurs
     */
    public void execute() throws SQLException {

        try (PreparedStatement preparedStatement = con.prepareStatement(STATEMENT)) {
            preparedStatement.setDate(1, subscription.getStartDay());
            preparedStatement.setInt(2, subscription.getDiscount());
            preparedStatement.setString(3, subscription.getTrainee());
            preparedStatement.setInt(4, subscription.getCourseEditionID());
            preparedStatement.setString(5, subscription.getCourseName());
            preparedStatement.setInt(6, subscription.getDuration());
            preparedStatement.execute();

        } finally {
            con.close();
        }

    }

}
