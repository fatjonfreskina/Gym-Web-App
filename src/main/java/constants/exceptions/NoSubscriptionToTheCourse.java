package constants.exceptions;

import constants.Codes;

/**
 * Custom exception thrown when an user is not subscribed to the course
 *
 * @author Harjot Singh
 */
public class NoSubscriptionToTheCourse extends CustomException {
    /**
     * Constructs a new instance for this exception
     */
    public NoSubscriptionToTheCourse() {
        super(Codes.NO_SUBSCRIPTION_TO_THE_COURSE);
    }
}
