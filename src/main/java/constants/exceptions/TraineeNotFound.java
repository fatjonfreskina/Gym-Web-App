package constants.exceptions;

import constants.Codes;

public class TraineeNotFound extends CustomException {
  // TODO CHANGE
  public TraineeNotFound() {
    super(Codes.EMAIL_NOT_FOUND);
  }
}
