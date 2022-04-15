package constants.exceptions;

import constants.Codes;

public class WrongDateOrTimeFormat extends CustomException {
  public WrongDateOrTimeFormat() {
    super(Codes.WRONG_DATE_OR_TIME_FORMAT);
  }
}
