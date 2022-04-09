package dao.course;

import constants.Constants;
import resource.Course;
import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Francesco Caldivezzi
 * */
public class GetListCoursesDatabase
{

    public class GetListOfTeachersDatabase
    {
        private static final String STATEMENT = "SELECT * FROM course";

        private final Connection connection;

        public GetListOfTeachersDatabase(final Connection connection)
        {
            this.connection = connection;
        }

        public List<Course> execute() throws SQLException
        {
            PreparedStatement stm = null;
            ResultSet rs = null;
            List<Course> result = new ArrayList<>();
            try {
                stm = connection.prepareStatement(STATEMENT);

                rs = stm.executeQuery();

                while (rs.next())
                    result.add(new Course(rs.getString(Constants.COURSE_NAME),rs.getString(Constants.COURSE_DESCRIPTION)));

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
}
