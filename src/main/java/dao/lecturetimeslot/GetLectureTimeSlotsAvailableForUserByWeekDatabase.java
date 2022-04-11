package dao.lecturetimeslot;

import constants.Constants;
import resource.LectureTimeSlot;
import resource.Person;
import resource.Subscription;
import resource.view.ValidSubscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetLectureTimeSlotsAvailableForUserByWeekDatabase {
    private final String statement = "SELECT lts.roomname, lts.date, lts.starttime, lts.courseeditionid, lts.coursename, lts.substitution" +
            " FROM subscription AS s JOIN lecturetimeslot AS lts ON (s.courseeditionid = lts.courseeditionid AND s.coursename = lts.coursename)"+
            " WHERE s.trainee = ? AND s.startday+ (s.duration || ' day')::interval>CURRENT_DATE AND lts.date>= ? AND lts.date<= ?";

    private final Connection conn;
    private final String email;
    private final Date fromDate;
    private final Date toDate;

    public GetLectureTimeSlotsAvailableForUserByWeekDatabase(Connection conn, String email, Date fromDate, Date toDate){
        this.conn = conn;
        this.email = email;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public List<LectureTimeSlot> execute() throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<LectureTimeSlot> l_slots = new ArrayList<>();

        try {
            stm = conn.prepareStatement(statement);
            stm.setString(1, email);
            stm.setDate(2,fromDate);
            stm.setDate(3,toDate);
            rs = stm.executeQuery();

            while (rs.next()) {
                String roomname = rs.getString(Constants.LECTURETIMESLOT_ROOMNAME);
                Date date = rs.getDate(Constants.LECTURETIMESLOT_DATE);
                Time starttime = rs.getTime(Constants.LECTURETIMESLOT_STARTTIME);
                int courseeditionid = rs.getInt(Constants.LECTURETIMESLOT_COURSEEDITIONID);
                String coursename = rs.getString(Constants.LECTURETIMESLOT_COURSENAME);
                String substitution = rs.getString(Constants.LECTURETIMESLOT_SUBSTITUTION);

                LectureTimeSlot l = new LectureTimeSlot(
                        roomname,
                        date,
                        starttime,
                        courseeditionid,
                        coursename,
                        substitution
                );

                l_slots.add(l);
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            conn.close();
        }
        return l_slots;
    }
}
