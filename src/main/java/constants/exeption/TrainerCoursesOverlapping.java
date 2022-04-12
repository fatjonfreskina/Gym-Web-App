package constants.exeption;

import constants.ErrorCodes;

public class TrainerCoursesOverlapping extends Exception {
  public TrainerCoursesOverlapping() {
    super(ErrorCodes.NO_COURSES_TAUGHT.getErrorMessage());
  }
}
