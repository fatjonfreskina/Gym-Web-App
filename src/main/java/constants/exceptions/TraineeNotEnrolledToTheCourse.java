package constants.exceptions;

import constants.Codes;

/**
 * Exception thrown when a subscription is invalid (not started or expired)
 *
 * @author Harjot Singh
 */
public class TraineeNotEnrolledToTheCourse extends CustomException {
    /**
     * Constructs a new instance for this exception
     */
    public TraineeNotEnrolledToTheCourse() {
        super(Codes.TRAINEE_NOT_ENROLLED_TO_THE_COURSE);
    }
}
