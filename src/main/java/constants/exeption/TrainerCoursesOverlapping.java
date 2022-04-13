package constants.exeption;

import constants.ErrorCodes;

public class TrainerCoursesOverlapping extends CustomException {
  public TrainerCoursesOverlapping() {
    super(ErrorCodes.NO_COURSES_TAUGHT);
  }
}
