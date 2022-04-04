package constants;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Enumeration that will contain error codes.
 * Error codes start with value 0 and goes down -1,-2 etc..
 */
public enum ErrorCodes {
    OK(0, HttpServletResponse.SC_OK, "OK."),
    EMPTY_INPUT_FIELDS(-1, HttpServletResponse.SC_BAD_REQUEST, "One or more input fields are empty."),
    DIFFERENT_PASSWORDS(-2, HttpServletResponse.SC_CONFLICT, "Different Passwords"),
    MINIMUM_AGE(-3, HttpServletResponse.SC_CONFLICT, "Minimum age not satified"),
    NOT_TELEPHONE_NUMBER(-4, HttpServletResponse.SC_CONFLICT, "Not a telephone number"),
    INVALID_FIELDS(-5, HttpServletResponse.SC_BAD_REQUEST, "Invalid Fields"),
    INVALID_FILE_TYPE(-6, HttpServletResponse.SC_BAD_REQUEST, "Invalid file type"),
    CANNOT_UPLOAD_FILE(-7, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot Upload File"),
    INTERNAL_ERROR(-8, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot Upload File"),
    USER_ALREADY_PRESENT(-9, HttpServletResponse.SC_CONFLICT, "User already present!"),
    NOT_A_MAIL(-10, HttpServletResponse.SC_BAD_REQUEST, "Please provide a mail address"),
    ;
    private final int errorCode;
    private final int httpCode;
    private final String errorMessage;

    ErrorCodes(int errorCode, int httpCode, String errorMessage) {
        this.errorCode = errorCode;
        this.httpCode = httpCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getHTTPCode() {
        return httpCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}