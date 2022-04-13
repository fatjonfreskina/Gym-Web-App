package constants.exeption;

import constants.Codes;

public class TrainerNoCourseHeldNow extends CustomException {
  public TrainerNoCourseHeldNow() {
    super(Codes.NO_COURSES_HELD_NOW);
  }
}
