package constants.exeption;

import constants.ErrorCodes;

public class ReservationAlreadyPresent extends CustomException {
  public ReservationAlreadyPresent() {
    super(ErrorCodes.RESERVATION_ALREADY_PRESENT);
  }
}
