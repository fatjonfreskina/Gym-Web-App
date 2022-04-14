package constants.exeption;

import constants.Codes;

public class SubscriptionNotStartedOrTerminated extends CustomException {
  public SubscriptionNotStartedOrTerminated() {
    super(Codes.SUBSCRIPTION_IS_NOT_CURRENTLY_VALID);
  }
}
