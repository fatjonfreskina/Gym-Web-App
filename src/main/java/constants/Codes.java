package constants;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Enumeration that will contain codes, for success and for errors
 * Error codes start with value 0 and goes down -1,-2 etc..
 */
public enum Codes {
  NO_CONTENT(3, HttpServletResponse.SC_NO_CONTENT, "NO CONTENT"),
  PASSWORD_CHANGED(2, HttpServletResponse.SC_OK, "Password has been changed!"),
  PASSWORD_RESET_SENT(1, HttpServletResponse.SC_OK, "Password reset email sent!"),
  OK(0, HttpServletResponse.SC_OK, "OK"),
  EMPTY_INPUT_FIELDS(-1, HttpServletResponse.SC_BAD_REQUEST, "One or more input fields are empty."),
  DIFFERENT_PASSWORDS(-2, HttpServletResponse.SC_CONFLICT, "Different Passwords"),
  MINIMUM_AGE(-3, HttpServletResponse.SC_CONFLICT, "Minimum age not satisfied"),
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
  OVERLAPPING_COURSES(-14, HttpServletResponse.SC_BAD_REQUEST, "There are overlapping courses!"),
  NO_COURSES_TAUGHT(-15, HttpServletResponse.SC_BAD_REQUEST, "There are no courses you are teaching"),
  NO_COURSES_HELD_NOW(-16, HttpServletResponse.SC_BAD_REQUEST, "There are no courses to be held right now"),
  FREE_TRIAL_ALREADY_DONE(-17, HttpServletResponse.SC_BAD_REQUEST, "Already done free trial"),
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
  RESERVATION_NOT_FOUND(-36, HttpServletResponse.SC_NO_CONTENT, "No Reservation found"),
  USER_HAS_NO_ROLE_ASSIGNED(-37, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User has no role assigned to it"),
  CONTENTTYPE_UNSUPPORTED(-38, HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Unsupported input media type"),
  LECTURETIMESLOT_NOT_FOUND(-39, HttpServletResponse.SC_NOT_FOUND, "LectureTimeSlot not found"),
  TOKEN_NOT_FOUND(-40, HttpServletResponse.SC_NOT_FOUND, "Token for password reset not found"),
  PASSWORD_NOT_VALID(-41, HttpServletResponse.SC_CONFLICT, "Password is not valid"),
  PERSON_NOT_FOUND(-42, HttpServletResponse.SC_NOT_FOUND, "Person not found"),
  WRONG_DATE_OR_TIME_FORMAT(-43, HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Wrong date or time format, " +
      "date format should be in ISO 2014 [yyyy]-[MM]-[dd], " +
      "and time format should be in ISO 8601's 24-hour clock T[hh]:[mm]:[ss] "),
  NOT_ACCEPTABLE_MISSING_FIELDS(-44, HttpServletResponse.SC_NOT_ACCEPTABLE, "Request not acceptable, missing fields."),
  NO_SUBSCRIPTION_TO_THE_COURSE(-45, HttpServletResponse.SC_NO_CONTENT, "Trainer has no Trainee subscribed to its course."),
  SUBSCRIPTION_IS_NOT_CURRENTLY_VALID(-46, HttpServletResponse.SC_NO_CONTENT, "Subscription is not currently valid."),
  WRONG_JSON_RESERVATION(-47, HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Wrong json reservation"),
  TYPE_SUBSCRIPTION_INVALID(-49, HttpServletResponse.SC_BAD_REQUEST, "Remaining days of the selected course are less than the subscription duration"),
  SUBSCRIPION_EXPIRED_BEFORE(-50,HttpServletResponse.SC_NOT_ACCEPTABLE, "Subscription expired before the requested reservation")
  ;
  private final int errorCode;
  private final int httpCode;
  private final String errorMessage;

  Codes(int errorCode, int httpCode, String errorMessage) {
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