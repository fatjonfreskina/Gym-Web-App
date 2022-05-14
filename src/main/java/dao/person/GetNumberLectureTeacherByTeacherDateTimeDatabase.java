package dao.person;

import resource.LectureTimeSlot;
import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This DAO is used to GET the lecture's number of a teacher for a particular course
 *
 * @author Francesco Caldivezzi
 */
public class GetNumberLectureTeacherByTeacherDateTimeDatabase {

    private static final String STATEMENT = """
            select count(*) as numberlectures
                         from teaches,person,lecturetimeslot
                         where
                         teaches.trainer = person.email and
                         lecturetimeslot.courseeditionid = teaches.courseeditionid and
                         lecturetimeslot.coursename = teaches.coursename and
                         email = ? and date = ? and starttime = ?                        
            """;

    /**
     * The connection to the database
     */
    private final Connection connection;

    /**
     * The instructor to be passed to the query
     */
    private final Person teacher;

    /**
     * The lecture time slot to be passed to the query
     */
    private final LectureTimeSlot lectureTimeSlot;

    /**
     * Parametric constructor
     *
     * @param connection      the connection to the database
     * @param teacher         the teacher as a Person object to be passed
     * @param lectureTimeSlot the LectureTimeSlot
     */
    public GetNumberLectureTeacherByTeacherDateTimeDatabase(final Connection connection, final Person teacher, final LectureTimeSlot lectureTimeSlot) {
        this.connection = connection;
        this.teacher = teacher;
        this.lectureTimeSlot = lectureTimeSlot;
    }

    /**
     * Executes the SELECT
     *
     * @return an Integer containing the number of lectures that matched the query
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public Integer execute() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Integer ret = 0;

        try {
            pstmt = connection.prepareStatement(STATEMENT);
            pstmt.setString(1, teacher.getEmail());
            pstmt.setDate(2, lectureTimeSlot.getDate());
            pstmt.setTime(3, lectureTimeSlot.getStartTime());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("numberlectures");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            connection.close();
        }
        return ret;
    }

}
