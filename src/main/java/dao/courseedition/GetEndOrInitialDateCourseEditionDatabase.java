package dao.courseedition;

import resource.CourseEdition;

import java.sql.*;

/**
 * Get starting date and/or ending date of a given course
 *
 * @author Francesco Caldivezzi
 */
public class GetEndOrInitialDateCourseEditionDatabase {

    /**
     * SQL statement to retrieve the end date of a given course
     */
    private static final String STATEMENT_MAX = """
            select max(date) as date
            from lecturetimeslot natural join courseedition
            where coursename = ? and courseeditionid = ?
            """;

    /**
     * SQL statement to retrieve the end starting date of a given course
     */
    private static final String STATEMENT_MIN = """
            select min(date) as date
            from lecturetimeslot natural join courseedition
            where coursename = ? and courseeditionid = ?
            """;

    /**
     * Connection to the database
     */
    private final Connection con;

    /**
     * CourseEdition object for which end date and start date will be retrieved
     */
    private final CourseEdition courseEdition;

    /**
     * Parametric constructor
     *
     * @param con           database connection
     * @param courseEdition object for which information are retrieved
     */
    public GetEndOrInitialDateCourseEditionDatabase(final Connection con, final CourseEdition courseEdition) {
        this.con = con;
        this.courseEdition = courseEdition;
    }

    /**
     * Gets the last lecture of a given course
     *
     * @return Date of the last lecture of a given course
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public Date executeMax() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Date ret = null;
        try {
            preparedStatement = con.prepareStatement(STATEMENT_MAX);
            preparedStatement.setString(1, courseEdition.getCourseName());
            preparedStatement.setInt(2, courseEdition.getId());

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ret = resultSet.getDate("date");
            }

        } finally {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }
        return ret;
    }

    /**
     * Gets the first lecture of a given course
     *
     * @return Date of the first lecture of a given course
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public Date executeMin() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Date ret = null;
        try {
            preparedStatement = con.prepareStatement(STATEMENT_MIN);
            preparedStatement.setString(1, courseEdition.getCourseName());
            preparedStatement.setInt(2, courseEdition.getId());

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ret = resultSet.getDate("date");
            }

        } finally {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }
        return ret;
    }

}
