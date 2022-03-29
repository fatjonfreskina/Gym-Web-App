package resource;

import java.sql.Date; 
import java.sql.Timestamp;

public class Reservation {

    private final String trainee;
    private final String room;
    private final Date lectureDate;
    private final Timestamp lectureStartTime;

    public Reservation (String trainee, String room, Date lectureDate, Timestamp lectureStartTime){
        this.trainee = trainee;
        this.room = room;
        this.lectureDate = lectureDate;
        this.lectureStartTime = lectureStartTime;
    }

    public String getTrainee(){
        return trainee;
    }

    public String getRoom(){
        return room;
    }

    public Date getLectureDate(){
        return lectureDate;
    }

    public Timestamp getLectureStartTime(){
        return lectureStartTime;
    }

}
