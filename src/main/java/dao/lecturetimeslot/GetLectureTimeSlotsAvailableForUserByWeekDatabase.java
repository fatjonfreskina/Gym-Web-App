package dao.lecturetimeslot;

import constants.Constants;
import resource.LectureTimeSlot;
import resource.rest.LectureTimeSlotOccupation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This DAO is used to get available lecture time slots in a week for a user
 *
 * @author Riccardo Tumiati
 */
public class GetLectureTimeSlotsAvailableForUserByWeekDatabase {

    /**
     * The SELECT query to be executed
     */
    private final String statement = "SELECT lts.roomname, lts.date, lts.starttime, lts.courseeditionid, lts.coursename, lts.substitution, ro.slots, r.reservations" +
            " FROM subscription AS s JOIN lecturetimeslot AS lts ON (s.courseeditionid = lts.courseeditionid AND s.coursename = lts.coursename)" +
            " JOIN room AS ro ON (lts.roomname = ro.name)" +
            " LEFT JOIN (SELECT lectureroom,lecturedate,lecturestarttime, count(*) AS reservations FROM reservation GROUP BY lectureroom,lecturedate,lecturestarttime) AS r ON (lts.roomname = r.lectureroom AND lts.date = r.lecturedate AND lts.starttime = r.lecturestarttime)" +
            " WHERE s.trainee = ? AND s.startday+ (s.duration || ' day')::interval>CURRENT_DATE AND lts.date>= ? AND lts.date<= ?";

    /**
     * The connection to the database
     */
    private final Connection conn;

    /**
     * The email of the user
     */
    private final String email;

    /**
     * Date from which to look for available lecture time slot
     */
    private final Date fromDate;

    /**
     * Date up to look for available lecture time slot
     */
    private final Date toDate;

    /**
     * Parametric constructor
     *
     * @param conn     the connection to the database
     * @param email    the email of the user
     * @param fromDate Date from which to look for
     * @param toDate   Date up to look for
     */
    public GetLectureTimeSlotsAvailableForUserByWeekDatabase(Connection conn, String email, Date fromDate, Date toDate) {
        this.conn = conn;
        this.email = email;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    /**
     * Execute the query
     *
     * @return a list containing LectureTimeSlotOccupation object that matched the query
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<LectureTimeSlotOccupation> execute() throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<LectureTimeSlotOccupation> l_slots = new ArrayList<>();

        try {
            stm = conn.prepareStatement(statement);
            stm.setString(1, email);
            stm.setDate(2, fromDate);
            stm.setDate(3, toDate);
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
