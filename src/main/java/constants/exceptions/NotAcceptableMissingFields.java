package constants.exceptions;

import constants.Codes;

public class NotAcceptableMissingFields extends CustomException {
  public NotAcceptableMissingFields() {
    super(Codes.NOT_ACCEPTABLE_MISSING_FIELDS);
  }
}
