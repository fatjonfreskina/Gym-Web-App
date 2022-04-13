package dao.subscription;

import constants.Constants;
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
public class GetValidSubscriptionByCourseAndTraineeDatabase {
    private static final String STATEMENT = "SELECT * FROM subscription WHERE courseeditionid = ? and startday+ (duration || ' day')::interval>CURRENT_DATE and trainee=? and coursename=?";
    private final Connection connection;
    private final CourseEdition course;
    private final Person person;

    public GetValidSubscriptionByCourseAndTraineeDatabase(final Connection connection, final CourseEdition course, final Person person) {
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
            ps.setInt(1, course.getId());
            ps.setString(2, person.getEmail());
            ps.setString(3,course.getCourseName());
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
