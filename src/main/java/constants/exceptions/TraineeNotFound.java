package constants.exceptions;

import constants.Codes;

/**
 * Exception thrown when a trainer has not been found in the database
 *
 * @author Harjot Singh
 */
public class TraineeNotFound extends CustomException {
    /**
     * Constructs a new instance for this exception
     */
    public TraineeNotFound() {
        super(Codes.EMAIL_NOT_FOUND);
    }
}
