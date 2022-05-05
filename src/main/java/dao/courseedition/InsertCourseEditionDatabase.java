package dao.courseedition;

import constants.Constants;
import resource.CourseEdition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Inserts a new course edition in the database
 *
 * @author Francesco Caldivezzi
 */
public class InsertCourseEditionDatabase {

    /**
     * SQL statement to be executed
     */
    private static final String STATEMENT = "INSERT INTO courseedition values(DEFAULT,?) returning *";

    /**
     * Connection to the database
     */
    private final Connection conn;

    /**
     * CourseEdition object to add
     */
    private final CourseEdition courseEdition;

    /**
     * Parametric constructor
     * @param conn          connection to the database
     * @param courseEdition object to add to the database
     */
    public InsertCourseEditionDatabase(final Connection conn, CourseEdition courseEdition) {
        this.conn = conn;
        this.courseEdition = courseEdition;
    }

    /**
     * Executes the insert operation in the database for the specified object
     *
     * @return id ot the inserted course edition id
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public Integer execute() throws SQLException {
        Integer key = null;
        try (PreparedStatement ps = conn.prepareStatement(STATEMENT)) {
            ps.setString(1, courseEdition.getCourseName());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                key = resultSet.getInt(Constants.COURSEEDITION_ID);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            conn.close();
        }
        return key;
    }

}
