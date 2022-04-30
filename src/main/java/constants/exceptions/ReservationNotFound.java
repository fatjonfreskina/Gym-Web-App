package constants.exceptions;

import constants.Codes;

/**
 * Exception thrown when a reservation has not been found in the database
 *
 * @author Harjot Singh
 */
public class ReservationNotFound extends CustomException {
    /**
     * Constructs a new instance for this exception
     */
    public ReservationNotFound() {
        super(Codes.RESERVATION_NOT_FOUND);
    }
}
