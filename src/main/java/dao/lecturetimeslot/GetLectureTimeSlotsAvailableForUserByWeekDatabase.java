package dao.lecturetimeslot;

import constants.Constants;
import resource.LectureTimeSlot;
import resource.rest.LectureTimeSlotOccupation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetLectureTimeSlotsAvailableForUserByWeekDatabase {
    private final String statement = "SELECT lts.roomname, lts.date, lts.starttime, lts.courseeditionid, lts.coursename, lts.substitution, ro.slots, r.reservations" +
            " FROM subscription AS s JOIN lecturetimeslot AS lts ON (s.courseeditionid = lts.courseeditionid AND s.coursename = lts.coursename)"+
            " JOIN room AS ro ON (lts.roomname = ro.name)"+
            " LEFT JOIN (SELECT lectureroom,lecturedate,lecturestarttime, count(*) AS reservations FROM reservation GROUP BY lectureroom,lecturedate,lecturestarttime) AS r ON (lts.roomname = r.lectureroom AND lts.date = r.lecturedate AND lts.starttime = r.lecturestarttime)"+
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

    public List<LectureTimeSlotOccupation> execute() throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<LectureTimeSlotOccupation> l_slots = new ArrayList<>();

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
                int total_slots = rs.getInt(Constants.ROOM_SLOTS);
                int occupied_slots = rs.getInt("reservations");

                LectureTimeSlot l = new LectureTimeSlot(
                        roomname,
                        date,
                        starttime,
                        courseeditionid,
                        coursename,
                        substitution
                );

                LectureTimeSlotOccupation lo = new LectureTimeSlotOccupation(l, total_slots, occupied_slots);

                l_slots.add(lo);
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            conn.close();
        }
        return l_slots;
    }
}
