package constants.exceptions;

import constants.Codes;

/**
 * @author Harjot Singh
 */
public class WrongTimeFormat extends CustomException {
  public WrongTimeFormat() {
    super(Codes.WRONG_TIME_FORMAT);
  }
}
