package dao.person;

import constants.Constants;
import resource.LectureTimeSlot;
import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private final Connection connection;
    private final Person teacher;
    private final LectureTimeSlot lectureTimeSlot;



    public GetNumberLectureTeacherByTeacherDateTimeDatabase(final Connection connection, final Person teacher,final  LectureTimeSlot lectureTimeSlot) {
        this.connection = connection;
        this.teacher = teacher;
        this.lectureTimeSlot = lectureTimeSlot;
    }

    public Integer execute() throws SQLException
    {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Integer ret = 0;

        try {
            pstmt = connection.prepareStatement(STATEMENT);
            pstmt.setString(1, teacher.getEmail());
            pstmt.setDate(2, lectureTimeSlot.getDate());
            pstmt.setTime(3,lectureTimeSlot.getStartTime());
            rs = pstmt.executeQuery();

            if (rs.next())
            {
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
