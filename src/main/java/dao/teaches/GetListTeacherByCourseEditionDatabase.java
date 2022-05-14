package dao.teaches;

import constants.Constants;
import resource.CourseEdition;
import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class used to get the list of teacher by a given course edition
 *
 * @author Andrea Pasin
 */
public class GetListTeacherByCourseEditionDatabase {

    private static final String STATEMENT = "SELECT * FROM teaches WHERE courseeditionid = ? and coursename= ?";
    private final Connection con;
    private final CourseEdition courseEdition;

    /**
     * Constructor for this class
     *
     * @param con           the database connection
     * @param courseEdition the course edition
     */
    public GetListTeacherByCourseEditionDatabase(final Connection con, final CourseEdition courseEdition) {
        this.con = con;
        this.courseEdition = courseEdition;
    }

    /**
     * Executes the sql statement retrieving the list of trainers for the given course edition
     *
     * @return the list of trainers for the given course edition
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<Person> execute() throws SQLException {
        List<Person> result = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = con.prepareStatement(STATEMENT);
            stm.setInt(1, courseEdition.getId());
            stm.setString(2, courseEdition.getCourseName());

            rs = stm.executeQuery();
            while (rs.next())
                result.add(new Person(rs.getString(Constants.TEACHES_TRAINER)));
        } finally {
            if (stm != null)
                stm.close();
            if (rs != null)
                rs.close();
            con.close();
        }
        return result;
    }
}
