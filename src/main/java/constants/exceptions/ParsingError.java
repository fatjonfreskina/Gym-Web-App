package constants.exceptions;

import constants.Codes;

/**
 * Exception thrown when an error during parsing occurs
 *
 * @author Harjot Singh
 */
public class ParsingError extends CustomException {
    /**
     * Constructs a new instance for this exception
     */
    public ParsingError() {
        super(Codes.PARSING_ERROR);
    }
}
