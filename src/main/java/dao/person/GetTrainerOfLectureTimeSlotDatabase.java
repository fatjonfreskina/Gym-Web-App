package dao.person;


import constants.Constants;
import resource.LectureTimeSlot;
import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Retireves the trainer for a given lecture time slot
 *
 * @author Francesco Caldivezzi
 */
public class GetTrainerOfLectureTimeSlotDatabase {

    /**
     * Query to be executed
     */
    private static final String STATEMENT = """
            SELECT person.* FROM teaches JOIN person ON person.email = teaches.trainer WHERE courseeditionid = ? AND coursename = ?
            """;

    /**
     * Database connection
     */
    private final Connection con;

    /**
     * LectureTimeSlot to get the trainer
     */
    private final LectureTimeSlot lectureTimeSlot;

    /**
     * Parametric constructor
     * @param con database connection
     * @param lectureTimeSlot LectureTimeSlot to get the trainer
     */
    public GetTrainerOfLectureTimeSlotDatabase(Connection con, LectureTimeSlot lectureTimeSlot) {
        this.con = con;
        this.lectureTimeSlot = lectureTimeSlot;
    }

    /**
     * Executes the query
     *
     * @return people with role trainer associated to the given lecture time slot
     * @throws SQLException thrown if something goes wrong while querying the database
     */
    public Person execute() throws SQLException {
        Person person = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, lectureTimeSlot.getCourseEditionId());
            preparedStatement.setString(2, lectureTimeSlot.getCourseName());
            //Execute the query
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString(Constants.PERSON_EMAIL), rs.getString(Constants.PERSON_NAME), rs.getString(Constants.PERSON_SURNAME),
                        rs.getString(Constants.PERSON_PSW), rs.getString(Constants.PERSON_TAXCODE), rs.getDate(Constants.PERSON_BIRTHDATE),
                        rs.getString(Constants.PERSON_TELEPHONE), rs.getString(Constants.PERSON_ADDRESS), rs.getString(Constants.PERSON_AVATARPATH));
            }
        } finally {
            if (rs != null)
                rs.close();
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }

        return person;
    }
}