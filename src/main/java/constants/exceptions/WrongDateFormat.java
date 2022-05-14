package constants.exceptions;

import constants.Codes;

/**
 * Exception thrown when a parameter has a wrong date format
 *
 * @author Harjot Singh
 */
public class WrongDateFormat extends CustomException {
    /**
     * Constructs a new instance for this exception
     */
    public WrongDateFormat() {
        super(Codes.WRONG_DATE_FORMAT);
    }
}
