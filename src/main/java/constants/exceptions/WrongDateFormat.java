package constants.exceptions;

import constants.Codes;

/**
 * @author Harjot Singh
 */
public class WrongDateFormat extends CustomException {
  public WrongDateFormat() {
    super(Codes.WRONG_DATE_FORMAT);
  }
}
