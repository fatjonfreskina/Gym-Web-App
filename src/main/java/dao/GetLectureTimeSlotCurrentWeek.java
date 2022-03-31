package dao;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

public class GetLectureTimeSlotCurrentWeek {
    private static final String STATEMENT = "SELECT * FROM lecturetimeslot WHERE date >= ? AND date <= ?";

    private final Connection connection;
    private static final Logger logger = LogManager.getLogger("harjot_singh_appender");

    public GetLectureTimeSlotCurrentWeek(final Connection connection){
        this.connection = connection;
    }

    public List<LectureTimeSlot> getLectureTimeSlotCurrentWeek() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<LectureTimeSlot> result = new ArrayList<>();
        String from = LocalDate.now().with(TemporalAdjusters.previous( DayOfWeek.MONDAY )).toString();
        String to = LocalDate.now().with(TemporalAdjusters.next( DayOfWeek.SUNDAY )).toString();
        try{
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1,from);
            ps.setString(2,to);

            rs = ps.executeQuery();
            logger.debug("[DEBUG] GetLectureTimeSlotCurrentWeek - %s - query executed successfully.\n".formatted(
                new Timestamp(System.currentTimeMillis())));
            while (rs.next()) {
                String roomName = rs.getString("roomname");
                Date date = rs.getDate("date");
                Time startTime = rs.getTime("starttime");
                int courseEditionId = rs.getInt("courseeditionid");
                String courseName = rs.getString("coursename");
                String substitution = rs.getString("substitution");

                logger.debug("[DEBUG] GetLectureTimeSlotCurrentWeek - %s - %s.\n".formatted(
                    new Timestamp(System.currentTimeMillis()),roomName));
                result.add(new LectureTimeSlot(roomName,date,startTime,courseEditionId,courseName,substitution));
            }
        } catch (SQLException ex){
            logger.error("[ERROR] GetLectureTimeSlotCurrentWeek - %s - Exception:\n%s\n".
                formatted(new Timestamp(System.currentTimeMillis()), ex.getMessage()));
            throw ex;
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            connection.close();
        }
        return result;
    }
}
