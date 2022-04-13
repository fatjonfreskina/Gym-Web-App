package constants.exeption;

import constants.ErrorCodes;

public class RoomAlreadyFull extends CustomException {
  public RoomAlreadyFull() {
    super(ErrorCodes.ROOM_ALREADY_FULL);
  }
}
