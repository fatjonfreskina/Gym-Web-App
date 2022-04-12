package constants.exeption;

import constants.ErrorCodes;

public class TrainerNoCourseHeld extends Exception {
  public TrainerNoCourseHeld() {
    super(ErrorCodes.NO_COURSES_TAUGHT.getErrorMessage());
  }
}
