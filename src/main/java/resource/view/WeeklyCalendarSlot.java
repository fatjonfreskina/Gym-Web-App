package resource.view;

import resource.LectureTimeSlot;
import resource.Person;

/**
 * @author Alberto Campeol
 */
public class WeeklyCalendarSlot {
    private Person trainer;
    private Person substituition;
    private LectureTimeSlot lts;

    public WeeklyCalendarSlot(LectureTimeSlot lts, Person trainer, Person substituition) {
        this.lts = lts;
        this.trainer = trainer;
        this.substituition = substituition;
    }

    public LectureTimeSlot getLts() {
        return lts;
    }

    public Person getTrainer() {
        return trainer;
    }

    public Person getSubstituition() {
        return substituition;
    }
}
