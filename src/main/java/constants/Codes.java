package constants;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Enumeration that will contain codes, for success and for errors
 * Success codes have positive int values associated
 * Error codes have negative values associated
 */
public enum Codes {

    /**
     * Success code, password has been changed
     */
    PASSWORD_CHANGED(2, HttpServletResponse.SC_OK, "Password has been changed!"),

    /**
     * Success code, password reset has been sent
     */
    PASSWORD_RESET_SENT(1, HttpServletResponse.SC_OK, "Password reset email sent!"),

    /**
     * Generic success code
     */
    OK(0, HttpServletResponse.SC_OK, "OK"),

    /**
     * Returned when some required input fields are missing
     */
    EMPTY_INPUT_FIELDS(-1, HttpServletResponse.SC_BAD_REQUEST, "One or more input fields are empty."),

    /**
     * Returned when the password and the confirmation one are different
     */
    DIFFERENT_PASSWORDS(-2, HttpServletResponse.SC_CONFLICT, "Different Passwords"),

    /**
     * Returned when the minimum age is not satisfied
     */
    MINIMUM_AGE(-3, HttpServletResponse.SC_CONFLICT, "Minimum age not satisfied"),

    /**
     * Returned when the telephone number is not valid
     */
    NOT_TELEPHONE_NUMBER(-4, HttpServletResponse.SC_CONFLICT, "Not a telephone number"),

    /**
     * Returned when some fields are not valid
     */
    INVALID_FIELDS(-5, HttpServletResponse.SC_BAD_REQUEST, "Invalid Fields"),

    /**
     * Returned when the file type given does not match the expected one
     */
    INVALID_FILE_TYPE(-6, HttpServletResponse.SC_BAD_REQUEST, "Invalid file type"),

    /**
     * Returned when an error occurs when uploading a file
     */
    CANNOT_UPLOAD_FILE(-7, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot Upload File"),

    /**
     * Returned when a generic internal error occurs
     */
    INTERNAL_ERROR(-8, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal error occurred"),

    /**
     * Returned when the user is already present
     */
    USER_ALREADY_PRESENT(-9, HttpServletResponse.SC_CONFLICT, "User already present!"),

    /**
     * Returned when the email provided is not valid
     */
    NOT_A_MAIL(-10, HttpServletResponse.SC_BAD_REQUEST, "Please provide a mail address"),

    /**
     * Returned when the token to confirm registration is not found
     */
    CONFIRMATION_NOT_FOUND(-11, HttpServletResponse.SC_BAD_REQUEST, "Registration not found"),

    /**
     * Generic bad request error
     */
    BAD_REQUEST(-12, HttpServletResponse.SC_BAD_REQUEST, "Bad request"),

    /**
     * Returned when the user is not autenticated
     */
    NOT_AUTHENTICATED(-13, HttpServletResponse.SC_UNAUTHORIZED, "The provided credentials are wrong"),

    /**
     * Returned when the course overlaps
     */
    OVERLAPPING_COURSES(-14, HttpServletResponse.SC_BAD_REQUEST, "There are overlapping courses!"),

    /**
     * Returned when the trainer does not teach any course
     */
    NO_COURSES_TAUGHT(-15, HttpServletResponse.SC_BAD_REQUEST, "There are no courses you teach!"),

    /**
     * Returned when the trainer does not teach any course for a given time slot
     */
    NO_COURSES_HELD_NOW(-16, HttpServletResponse.SC_BAD_REQUEST, "There is no course you teach at this time!"),

    /**
     * Returned when the free trial period has been already consumed
     */
    FREE_TRIAL_ALREADY_DONE(-17, HttpServletResponse.SC_BAD_REQUEST, "Already done free trial"),

    /**
     * Returned when some subscription will overlap
     */
    OVELAPPING_SUBSCRIPTIONS(-18, HttpServletResponse.SC_BAD_REQUEST, "Overlapping Subscription"),

    /**
     * Returned when the email address is not found
     */
    EMAIL_NOT_FOUND(-19, HttpServletResponse.SC_BAD_REQUEST, "Email not associated to a user"),

    /**
     * Returned when the accepting field is missing
     */
    ACCEPT_MISSING(-20, HttpServletResponse.SC_BAD_REQUEST, "Accept field missing"),

    /**
     * Returned when the media type given is not supported
     */
    MEDIA_TYPE_NOT_SUPPORTED(-21, HttpServletResponse.SC_NOT_ACCEPTABLE, "Requested media type not supported"),

    /**
     * Returned when the content type given is not supported
     */
    CONTENTTYPE_MISSING(-22, HttpServletResponse.SC_BAD_REQUEST, "Content type field missing"),

    /**
     * Returned when the DELETE operation is not supported
     */
    DELETE_OPERATION_NOT_SUPPORTED(-23, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "DELETE operation not supported"),

    /**
     * Returned when the GET operation is not supported
     */
    GET_OPERATION_NOT_SUPPORTED(-24, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET operation not supported"),

    /**
     * Returned when the HEAD operation is not supported
     */
    HEAD_OPERATION_NOT_SUPPORTED(-25, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "HEAD operation not supported"),

    /**
     * Returned when the OPTIONS operation is not supported
     */
    OPTIONS_OPERATION_NOT_SUPPORTED(-26, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "OPTIONS operation not supported"),

    /**
     * Returned when the POST operation is not supported
     */
    POST_OPERATION_NOT_SUPPORTED(-27, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST operation not supported"),

    /**
     * Returned when the PUT operation is not supported
     */
    PUT_OPERATION_NOT_SUPPORTED(-28, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "PUT operation not supported"),

    /**
     * Returned when the TRACE operation is not supported
     */
    TRACE_OPERATION_NOT_SUPPORTED(-29, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "TRACE operation not supported"),

    /**
     * Returned when the method is not supported
     */
    METHOD_NOT_ALLOWED(-30, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unsupported operation for the requested URI"),

    /**
     *  Returned when a generic error occurs
     */
    UNEXPECTED_ERROR(-31, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error"),

    /**
     *
     */
    ROOM_ALREADY_FULL(-32, HttpServletResponse.SC_BAD_REQUEST, "There are no more spots left"),

    /**
     *
     */
    INVALID_DATE(-33, HttpServletResponse.SC_BAD_REQUEST, "The date specified is invalid"),

    /**
     *
     */
    TRAINEE_NOT_ENROLLED_TO_THE_COURSE(-34, HttpServletResponse.SC_BAD_REQUEST, "Trainee is not enrolled to your course!"),

    /**
     *
     */
    RESERVATION_ALREADY_PRESENT(-35, HttpServletResponse.SC_CONFLICT, "Reservation already present!"),

    /**
     *
     */
    RESERVATION_NOT_FOUND(-36, HttpServletResponse.SC_NO_CONTENT, "No Reservation found!"),

    /**
     *
     */
    USER_HAS_NO_ROLE_ASSIGNED(-37, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User has no role assigned to it"),

    /**
     *
     */
    CONTENTTYPE_UNSUPPORTED(-38, HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Unsupported input media type"),

    /**
     *
     */
    LECTURETIMESLOT_NOT_FOUND(-39, HttpServletResponse.SC_NOT_FOUND, "LectureTimeSlot not found"),

    /**
     *
     */
    TOKEN_NOT_FOUND(-40, HttpServletResponse.SC_NOT_FOUND, "Token for password reset not found"),

    /**
     *
     */
    PASSWORD_NOT_VALID(-41, HttpServletResponse.SC_CONFLICT, "Password is not valid"),

    /**
     *
     */
    PERSON_NOT_FOUND(-42, HttpServletResponse.SC_NOT_FOUND, "Person not found"),

    /**
     *
     */
    WRONG_DATE_FORMAT(-43, /*422 Unprocessable Entity, NOT PRESENT */ HttpServletResponse.SC_BAD_REQUEST, "Wrong date format, date format should be in ISO 2014 [yyyy]-[MM]-[dd] format!"),

    /**
     *
     */
    WRONG_TIME_FORMAT(-44, /*422 Unprocessable Entity, NOT PRESENT */ HttpServletResponse.SC_BAD_REQUEST, "Wrong time format, time format should be in ISO 8601's 24-hour clock T[hh]:[mm]:[ss] format!"),

    /**
     *
     */
    NOT_ACCEPTABLE_MISSING_FIELDS(-45, HttpServletResponse.SC_BAD_REQUEST, "Request not acceptable, missing fields."),

    /**
     *
     */
    NO_SUBSCRIPTION_TO_THE_COURSE(-46, HttpServletResponse.SC_NO_CONTENT, "Trainer has no Trainee subscribed to its course."),

    /**
     *
     */
    SUBSCRIPTION_IS_NOT_CURRENTLY_VALID(-47, HttpServletResponse.SC_NO_CONTENT, "Subscription is not currently valid."),

    /**
     *
     */
    WRONG_JSON_RESERVATION(-48, HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Wrong json reservation"),

    /**
     *
     */
    TYPE_SUBSCRIPTION_INVALID(-49, HttpServletResponse.SC_BAD_REQUEST, "Remaining days of the selected course are less than the subscription duration"),

    /**
     *
     */
    SUBSCRIPION_EXPIRED_BEFORE(-50, HttpServletResponse.SC_NOT_ACCEPTABLE, "Subscription expired before the requested reservation"),

    /**
     *
     */
    CONFLICT_BETWEEN_RESERVATION_AND_LECTURETIMESLOT_VALUES(-51, HttpServletResponse.SC_CONFLICT, "Conflict between Reservation and LectureTimeSlot values!"),

    /**
     * Returned when a wrong type value for a field is given
     */
    PARSING_ERROR(-52, HttpServletResponse.SC_BAD_REQUEST, "Wrong type for a field.");

    private final int errorCode;
    private final int httpCode;
    private final String errorMessage;

    /**
     * Creates a new object
     *
     * @param errorCode    our internal error code (declared in this class)
     * @param httpCode     http code associated to the event
     * @param errorMessage explicative messages
     */
    Codes(int errorCode, int httpCode, String errorMessage) {
        this.errorCode = errorCode;
        this.httpCode = httpCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Get the error code associated to the object
     *
     * @return our internal error code (declared in this class)
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Returns the HTTP code associated
     *
     * @return HTTP code associated with this object
     */
    public int getHTTPCode() {
        return httpCode;
    }

    /**
     * Returns the error message associated
     *
     * @return error message associated with this object
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "Codes{errorCode=%s, httpCode=%s, errorMessage=%s\n"
                .formatted(this.errorCode, this.httpCode, this.errorMessage);
    }
}