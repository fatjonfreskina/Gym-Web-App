package dao.course;

import constants.Constants;
import resource.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gets a list of all the courses from the database
 *
 * @author Francesco Caldivezzi
 */
public class GetListCoursesDatabase {

    /**
     * Statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM course";

    /**
     * Connection to the database
     */
    private final Connection connection;

    /**
     * Parametric constructor
     *
     * @param connection connection to the database
     */
    public GetListCoursesDatabase(final Connection connection) {
        this.connection = connection;
    }

    /**
     * Gets a list of all the courses loaded into the database
     * @return list of courses
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<Course> execute() throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Course> result = new ArrayList<>();
        try {
            stm = connection.prepareStatement(STATEMENT);

            rs = stm.executeQuery();

            while (rs.next())
                result.add(new Course(rs.getString(Constants.COURSE_NAME), rs.getString(Constants.COURSE_DESCRIPTION)));

        } finally {
            if (stm != null)
                stm.close();
            if (rs != null)
                rs.close();
            connection.close();
        }
        return result;
    }

}
