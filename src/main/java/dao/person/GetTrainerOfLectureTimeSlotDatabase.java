package dao.person;


import constants.Constants;
import resource.LectureTimeSlot;
import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetTrainerOfLectureTimeSlotDatabase {

    private static final String STATEMENT = """
    SELECT person.* FROM teaches JOIN person ON person.email = teaches.trainer WHERE courseeditionid = ? AND coursename = ?
    """;
    private final Connection con;
    private final LectureTimeSlot lectureTimeSlot;

    public GetTrainerOfLectureTimeSlotDatabase(Connection con, LectureTimeSlot lectureTimeSlot) {
        this.con = con;
        this.lectureTimeSlot = lectureTimeSlot;
    }

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