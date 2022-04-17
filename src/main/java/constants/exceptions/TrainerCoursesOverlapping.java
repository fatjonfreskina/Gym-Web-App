package constants.exceptions;

import constants.Codes;

public class TrainerCoursesOverlapping extends CustomException {
  public TrainerCoursesOverlapping() {
    super(Codes.OVERLAPPING_COURSES);
  }
}
