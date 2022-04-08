package resource;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author Fatjon Freskina
 */
public class Reservation {

    private final String trainee;
    private final String room;
    private final Date lectureDate;
    private final Timestamp lectureStartTime;

    public Reservation(String trainee, String room, Date lectureDate, Timestamp lectureStartTime) {
        this.trainee = trainee;
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

    public final Timestamp getLectureStartTime() {
        return lectureStartTime;
    }

}
