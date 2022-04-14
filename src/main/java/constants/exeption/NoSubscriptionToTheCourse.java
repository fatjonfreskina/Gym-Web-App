package constants.exeption;

import constants.Codes;

public class NoSubscriptionToTheCourse extends CustomException {
  public NoSubscriptionToTheCourse() {
    super(Codes.NO_SUBSCRIPTION_TO_THE_COURSE);
  }
}
