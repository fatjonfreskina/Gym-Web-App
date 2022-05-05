package resource;

import java.sql.Date;
import java.util.Objects;

/**
 * This Bean contains all the info about a subscription.
 *
 * @author Riccardo Forzan
 * @author Harjot Singh
 */
public class Subscription {

  private final int courseEditionID;
  private final String courseName;
  private final int duration;
  private final Date startDay;
  private final int discount;
  private final String trainee;

  /**
   * Parametric constructor for Subscription
   *
   * @param courseEditionID ID of a course edition
   * @param courseName      name of the course
   * @param duration        duration of the subscription (in days)
   * @param startDay        date from which the subscription starts
   * @param discount        discount applied to the price of the subscription
   * @param trainee         email of the trainer
   */
  public Subscription(int courseEditionID, String courseName, int duration, Date startDay, int discount, String trainee) {
    this.courseEditionID = courseEditionID;
    this.courseName = courseName;
    this.duration = duration;
    this.startDay = startDay;
    this.discount = discount;
    this.trainee = trainee;
  }

  /**
   * Parametric constructor for Subscription
   *
   * @param courseEditionID ID of a course edition
   * @param courseName      name of the course
   */
  public Subscription(int courseEditionID, String courseName) {
    this.courseEditionID = courseEditionID;
    this.courseName = courseName;
    this.duration = 0;
    this.startDay = null;
    this.discount = 0;
    this.trainee = null;
  }

  /**
   * Gets the course edition
   * @return  the course edition
   */
  public int getCourseEditionID() {
    return courseEditionID;
  }

  /**
   * Gets the course name
   * @return  the course name
   */
  public String getCourseName() {
    return courseName;
  }

  /**
   * Gets the course duration
   * @return  the course duration
   */
  public int getDuration() {
    return duration;
  }

  /**
   * Gets the starting day of the course
   * @return  the starting day of the course
   */
  public Date getStartDay() {
    return startDay;
  }

  /**
   * Gets the discount
   * @return  the discount
   */
  public int getDiscount() {
    return discount;
  }

  /**
   * Gets the trainee
   * @return  the trainee
   */
  public String getTrainee() {
    return trainee;
  }

  @Override
  public String toString() {
    //System.out.println(new Gson().toJson(this,Subscription.class));
    return "Subscription{" +
        "courseEditionID=" + courseEditionID +
        ", courseName='" + courseName + '\'' +
        ", duration=" + duration +
        ", startDay='" + startDay + '\'' +
        ", discount=" + discount +
        ", trainee='" + trainee + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Subscription that = (Subscription) o;
    /*boolean equalCourse = courseEditionID == that.courseEditionID && Objects.equals(courseName, that.courseName),
        equalTrainer = Objects.equals(trainee, that.trainee),
        equalDuration = duration == that.duration,
        equalDiscount = discount == that.discount,
        equalStartDay = Objects.equals(startDay, that.startDay);
    //System.out.println(startDay + " vs " + that.startDay);
    //System.out.println(">>>%s,%s,%s,%s,%s".formatted(equalCourse, equalTrainer, equalDuration, equalDiscount, equalStartDay));*/
    return courseEditionID == that.courseEditionID && duration == that.duration && discount == that.discount && Objects.equals(courseName, that.courseName) && Objects.equals(startDay, that.startDay) && Objects.equals(trainee, that.trainee);
  }

  @Override
  public int hashCode() {
    return Objects.hash(courseEditionID, courseName, duration, startDay, discount, trainee);
  }
}
