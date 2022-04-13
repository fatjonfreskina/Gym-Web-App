package resource.rest;

import resource.LectureTimeSlot;
import resource.Reservation;
import resource.Subscription;

import java.util.List;

public class TrainerAttendance {
  final private LectureTimeSlot lecture;
  final private List<Reservation> reservations;
  final private List<Subscription> subscriptions;

  public TrainerAttendance(LectureTimeSlot lecture, List<Reservation> reservations, List<Subscription> subscriptions) {
    this.lecture = lecture;
    this.reservations = reservations;
    this.subscriptions = subscriptions;
  }

  public LectureTimeSlot getLecture() {
    return lecture;
  }

  public List<Reservation> getReservations() {
    return reservations;
  }

  public List<Subscription> getSubscriptions() {
    return subscriptions;
  }

  @Override
  public String toString() {
    return "TrainerAttendance{" +
        "lecture=" + lecture +
        ", reservations=" + reservations +
        ", subscriptions=" + subscriptions +
        '}';
  }
}
