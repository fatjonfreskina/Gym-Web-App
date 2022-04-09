package dao.courseedition;

import resource.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Riccardo Tumiati => sistema non funziona!!!
 */
public class GetAvailableCourses {
    private final String statement =
            "SELECT DISTINCT courseedition.coursename AS cname,description FROM courseedition " +
                    "INNER JOIN course ON courseedition.coursename = course.name " +
                    "INNER JOIN lecturetimeslot ON lecturetimeslot.courseeditionid=courseedition.id AND courseedition.coursename=lecturetimeslot.coursename " +
                    "WHERE date >=?";
    private final Connection con;
    private final Date today;

    public GetAvailableCourses(final Connection con) {
        this.con = con;
        this.today = new Date(System.currentTimeMillis());
    }

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
