package resource;

import java.time.LocalDate; // https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html
import java.sql.Timestamp;

public class Reservation {

    private String trainee;
    private String room;
    private LocalDate lectureDate;
    private Timestamp lectureStartTime;

    public Reservation (String trainee, String room, LocalDate lectureDate, Timestamp lectureStartTime){
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

    public LocalDate getLectureDate(){
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

    public void setLectureDate(LocalDate newLectureDate){
        this.lectureDate = newLectureDate;
    }

    public void setLectureStartTime(Timestamp newStartTime){
        this.lectureStartTime = newStartTime;
    }
    
}
