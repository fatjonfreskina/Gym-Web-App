package resource;

import java.sql.Date;

/**
 * This Bean contains all the info about a subscription.
 *
 * @author Riccardo Forzan
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
     * @param trainee
     */
    public Subscription(int courseEditionID, String courseName, int duration, Date startDay, int discount, String trainee) {
        this.courseEditionID = courseEditionID;
        this.courseName = courseName;
        this.duration = duration;
        this.startDay = startDay;
        this.discount = discount;
        this.trainee = trainee;
    }

    public int getCourseEditionID() {
        return courseEditionID;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getDuration() {
        return duration;
    }

    public Date getStartDay() {
        return startDay;
    }

    public int getDiscount() {
        return discount;
    }

    public String getTrainee() {
        return trainee;
    }

}
