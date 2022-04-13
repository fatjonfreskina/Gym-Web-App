package constants.exeption;

import constants.ErrorCodes;

public class TrainerNoCourseHeld extends CustomException {
  public TrainerNoCourseHeld() {
    super(ErrorCodes.NO_COURSES_TAUGHT);
  }
}
