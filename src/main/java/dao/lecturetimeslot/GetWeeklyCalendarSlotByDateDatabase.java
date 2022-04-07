package dao.lecturetimeslot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;
import resource.Person;
import resource.view.WeeklyCalendarSlot;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class GetWeeklyCalendarSlotByDateDatabase {
    private static final String STATEMENT =
            "SELECT roomname, date, starttime, courseeditionid, coursename, substitution, " +
                    "tr.email as trainer_email, tr.name as trainer_name, tr.surname as trainer_surname, tr.psw as trainer_psw, " +
                    "tr.taxCode as trainer_taxcode, tr.birthDate as trainer_birthdate, tr.telephone as trainer_telephone, " +
                    "tr.address as trainer_address, tr.avatarPath as trainer_avatarpath, " +
                    "sub.email, sub.name, sub.surname, sub.psw, sub.taxCode, sub.birthDate, sub.telephone, sub.address, sub.avatarPath" +
                    " FROM lecturetimeslot NATURAL JOIN teaches AS t " +
                    "JOIN person AS tr ON t.trainer = tr.email " +
                    "LEFT JOIN person AS sub ON substitution = sub.email " +
                    "WHERE date >= ? AND date <= ? ORDER BY date,starttime ASC";

    private final Date date;
    private final Connection connection;
    private static final Logger logger = LogManager.getLogger("alberto_campeol_appender");

    public GetWeeklyCalendarSlotByDateDatabase(final Connection connection, final Date date) {
        this.connection = connection;
        this.date = date;
    }

    public List<WeeklyCalendarSlot> execute() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<WeeklyCalendarSlot> result = new ArrayList<>();
        Date from = Date.valueOf(LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)));
        Date to = Date.valueOf(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setDate(1, from);
            ps.setDate(2, to);

            rs = ps.executeQuery();
            logger.debug("[DEBUG] gwa.dao.GetWeeklyCalendarSlotByDateD - %s - query executed successfully, retrieving data...\n".formatted(
                    new Timestamp(System.currentTimeMillis())));
            while (rs.next()) {
                String roomName = rs.getString("roomname");
                Date date = rs.getDate("date");
                Time startTime = rs.getTime("starttime");
                int courseEditionId = rs.getInt("courseeditionid");
                String courseName = rs.getString("coursename");
                String substitution = rs.getString("substitution");
                LectureTimeSlot lts = new LectureTimeSlot(roomName, date, startTime, courseEditionId, courseName, substitution);
                Person trainer = new Person(
                        rs.getString("trainer_email"),
                        rs.getString("trainer_name"),
                        rs.getString("trainer_surname"),
                        rs.getString("trainer_psw"), //password
                        rs.getString("trainer_taxCode"),
                        rs.getDate("trainer_birthDate"),
                        rs.getString("trainer_telephone"),
                        rs.getString("trainer_address"),
                        rs.getString("trainer_avatarPath")
                );
                Person sub_trainer = null;
                if (substitution != null) {
                    sub_trainer = new Person(
                            rs.getString("email"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("psw"), //password
                            rs.getString("taxCode"),
                            rs.getDate("birthDate"),
                            rs.getString("telephone"),
                            rs.getString("address"),
                            rs.getString("avatarPath")
                    );
                }

                logger.debug("[DEBUG] gwa.dao.GetWeeklyCalendarSlotByDateD - %s - Retrieved: %s.\n".formatted(
                        new Timestamp(System.currentTimeMillis()), lts.toString()));
                result.add(new WeeklyCalendarSlot(lts, trainer, sub_trainer));
            }
        } catch (SQLException ex) {
            logger.error("[ERROR] gwa.dao.GetWeeklyCalendarSlotByDateD - %s - Exception:\n%s\n".
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
