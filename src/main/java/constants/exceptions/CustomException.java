package constants.exceptions;

import constants.Codes;

public class CustomException extends Exception {
  final private Codes code;

  public CustomException(final Codes code) {
    super(code.getErrorMessage());
    this.code = code;
  }

  public Codes getErrorCode() {
    return code;
  }
}
