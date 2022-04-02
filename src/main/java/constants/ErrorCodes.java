package constants;

import jakarta.servlet.http.HttpServletResponse;
/**
 * Enumeration that will contain error codes.
 * Error codes start with value 0 and goes down -1,-2 etc..
 * */
public enum ErrorCodes {
    OK(0, HttpServletResponse.SC_OK,"OK.")

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