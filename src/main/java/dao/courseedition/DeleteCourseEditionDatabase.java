package dao.courseedition;

import resource.CourseEdition;

import java.sql.*;

public class DeleteCourseEditionDatabase {
    private static final String STATEMENT = "DELETE FROM courseedition WHERE id=? and coursename=?";

    private final Connection conn;
    private final CourseEdition courseEdition;


    public DeleteCourseEditionDatabase(final Connection conn, CourseEdition courseEdition) {
        this.conn = conn;
        this.courseEdition = courseEdition;
    }

    public void execute() throws SQLException
    {
        try (PreparedStatement ps = conn.prepareStatement(STATEMENT, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(1, courseEdition.getId());
            ps.setString(2, courseEdition.getCourseName());
            ps.execute();
        } finally
        {
            conn.close();
        }
    }
}
