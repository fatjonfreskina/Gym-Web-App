package constants.exeption;

import constants.Codes;

public class TrainerNoCourseHeld extends CustomException {
  public TrainerNoCourseHeld() {
    super(Codes.NO_COURSES_TAUGHT.getErrorMessage());
  }
}
