package constants.exeption;

import constants.Codes;

public class TrainerCoursesOverlapping extends Exception {
  public TrainerCoursesOverlapping() {
    super(Codes.NO_COURSES_TAUGHT.getErrorMessage());
  }
}
