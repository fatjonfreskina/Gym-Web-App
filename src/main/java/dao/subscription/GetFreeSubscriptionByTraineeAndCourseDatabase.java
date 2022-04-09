package dao.subscription;

import constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.Course;
import resource.CourseEdition;
import resource.Person;
import resource.Subscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Pasin
 */
public class GetFreeSubscriptionByTraineeAndCourseDatabase {
    private static final String STATEMENT = "SELECT * FROM subscription WHERE courseName = ? and duration=7 and trainee=?";
    private final Connection connection;
    private final Course course;
    private final Person person;

    public GetFreeSubscriptionByTraineeAndCourseDatabase(final Connection connection, final Course course, final Person person) {
        this.connection = connection;
        this.course = course;
        this.person=person;
    }

    public List<Subscription> execute() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Subscription> result = new ArrayList<>();
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1, course.getName());
            ps.setString(2, person.getEmail());

            rs = ps.executeQuery();

            while (rs.next()) {
                int courseEditionId = rs.getInt(Constants.SUBSCRIPTION_COURSEEDITIONID);
                String courseName = rs.getString(Constants.SUBSCRIPTION_COURSENAME);
                int duration = rs.getInt(Constants.SUBSCRIPTION_DURATION);
                Date startDay = rs.getDate(Constants.SUBSCRIPTION_STARTDAY);
                String traineeEmail = rs.getString(Constants.SUBSCRIPTION_TRAINEE);
                int discount = rs.getInt(Constants.SUBSCRIPTION_DISCOUNT);

                Subscription s = new Subscription(courseEditionId, courseName, duration, startDay, discount, traineeEmail);
                result.add(s);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            connection.close();
        }
        return result;
    }
}
