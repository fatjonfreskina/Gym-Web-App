package constants.exceptions;

import constants.Codes;

/**
 * Exception thrown when a trainer has already a lesson associated to a given timeslot
 *
 * @author Harjot Singh
 */
public class TrainerNoCourseHeldNow extends CustomException {
    /**
     * Constructs a new instance for this exception
     */
    public TrainerNoCourseHeldNow() {
        super(Codes.NO_COURSES_HELD_NOW);
    }
}
