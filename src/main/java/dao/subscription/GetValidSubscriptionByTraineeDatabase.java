package dao.subscription;

import constants.Constants;
import resource.Person;
import resource.Subscription;
import resource.view.ValidSubscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class to get the valid subscriptions of a trainee
 *
 * @author Riccardo Tumiati
 */
public class GetValidSubscriptionByTraineeDatabase {
    private static final String statement = """
            SELECT subscription.coursename as coursename, name, surname, startday+ (duration || ' day')::interval as expiration
            FROM subscription JOIN teaches ON subscription.courseEditionId = teaches.courseEditionId and subscription.courseName = teaches.courseName
            JOIN person ON teaches.trainer = person.email
            WHERE trainee = ? and startday+ (duration || ' day')::interval>CURRENT_DATE
            """;

    private final Connection conn;

    private final String trainee_email;

    /**
     * Constructor for this class
     *
     * @param conn  the database connection
     * @param email the email address of a trainee
     */
    public GetValidSubscriptionByTraineeDatabase(final Connection conn, final String email) {
        this.conn = conn;
        this.trainee_email = email;
    }

    /**
     * Executes the sql statement retrieving the list of valid subscriptions of a trainee
     *
     * @return the list of valid subscriptions of a trainee
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<ValidSubscription> execute() throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<ValidSubscription> l_subscription = new ArrayList<>();

        try {
            stm = conn.prepareStatement(statement);
            stm.setString(1, trainee_email);
            rs = stm.executeQuery();

            while (rs.next()) {
                Date expiration = rs.getDate("expiration");
                String coursename = rs.getString(Constants.SUBSCRIPTION_COURSENAME);
                String name = rs.getString(Constants.PERSON_NAME);
                String surname = rs.getString(Constants.PERSON_SURNAME);

                Person p = new Person(
                        null,
                        name,
                        surname,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );

                Subscription s = new Subscription(
                        0,
                        coursename,
                        0,
                        null,
                        0,
                        null
                );

                ValidSubscription v = new ValidSubscription(s, p, expiration);
                l_subscription.add(v);
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            conn.close();
        }
        return l_subscription;
    }
}
