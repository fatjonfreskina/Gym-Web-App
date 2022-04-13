package constants;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Enumeration that will contain error codes.
 * Error codes start with value 0 and goes down -1,-2 etc..
 */
public enum ErrorCodes {
    NO_CONTENT(204, HttpServletResponse.SC_NO_CONTENT, "NO CONTENT"),
    OK(0, HttpServletResponse.SC_OK, "OK"),
    EMPTY_INPUT_FIELDS(-1, HttpServletResponse.SC_BAD_REQUEST, "One or more input fields are empty."),
    DIFFERENT_PASSWORDS(-2, HttpServletResponse.SC_CONFLICT, "Different Passwords"),
    MINIMUM_AGE(-3, HttpServletResponse.SC_CONFLICT, "Minimum age not satified"),
    NOT_TELEPHONE_NUMBER(-4, HttpServletResponse.SC_CONFLICT, "Not a telephone number"),
    INVALID_FIELDS(-5, HttpServletResponse.SC_BAD_REQUEST, "Invalid Fields"),
    INVALID_FILE_TYPE(-6, HttpServletResponse.SC_BAD_REQUEST, "Invalid file type"),
    CANNOT_UPLOAD_FILE(-7, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot Upload File"),
    INTERNAL_ERROR(-8, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal error occurred"),
    USER_ALREADY_PRESENT(-9, HttpServletResponse.SC_CONFLICT, "User already present!"),
    NOT_A_MAIL(-10, HttpServletResponse.SC_BAD_REQUEST, "Please provide a mail address"),
    CONFIRMATION_NOT_FOUND(-11, HttpServletResponse.SC_BAD_REQUEST, "Registration not found"),
    BAD_REQUEST(-12, HttpServletResponse.SC_BAD_REQUEST, "Bad request"),
    NOT_AUTHENTICATED(-13, HttpServletResponse.SC_UNAUTHORIZED, "The provided credentials are wrong"),
    OVERLAPPING(-14, HttpServletResponse.SC_BAD_REQUEST, "There are overlapping!"),
    NO_COURSES_TAUGHT(-15, HttpServletResponse.SC_BAD_REQUEST, "There are no courses you are teaching"),
    NO_COURSES_HELD_NOW(-16, HttpServletResponse.SC_BAD_REQUEST, "There are no courses to be held right now"),
    FREE_TRIAL_ALREADY_DONE(-17, HttpServletResponse.SC_BAD_REQUEST, "Aldreay done free trial"),
    OVELAPPING_SUBSCRIPTIONS(-18, HttpServletResponse.SC_BAD_REQUEST, "Overlapping Subscription"),
    EMAIL_NOT_FOUND(-19, HttpServletResponse.SC_BAD_REQUEST, "Email not associated to a user"),
    ACCEPT_MISSING(-20, HttpServletResponse.SC_BAD_REQUEST, "Accept field missing"),
    MEDIA_TYPE_NOT_SUPPORTED(-21, HttpServletResponse.SC_NOT_ACCEPTABLE, "Requested media type not supported"),
    CONTENTTYPE_MISSING(-22, HttpServletResponse.SC_BAD_REQUEST, "Content type field missing"),
    DELETE_OPERATION_NOT_SUPPORTED(-23, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "DELETE operation not supported"),
    GET_OPERATION_NOT_SUPPORTED(-24, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET operation not supported"),
    HEAD_OPERATION_NOT_SUPPORTED(-25, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "HEAD operation not supported"),
    OPTIONS_OPERATION_NOT_SUPPORTED(-26, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "OPTIONS operation not supported"),
    POST_OPERATION_NOT_SUPPORTED(-27, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST operation not supported"),
    PUT_OPERATION_NOT_SUPPORTED(-28, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "PUT operation not supported"),
    TRACE_OPERATION_NOT_SUPPORTED(-29, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "TRACE operation not supported"),
    METHOD_NOT_ALLOWED(-30, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Unsupported operation for the requested URI"),
    UNEXPECTED_ERROR(-31, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error"),
    ROOM_ALREADY_FULL(-32, HttpServletResponse.SC_BAD_REQUEST, "There are no more spots left"),
    INVALID_DATE(-33, HttpServletResponse.SC_BAD_REQUEST, "Can't insert date in the past!"),
    TRAINEE_NOT_ENROLLED_TO_THE_COURSE(-34, HttpServletResponse.SC_FORBIDDEN, "Trainee must be enrolled to the course to make a reservation"),
    RESERVATION_ALREADY_PRESENT(-35, HttpServletResponse.SC_CONFLICT, "Reservation already present"),
    USER_HAS_NO_ROLE_ASSIGNED(-35, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User has no role assigned to it")
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