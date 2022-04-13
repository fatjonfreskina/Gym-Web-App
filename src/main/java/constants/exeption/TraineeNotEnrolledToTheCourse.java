package constants.exeption;

import constants.Codes;

public class TraineeNotEnrolledToTheCourse extends CustomException {
  public TraineeNotEnrolledToTheCourse() {
    super(Codes.TRAINEE_NOT_ENROLLED_TO_THE_COURSE);
  }
}
