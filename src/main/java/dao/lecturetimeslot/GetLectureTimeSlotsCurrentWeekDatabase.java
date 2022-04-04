package dao.lecturetimeslot;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;

public class GetLectureTimeSlotsCurrentWeekDatabase {
    private static final String STATEMENT = "SELECT * FROM lecturetimeslot WHERE date >= ? AND date <= ?";

    private final Connection connection;
    private static final Logger logger = LogManager.getLogger("harjot_singh_appender");

    public GetLectureTimeSlotsCurrentWeekDatabase(final Connection connection) {
        this.connection = connection;
    }

    public List<LectureTimeSlot> execute() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<LectureTimeSlot> result = new ArrayList<>();
        Date from = Date.valueOf(LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)));//.toString();
        Date to = Date.valueOf(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));//.toString();
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setDate(1, from);
            ps.setDate(2, to);

            rs = ps.executeQuery();
            logger.debug("[DEBUG] gwa.dao.GetLTSCWeekD - %s - query executed successfully, retrieving data...\n".formatted(new Timestamp(System.currentTimeMillis())));
            while (rs.next()) {
                String roomName = rs.getString("roomname");
                Date date = rs.getDate("date");
                Time startTime = rs.getTime("starttime");
                int courseEditionId = rs.getInt("courseeditionid");
                String courseName = rs.getString("coursename");
                String substitution = rs.getString("substitution");
                LectureTimeSlot lts = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);

                logger.debug("[DEBUG] gwa.dao.GetLTSCWeekD - %s - Retrieved: %s.\n".formatted(new Timestamp(System.currentTimeMillis()), lts.toString()));
                result.add(lts);
            }
        } catch (SQLException ex) {
            logger.error("[ERROR] gwa.dao.GetLTSCWeekD - %s - Exception:\n%s\n".formatted(new Timestamp(System.currentTimeMillis()), ex.getMessage()));
            throw ex;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            connection.close();
        }
        return result;
    }
}
