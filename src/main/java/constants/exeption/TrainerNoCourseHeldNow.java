package constants.exeption;

import constants.Codes;

public class TrainerNoCourseHeldNow extends Exception {
  public TrainerNoCourseHeldNow() {
    super(Codes.NO_COURSES_HELD_NOW.getErrorMessage());
  }
}
