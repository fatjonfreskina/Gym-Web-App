package constants.exceptions;

import constants.Codes;

/**
 * Exception thrown when a trainer has already a lesson associated to a timeslot, so is not possible to add another
 * course
 *
 * @author Harjot Singh
 */
public class TrainerCoursesOverlapping extends CustomException {
    /**
     * Constructs a new instance for this exception
     */
    public TrainerCoursesOverlapping() {
        super(Codes.OVERLAPPING_COURSES);
    }
}
