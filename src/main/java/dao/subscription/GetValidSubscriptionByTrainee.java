package dao.subscription;

import constants.Constants;
import resource.Person;
import resource.Teaches;
import resource.view.Trainer;
import resource.view.ValidSubscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
    @author Tumiati Riccardo
 */
public class GetValidSubscriptionByTrainee {
    private static final String statement = "SELECT subscription.coursename, name, surname, startday+ (duration || ' day')::interval as Expiration" +
            "FROM subscription JOIN teaches ON subscription.courseEditionId = teaches.courseEditionId and subscription.courseName = teaches.courseName"+
            "JOIN person ON teaches.trainer = person.email"+
            "WHERE trainee = ? and startday+ (duration || ' day')::interval>CURRENT_DATE";
    private final Connection conn;
    private final String trainee_email;

    public GetValidSubscriptionByTrainee(final Connection conn, final String email) {
        this.conn = conn;
        this.trainee_email = email;
    }

    public List<ValidSubscription> execute() throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<ValidSubscription> l_subscription = new ArrayList<>();

        //TODO
        return null;
    }
}
