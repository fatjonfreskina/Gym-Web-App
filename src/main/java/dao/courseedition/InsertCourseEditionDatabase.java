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

    private static final String STATEMENT = "INSERT INTO courseedition values(DEFAULT,?) returning *";

    private final Connection conn;
    private final CourseEdition courseEdition;


    public InsertCourseEditionDatabase(final Connection conn, CourseEdition courseEdition) {
        this.conn = conn;
        this.courseEdition = courseEdition;
    }

    public Integer execute() throws SQLException
    {
        Integer key = null;
        try (PreparedStatement ps = conn.prepareStatement(STATEMENT))
        {

            ps.setString(1, courseEdition.getCourseName());
            ResultSet resultSet =  ps.executeQuery();
            if(resultSet.next())
                key = resultSet.getInt(Constants.COURSEEDITION_ID);

            //ps.execute();
        }catch (Exception e)
        {
            System.out.println(e);

        }

        finally
        {
            conn.close();
        }
        return key;
    }

}
