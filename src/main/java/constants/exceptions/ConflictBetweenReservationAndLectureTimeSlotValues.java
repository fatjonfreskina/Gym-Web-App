package constants.exceptions;

import constants.Codes;

/**
 * @author Harjot Singh
 */
public class ConflictBetweenReservationAndLectureTimeSlotValues extends CustomException {
  public ConflictBetweenReservationAndLectureTimeSlotValues() {
    super(Codes.CONFLICT_BETWEEN_RESERVATION_AND_LECTURETIMESLOT_VALUES);
  }
}
