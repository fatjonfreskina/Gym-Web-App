package resource;

/**
 * This class represents both informative and error messages
 */
public class Message {

    private final String message;
    private final boolean isError;

    /**
     * This constructor is used for error messages
     *
     * @param message general description of the error
     */
    public Message(final String message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

    /**
     * This constructor is used for informative messages
     *
     * @param message what we have to say
     */
    public Message(final String message) {
        this.message = message;
        isError = false;

    }

    /**
     * Gets the message
     *
     * @return the message
     */
    public final String getMessage() {
        return message;
    }

    /**
     * Returns if this message represents an error
     *
     * @return true if the message is an error, false otherwise
     */
    public final boolean isError() {
        return isError;
    }
}
