package constants.exceptions;

import constants.Codes;

/**
 * @author Harjot Singh
 */
public class ParsingError extends CustomException {
  public ParsingError() {
    super(Codes.PARSING_ERROR);
  }
}
