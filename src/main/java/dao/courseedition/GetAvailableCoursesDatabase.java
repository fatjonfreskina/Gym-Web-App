package dao.courseedition;

import resource.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gets all the available course from the database for a given date
 *
 * @author Riccardo Tumiati
 */
public class GetAvailableCoursesDatabase {

    /**
     * SQL statement to be executed
     */
    private final String statement =
            "SELECT DISTINCT courseedition.coursename AS cname,description FROM courseedition " +
                    "INNER JOIN course ON courseedition.coursename = course.name " +
                    "INNER JOIN lecturetimeslot ON lecturetimeslot.courseeditionid=courseedition.id AND courseedition.coursename=lecturetimeslot.coursename " +
                    "WHERE date >=?";

    /**
     * Connection to the database
     */
    private final Connection con;

    /**
     * Date used in the query
     */
    private final Date today;

    /**
     * Parametric constructor
     *
     * @param con connection to the database
     */
    public GetAvailableCoursesDatabase(final Connection con) {
        this.con = con;
        this.today = new Date(System.currentTimeMillis());
    }

    /**
     * Returns a list of courses for the given date
     *
     * @return list of courses
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<Course> execute() throws SQLException {
        PreparedStatement prstm = null;
        ResultSet res = null;
        List<Course> output = new ArrayList<>();

        try {
            prstm = con.prepareStatement(statement);
            prstm.setDate(1, today);

            res = prstm.executeQuery();
            while (res.next()) {
                output.add(new Course(
                        res.getString("cname"),
                        res.getString("description")
                ));
            }
        } finally {
            if (res != null)
                res.close();
            if (prstm != null)
                prstm.close();
            con.close();
        }

        return output;
    }
}
