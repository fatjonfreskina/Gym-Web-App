package resource;

/**
 * SubscriptionType is the JavaBean representing a type of subscription that can be
 * bought by a Trainee. The subscription, in fact, can have a different cost based
 * on the course (and its edition) for which the subscription is done and also for
 * the duration of the subscription itself.
 *
 * @author Andrea Pasin
 */


public class SubscriptionType {
    private final int courseEditionID;
    private final String courseName;
    private final int duration;
    private final float cost;

    /**
     * Constructor for the SubscriptionType class
     *
     * @param courseEditionID the ID of the edition of the course
     * @param courseName      the name of the course
     * @param duration        the duration of the subscription
     * @param cost            the cost of the type of subscription based on the duration and the course edition
     */
    public SubscriptionType(final int courseEditionID, final String courseName, final int duration, final float cost) {
        this.courseEditionID = courseEditionID;
        this.courseName = courseName;
        this.duration = duration;
        this.cost = cost;
    }

    /**
     * Constructor for the SubscriptionType class. The cost of this type of subscription is by default set to -1
     *
     * @param courseEditionID the ID of the edition of the course
     * @param courseName      the name of the course
     * @param duration        the duration of the subscription
     */
    public SubscriptionType(final int courseEditionID, final String courseName, final int duration) {
        this.courseEditionID = courseEditionID;
        this.courseName = courseName;
        this.duration = duration;
        this.cost = -1;
    }

    /**
     * Gets the ID of the edition of the course
     *
     * @return the ID of the edition of the considered course
     */
    public final int getCourseEditionID() {
        return courseEditionID;
    }

    /**
     * Gets the name of the course
     *
     * @return the name of the course
     */
    public final String getCourseName() {
        return courseName;
    }

    /**
     * Gets the duration of the subscription
     *
     * @return the duration of the subscription in days
     */
    public final int getDuration() {
        return duration;
    }

    /**
     * Gets the cost of the subscription
     *
     * @return the cost of the type of subscription in the current currency
     */
    public final float getCost() {
        return cost;
    }
}
