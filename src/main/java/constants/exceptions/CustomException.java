package constants.exceptions;

import constants.Codes;

/**
 * Custom exception
 *
 * @author Harjot Singh
 */
public class CustomException extends Exception {
    /**
     * The error code
     */
    final private Codes code;

    /**
     * Constructs a new instance of CustomException
     *
     * @param code {@see constants.Codes} error code to be printed
     */
    public CustomException(final Codes code) {
        super(code.getErrorMessage());
        this.code = code;
    }

    /**
     * Returns the error code associated to this exception
     *
     * @return {@see constants.Codes} associated to this exception
     */
    public Codes getErrorCode() {
        return code;
    }
}
