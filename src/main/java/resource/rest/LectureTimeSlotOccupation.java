package resource.rest;

import resource.LectureTimeSlot;

/**
 * Java Bean representing a lecture occupation
 *
 * @author Riccardo Tumiati
 */
public class LectureTimeSlotOccupation extends LectureTimeSlot {
    private final int totalSlots;
    private final int occupiedSlots;

    /**
     * Constructor for this class
     * @param l  the lecture
     * @param total_slots  the total number of slots
     * @param occupied_slots  the total number of slots already booked
     */
    public LectureTimeSlotOccupation(LectureTimeSlot l, int total_slots, int occupied_slots){
        super(l);
        this.totalSlots = total_slots;
        this.occupiedSlots = occupied_slots;
    }

    /**
     * Gets the total number of slots
     * @return  the total number of slots
     */
    public int getTotalSlots() {
        return totalSlots;
    }

    /**
     * Gets the number of already booked slots
     * @return  the number of already booked slots
     */
    public int getOccupiedSlots() {
        return occupiedSlots;
    }
}
