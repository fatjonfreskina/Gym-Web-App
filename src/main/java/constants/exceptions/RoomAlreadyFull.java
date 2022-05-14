package constants.exceptions;

import constants.Codes;

/**
 * Exception thrown when a room has no more slots available
 *
 * @author Harjot Singh
 */
public class RoomAlreadyFull extends CustomException {
    /**
     * Constructs a new instance for this exception
     */
    public RoomAlreadyFull() {
        super(Codes.ROOM_ALREADY_FULL);
    }
}
