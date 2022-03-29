package resource;

import java.util.Date;

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

    private final int substitution;

    /**
     * Parametric constructor for a Subscription
     * @param courseEditionID the ID of the edition of the course
     * @param courseName the name of the course
     * @param duration the duration of the subscription (expressed in days)
     * @param startDay the day from which the subscription is active
     * @param substitution the ID of the Person that performs the substitution
     */
    public Subscription(int courseEditionID, String courseName, int duration, Date startDay, int substitution) {
        this.courseEditionID = courseEditionID;
        this.courseName = courseName;
        this.duration = duration;
        this.startDay = startDay;
        this.substitution = substitution;
    }

    public final int getCourseEditionID() {
        return courseEditionID;
    }

    public final String getCourseName() {
        return courseName;
    }

    public final int getDuration() {
        return duration;
    }

    public final Date getStartDay() {
        return startDay;
    }

    public final int getSubstitution() {
        return substitution;
    }

}
