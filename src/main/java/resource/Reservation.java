package resource;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

/**
 * Java Bean used to represent a reservation
 *
 * @author Fatjon Freskina
 * @author Harjot Singh
 */
public class Reservation {

    private final String trainee;
    private final String room;
    private final Date lectureDate;
    private final Time lectureStartTime;

    /**
     * Constructor for this class
     *
     * @param trainee          Email of the trainee
     * @param room             The name of the room
     * @param lectureDate      Date of the lecture as Date
     * @param lectureStartTime The time the lecture starts
     */
    public Reservation(String trainee, String room, Date lectureDate, Time lectureStartTime) {
        this.trainee = trainee;
        this.room = room;
        this.lectureDate = lectureDate;
        this.lectureStartTime = lectureStartTime;
    }

    /**
     * Constructor for this class
     *
     * @param room             The name of the room
     * @param lectureDate      Date of the lecture as Date
     * @param lectureStartTime The time the lecture starts
     */
    public Reservation(String room, Date lectureDate, Time lectureStartTime) {
        this.trainee = null;
        this.room = room;
        this.lectureDate = lectureDate;
        this.lectureStartTime = lectureStartTime;
    }

    /**
     * Constructor for this class
     *
     * @param trainee Email of the trainee
     * @param r       the reservation
     * @param trainee the trainee
     */
    public Reservation(Reservation r, String trainee) {
        this.trainee = trainee;
        this.room = r.room;
        this.lectureDate = r.lectureDate;
        this.lectureStartTime = r.lectureStartTime;
    }


    /**
     * Gets the trainee associated to this reservation
     *
     * @return the trainee associated to this reservation
     */
    public final String getTrainee() {
        return trainee;
    }

    /**
     * Gets the room associated to this reservation
     *
     * @return the room associated to this reservation
     */
    public final String getRoom() {
        return room;
    }

    /**
     * Gets the lecture date associated to this reservation
     *
     * @return the lecture date associated to this reservation
     */
    public final Date getLectureDate() {
        return lectureDate;
    }

    /**
     * Gets the lecture start time associated to this reservation
     *
     * @return the lecture start time associated to this reservation
     */
    public final Time getLectureStartTime() {
        return lectureStartTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reservation{trainee=");
        sb.append(trainee);
        sb.append(", room='");
        sb.append(room);
        sb.append(", lectureDate='");
        sb.append(lectureDate);
        sb.append(", lectureStartTime='");
        sb.append(lectureStartTime);
        sb.append("'}");
        return sb.toString();
    }

    /* //TODO check if refactor is ok before deleting
    public String toString() {
        return "Reservation{" +
                "trainee='" + trainee + '\'' +
                ", room='" + room + '\'' +
                ", lectureDate='" + lectureDate + '\'' +
                ", lectureStartTime='" + lectureStartTime + '\'' +
                '}';
    }

     */

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
