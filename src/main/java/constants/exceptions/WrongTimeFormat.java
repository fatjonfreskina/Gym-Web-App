package constants.exceptions;

import constants.Codes;

/**
 * Exception thrown when a parameter has a wrong time format
 *
 * @author Harjot Singh
 */
public class WrongTimeFormat extends CustomException {
    /**
     * Constructs a new instance for this exception
     */
    public WrongTimeFormat() {
        super(Codes.WRONG_TIME_FORMAT);
    }
}
