package database;

import dao.lecturetimeslot.GetLectureTimeSlotsByCourseDatabase;
import dao.lecturetimeslot.GetLectureTimeSlotsByRoomNameDatabase;
import resource.LectureTimeSlot;
import resource.Room;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Prova {
    
    private static final String DB_GWA = "jdbc:postgresql://localhost:5432/gwa_db";
    private static final String USER = "robot";
    private static final String PASS = "robot";
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_GWA, USER, PASS);
        List<LectureTimeSlot> lectureTimeSlot = (new GetLectureTimeSlotsByRoomNameDatabase(conn,"Stamina").execute());

        LectureTimeSlot l = lectureTimeSlot.get(0);

        String x =l.toGson();

        System.out.println(x);
    }
}
