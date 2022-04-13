package constants.exeption;

import constants.ErrorCodes;

public class TraineeNotEnrolledToTheCourse extends CustomException {
  public TraineeNotEnrolledToTheCourse() {
    super(ErrorCodes.TRAINEE_NOT_ENROLLED_TO_THE_COURSE);
  }
}
