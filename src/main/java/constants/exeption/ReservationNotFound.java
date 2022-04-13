package constants.exeption;

import constants.ErrorCodes;

public class ReservationNotFound extends CustomException {
  public ReservationNotFound() {
    super(ErrorCodes.RESERVATION_NOT_FOUND);
  }
}
