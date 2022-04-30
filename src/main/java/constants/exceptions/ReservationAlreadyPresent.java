package constants.exceptions;

import constants.Codes;

/**
 * Exception thrown when a reservation has already been booked
 *
 * @author Harjot Singh
 */
public class ReservationAlreadyPresent extends CustomException {
    /**
     * Constructs a new instance for this exception
     */
    public ReservationAlreadyPresent() {
        super(Codes.RESERVATION_ALREADY_PRESENT);
    }
}
