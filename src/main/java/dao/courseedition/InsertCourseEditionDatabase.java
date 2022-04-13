package dao.courseedition;

import constants.Constants;
import resource.CourseEdition;
import resource.Person;

import java.sql.*;

/**
 * @author Francesco Caldivezzi
 *
 * */
public class InsertCourseEditionDatabase {

    private static final String STATEMENT = "INSERT INTO courseedition values(DEFAULT,?)";

    private final Connection conn;
    private final CourseEdition courseEdition;


    public InsertCourseEditionDatabase(final Connection conn, CourseEdition courseEdition) {
        this.conn = conn;
        this.courseEdition = courseEdition;
    }

    public Integer execute() throws SQLException
    {
        Integer key = null;
        try (PreparedStatement ps = conn.prepareStatement(STATEMENT, Statement.RETURN_GENERATED_KEYS))
        {

            ps.setString(1, courseEdition.getCourseName());
            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if(resultSet.next())
                key = resultSet.getInt(1);

            //ps.execute();
        } finally
        {
            conn.close();
        }
        return key;
    }

}
