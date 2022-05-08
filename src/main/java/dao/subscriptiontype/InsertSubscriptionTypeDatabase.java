package dao.subscriptiontype;

import resource.SubscriptionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DAO class that can be used to insert a type of subscription into the
 * database that refers to a course and its edition. The type of subscription
 * must also have an associated duration and a cost.
 *
 * @author Andrea Pasin
 */
public class InsertSubscriptionTypeDatabase {
    private static final String STATEMENT = "INSERT INTO subscriptiontype (courseeditionid, coursename, duration, cost) VALUES (?, ?, ?, ?)";
    private final Connection con;

    private final SubscriptionType subscriptionType;

    /**
     * Constructor for the InsertSubscriptionTypeDatabase class
     *
     * @param con              the connection to the database
     * @param subscriptionType the type of subscription to be added into the database
     */
    public InsertSubscriptionTypeDatabase(final Connection con, final SubscriptionType subscriptionType) {
        this.con = con;
        this.subscriptionType = subscriptionType;
    }

    /**
     * Execute an Insert query to insert the provided SubscriptionType into the subscriptiontype table
     *
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public void execute() throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(STATEMENT)) {
            preparedStatement.setInt(1, subscriptionType.getCourseEditionID());
            preparedStatement.setString(2, subscriptionType.getCourseName());
            preparedStatement.setInt(3, subscriptionType.getDuration());
            preparedStatement.setFloat(4, subscriptionType.getCost());

            preparedStatement.execute();
        } finally {
            con.close();
        }

    }
}
