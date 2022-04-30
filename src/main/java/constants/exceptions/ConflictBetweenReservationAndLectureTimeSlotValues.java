package constants.exceptions;

import constants.Codes;

/**
 * Exception thrown when there is a conflict between reservation and lecture time slotsù
 *
 * @author Harjot Singh
 */
public class ConflictBetweenReservationAndLectureTimeSlotValues extends CustomException {

    /**
     * Constructs a new instance for this exception
     */
    public ConflictBetweenReservationAndLectureTimeSlotValues() {
        super(Codes.CONFLICT_BETWEEN_RESERVATION_AND_LECTURETIMESLOT_VALUES);
    }

}
