package constants.exeption;

import constants.Codes;

public class ReservationAlreadyPresent extends CustomException {
  public ReservationAlreadyPresent() {
    super(Codes.RESERVATION_ALREADY_PRESENT);
  }
}
