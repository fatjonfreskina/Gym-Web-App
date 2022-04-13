package resource.rest;

import resource.LectureTimeSlot;

public class LectureTimeSlotOccupation extends LectureTimeSlot {
    private final int totalSlots;
    private final int occupiedSlots;

    public LectureTimeSlotOccupation(LectureTimeSlot l, int total_slots, int occupied_slots){
        super(l);
        this.totalSlots = total_slots;
        this.occupiedSlots = occupied_slots;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public int getOccupiedSlots() {
        return occupiedSlots;
    }
}
