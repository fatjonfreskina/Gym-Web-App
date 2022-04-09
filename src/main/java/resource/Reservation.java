package resource;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author Fatjon Freskina
 */
public class Reservation {

    private final String trainee;
    private final String room;
    private final Date lectureDate;
    private final Time lectureStartTime;

    public Reservation(String trainee, String room, Date lectureDate, Time lectureStartTime) {
        this.trainee = trainee;
        this.room = room;
        this.lectureDate = lectureDate;
        this.lectureStartTime = lectureStartTime;
    }

    public Reservation(String room, Date lectureDate, Time lectureStartTime) {
        this.trainee = null;
        this.room = room;
        this.lectureDate = lectureDate;
        this.lectureStartTime = lectureStartTime;
    }


    public final String getTrainee() {
        return trainee;
    }

    public final String getRoom() {
        return room;
    }

    public final Date getLectureDate() {
        return lectureDate;
    }

    public final Time getLectureStartTime() {
        return lectureStartTime;
    }

}
