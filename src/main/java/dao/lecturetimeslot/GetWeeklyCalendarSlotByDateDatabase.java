package dao.lecturetimeslot;

import resource.LectureTimeSlot;
import resource.Person;
import resource.view.WeeklyCalendarSlot;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * This DAO is used to get the weekly slots of a course by passing the date
 *
 * @author //TODO : Missing Author, check if the description is ok
 * */
public class GetWeeklyCalendarSlotByDateDatabase {

    /**
     * The SELECT query to be executed
     */
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

    /**
     * The date to be passed to the query
     */
    private final Date date;

    /**
     * The connection to the database
     */
    private final Connection connection;

    /**
     *
     * Parametric constructor
     *
     * @param connection the connection to the database
     * @param date the date to be passed to the query
     */
    public GetWeeklyCalendarSlotByDateDatabase(final Connection connection, final Date date) {
        this.connection = connection;
        this.date = date;
    }

    /**
     *
     * Execute the query
     *
     * @return a list of WeeklyCalendarSlot objects that matched the query
     * @throws SQLException
     */
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
                result.add(new WeeklyCalendarSlot(lts, trainer, sub_trainer));
            }
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
