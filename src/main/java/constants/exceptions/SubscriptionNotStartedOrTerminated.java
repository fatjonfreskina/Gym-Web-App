package constants.exceptions;

import constants.Codes;

/**
 * Exception thrown when a subscription is invalid (not started or expired)
 *
 * @author Harjot Singh
 */
public class SubscriptionNotStartedOrTerminated extends CustomException {
    /**
     * Constructs a new instance for this exception
     */
    public SubscriptionNotStartedOrTerminated() {
        super(Codes.SUBSCRIPTION_IS_NOT_CURRENTLY_VALID);
    }
}
