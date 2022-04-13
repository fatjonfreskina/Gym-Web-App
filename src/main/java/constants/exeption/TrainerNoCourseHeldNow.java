package constants.exeption;

import constants.ErrorCodes;

public class TrainerNoCourseHeldNow extends CustomException {
  public TrainerNoCourseHeldNow() {
    super(ErrorCodes.NO_COURSES_HELD_NOW);
  }
}
