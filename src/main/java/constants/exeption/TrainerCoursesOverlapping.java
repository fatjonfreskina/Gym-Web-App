package constants.exeption;

import constants.Codes;

public class TrainerCoursesOverlapping extends CustomException {
  public TrainerCoursesOverlapping() {
    super(Codes.NO_COURSES_TAUGHT);
  }
}
