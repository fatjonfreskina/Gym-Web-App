package dao.subscription;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import constants.Constants;
import resource.CourseEdition;
import resource.Subscription;

/**
 * DAO class used to get the subscriptions associated to a course
 * @author Harjot Singh
 */
public class GetSubscriptionsByCourseDatabase {

  private static final String STATEMENT = "SELECT * FROM subscription WHERE courseEditionId = ? and courseName = ?";
  private final Connection connection;
  private final CourseEdition course;
  private static final Logger logger = LogManager.getLogger("harjot_singh_logger");
  private final String loggerClass = "gwa.dao.subscription.GetSByCourseD: ";

  /**
   * Constructor for this class
   * @param connection  the database connection
   * @param course  the course
   */
  public GetSubscriptionsByCourseDatabase(final Connection connection, final CourseEdition course) {
    this.connection = connection;
    this.course = course;
  }

  /**
   * Executes the sql statement retrieving the list of subscriptions associated to a given course
   * @return  the list of subscriptions associated to a given course
   * @throws SQLException
   */
  public List<Subscription> execute() throws SQLException {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Subscription> result = new ArrayList<>();
    try {
      ps = connection.prepareStatement(STATEMENT);
      ps.setInt(1, course.getId());
      ps.setString(2, course.getCourseName());

      rs = ps.executeQuery();

      logger.debug(loggerClass + "query executed successfully, retrieving data...");
      while (rs.next()) {
        int courseEditionId = rs.getInt(Constants.SUBSCRIPTION_COURSEEDITIONID);
        String courseName = rs.getString(Constants.SUBSCRIPTION_COURSENAME);
        int duration = rs.getInt(Constants.SUBSCRIPTION_DURATION);
        Date startDay = rs.getDate(Constants.SUBSCRIPTION_STARTDAY);
        String traineeEmail = rs.getString(Constants.SUBSCRIPTION_TRAINEE);
        int discount = rs.getInt(Constants.SUBSCRIPTION_DISCOUNT);

        Subscription s = new Subscription(courseEditionId, courseName, duration, startDay, discount, traineeEmail);
        logger.trace(loggerClass + "Retrieved " + s);
        result.add(s);
      }
    } catch (SQLException ex) {
      logger.error("gwa.dao.GetLTSByCourseD: " + ex.getMessage());
      throw ex;
    } finally {
      if (rs != null) rs.close();
      if (ps != null) ps.close();
      connection.close();
    }
    return result;
  }
}
