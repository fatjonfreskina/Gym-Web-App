package dao.courseedition;

import resource.CourseEdition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Deletes a course edition from the database
 *
 * @author Andrea Pasin
 */
public class DeleteCourseEditionDatabase {

    /**
     * SQL statement to be executed
     */
    private static final String STATEMENT = "DELETE FROM courseedition WHERE id=? and coursename=?";

    /**
     * Connection to the database
     */
    private final Connection conn;

    /**
     * CourseEdition object to be deleted
     */
    private final CourseEdition courseEdition;

    /**
     * Parametric constructor
     *
     * @param conn          connection to the database
     * @param courseEdition object to be deleted
     */
    public DeleteCourseEditionDatabase(final Connection conn, CourseEdition courseEdition) {
        this.conn = conn;
        this.courseEdition = courseEdition;
    }

    /**
     * Executes the delete operation in the database for the specified object
     *
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public void execute() throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(STATEMENT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, courseEdition.getId());
            ps.setString(2, courseEdition.getCourseName());
            ps.execute();
        } finally {
            conn.close();
        }
    }
}
