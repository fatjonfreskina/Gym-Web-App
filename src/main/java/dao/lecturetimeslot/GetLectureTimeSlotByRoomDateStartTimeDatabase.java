package dao.lecturetimeslot;

import constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Francesco Caldivezzi
 * */

public class GetLectureTimeSlotByRoomDateStartTimeDatabase
{
    private static final String STATEMENT = "SELECT * FROM lecturetimeslot WHERE roomname = ? and date = ? and starttime=?";
    private final Connection connection;
    private final LectureTimeSlot lectureTimeSlot;


    public GetLectureTimeSlotByRoomDateStartTimeDatabase(final Connection connection, final LectureTimeSlot lectureTimeSlot) {
        this.connection = connection;
        this.lectureTimeSlot = lectureTimeSlot;
    }

    public LectureTimeSlot execute() throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        LectureTimeSlot result = null;
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1, lectureTimeSlot.getRoomName());
            ps.setDate(2, lectureTimeSlot.getDate());
            ps.setTime(3, lectureTimeSlot.getStartTime());

            rs = ps.executeQuery();


            if (rs.next()) {
                String roomName = rs.getString(Constants.LECTURETIMESLOT_ROOMNAME);
                Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
                Time startTime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
                int courseEditionId = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
                String courseName = rs.getString(Constants.LECTURETIMESLOT_COURSENAME);
                String substitution = rs.getString(Constants.LECTURETIMESLOT_SUBSTITUTION);
                result = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);
            }
        }  finally
        {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            connection.close();
        }
        return result;
    }


}
