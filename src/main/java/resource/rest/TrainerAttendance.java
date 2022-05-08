package resource.rest;

import resource.LectureTimeSlot;
import resource.Reservation;
import resource.Subscription;

import java.util.List;

/**
 * Java Bean representing a trainee attendance
 *
 * @author Harjot Singh
 */
public class TrainerAttendance {
    final private LectureTimeSlot lecture;
    final private List<Reservation> reservations;
    final private List<Subscription> subscriptions;

    /**
     * Constructor for this class
     *
     * @param lecture       the lecture
     * @param reservations  the reservations
     * @param subscriptions the subscriptions
     */
    public TrainerAttendance(LectureTimeSlot lecture, List<Reservation> reservations, List<Subscription> subscriptions) {
        this.lecture = lecture;
        this.reservations = reservations;
        this.subscriptions = subscriptions;
    }

    /**
     * Gets the lecture
     *
     * @return the lecture
     */
    public LectureTimeSlot getLecture() {
        return lecture;
    }

    /**
     * Gets the reservations
     *
     * @return the list of reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Gets the list of subscriptions
     *
     * @return the list of subscriptions
     */
    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TrainerAttendance{lecture=");
        sb.append(lecture);
        sb.append(", reservations=");
        sb.append(reservations);
        sb.append(", subscriptions=");
        sb.append(subscriptions);
        sb.append("}");
        return sb.toString();
    }
}
