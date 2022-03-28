package resource;

import java.sql.Date; 
import java.sql.Timestamp;

public class Reservation {

    private String trainee;
    private String room;
    private Date lectureDate;
    private Timestamp lectureStartTime;

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

    public void setTrainee(String newTrainee){
        this.trainee = newTrainee;
    }

    public void setRoom(String newRoom){
        this.room = newRoom;
    }

    public void setLectureDate(Date newLectureDate){
        this.lectureDate = newLectureDate;
    }

    public void setLectureStartTime(Timestamp newStartTime){
        this.lectureStartTime = newStartTime;
    }
    
}
