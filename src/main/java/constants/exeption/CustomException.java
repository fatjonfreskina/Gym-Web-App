package constants.exeption;

import constants.ErrorCodes;

public class CustomException extends Exception {
  final private ErrorCodes errorCode;

  public CustomException(final ErrorCodes errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public ErrorCodes getErrorCode() {
    return errorCode;
  }
}
