package constants.exceptions;

import constants.Codes;

/**
 * Custom exception thrown when a required field is missing
 *
 * @author Harjot Singh
 */
public class NotAcceptableMissingFields extends CustomException {
    /**
     * Constructs a new instance for this exception
     */
    public NotAcceptableMissingFields() {
        super(Codes.NOT_ACCEPTABLE_MISSING_FIELDS);
    }
}
