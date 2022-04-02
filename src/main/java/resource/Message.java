package resource;

/**
 * This class represents both informative and error messages
 */
public class Message {

    private final String message;
    private final String errorCode;
    private final String errorDetails;
    private final boolean isError;

    /**
     * This constructor is used for error messages
     *
     * @param message      general description of the error
     * @param errorCode    error code thrown by an exception, e.g. SQL Error Code
     * @param errorDetails more details about the error
     */
    public Message(final String message, final String errorCode, final String errorDetails, boolean isError) {
        this.message = message;
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
        this.isError = isError;
    }

    /**
     * This constructor is used for informative messages
     *
     * @param message what we have to say
     */
    public Message(final String message) {
        this.message = message;
        errorCode = null;
        errorDetails = null;
        isError = false;

    }

    public final String getMessage() {
        return message;
    }

    public final String getErrorCode() {
        return errorCode;
    }

    public final String getErrorDetails() {
        return errorDetails;
    }

    public final boolean isError() {
        return isError;
    }
}
