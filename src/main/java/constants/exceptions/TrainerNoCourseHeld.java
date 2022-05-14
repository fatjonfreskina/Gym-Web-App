package constants.exceptions;

import constants.Codes;

/**
 * Exception thrown when a trainer do not teach a course
 *
 * @author Harjot Singh
 */
public class TrainerNoCourseHeld extends CustomException {
    /**
     * Constructs a new instance for this exception
     */
    public TrainerNoCourseHeld() {
        super(Codes.NO_COURSES_TAUGHT);
    }
}
