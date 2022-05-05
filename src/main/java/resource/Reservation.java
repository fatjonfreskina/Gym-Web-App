package resource;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

/**
 * @author Fatjon Freskina
 * @author Harjot Singh
 */
public class Reservation {

  private final String trainee;
  private final String room;
  private final Date lectureDate;
  private final Time lectureStartTime;

  /**
   *
   * @param trainee           Email of the trainee
   * @param room              The name of the room
   * @param lectureDate       Date of the lecture as Date
   * @param lectureStartTime  The time the lecture starts
   */

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

    public Reservation(Reservation r, String trainee){
        this.trainee = trainee;
        this.room = r.room;
        this.lectureDate = r.lectureDate;
        this.lectureStartTime = r.lectureStartTime;
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

  @Override
  public String toString() {
    //return "Reservation" + new Gson().toJson(this, Reservation.class);//
    return "Reservation{" +
        "trainee='" + trainee + '\'' +
        ", room='" + room + '\'' +
        ", lectureDate='" + lectureDate + '\'' +
        ", lectureStartTime='" + lectureStartTime + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Reservation that = (Reservation) o;
    return Objects.equals(trainee, that.trainee) && Objects.equals(room, that.room) && Objects.equals(lectureDate, that.lectureDate) && Objects.equals(lectureStartTime, that.lectureStartTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(trainee, room, lectureDate, lectureStartTime);
  }
}
