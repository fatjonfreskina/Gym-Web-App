package dao;

import resource.LectureTimeSlot;
import resource.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * DAO class that can be used to check before a user try to book a lecturetimeslot if there are available slots for booking it
 *
 * @author Francesco Caldivezzi
 */
public class GetAvailableSlotsRoomByLectureTimeSlotDatabase {

    private static final String STATEMENT = "SELECT slots - count AS availableslots FROM " +
            "(SELECT slots FROM room where name=?) AS t1, " +
            "(SELECT count(*) AS count FROM reservation WHERE lectureroom=? and lecturedate=? and lecturestarttime=?) AS t2;";

    private final Connection con;
    private final Room room;
    private final LectureTimeSlot lectureTimeSlot;

    /**
     * Constructor for the GetAvailableSlotsRoomByLectureTimeSlotDatabase class
     * @param con  the connection to the database
     * @param room  the room in which the lecturetimeslot will occur
     * @param lectureTimeSlot the lecturetimeslot that the user want to book
     */
    public GetAvailableSlotsRoomByLectureTimeSlotDatabase(final Connection con, final Room room, final LectureTimeSlot lectureTimeSlot)
    {
        this.con = con;
        this.room = room;
        this.lectureTimeSlot = lectureTimeSlot;
    }

    /**
     * Execute an the query and provide the number of available slots
     * @return the number of available slots
     */
    public int getAvailableSlotsRoomByLectureTimeSlots() throws SQLException
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int availableSlots = 0;
        try
        {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, room.getName());
            pstmt.setString(2, room.getName());
            pstmt.setDate(3, lectureTimeSlot.getDate());
            pstmt.setTime(4, lectureTimeSlot.getStartTime());

            rs = pstmt.executeQuery();
            if(rs.next())
                availableSlots = rs.getInt("availableslots");
        } finally
        {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            con.close();
        }
        return availableSlots;
    }

}
