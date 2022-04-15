package dao.courseedition;

import resource.CourseEdition;
import resource.view.PricesView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Francesco Caldivezzi
 * */
public class GetEndOrInitialDateCourseEditionDatabase {

    private static final String STATEMENT_MAX = """
            select max(date) as date
            from lecturetimeslot natural join courseedition
            where coursename = ? and courseeditionid = ?
            """;

    private static final String STATEMENT_MIN = """
            select min(date) as date
            from lecturetimeslot natural join courseedition
            where coursename = ? and courseeditionid = ?
            """;

    private final Connection con;
    private final CourseEdition courseEdition;


    public GetEndOrInitialDateCourseEditionDatabase(final Connection con, final CourseEdition courseEdition) {
        this.con = con;
        this.courseEdition = courseEdition;
    }

    public Date executeMax() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Date ret = null;
        try {
            preparedStatement = con.prepareStatement(STATEMENT_MAX);
            preparedStatement.setString(1,courseEdition.getCourseName());
            preparedStatement.setInt(2,courseEdition.getId());

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
               ret = resultSet.getDate("date");
            }

        }
        finally{
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }
        return ret;
    }

    public Date executeMin() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Date ret = null;
        try {
            preparedStatement = con.prepareStatement(STATEMENT_MIN);
            preparedStatement.setString(1,courseEdition.getCourseName());
            preparedStatement.setInt(2,courseEdition.getId());

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ret = resultSet.getDate("date");
            }

        }
        finally{
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }
        return ret;
    }

}
