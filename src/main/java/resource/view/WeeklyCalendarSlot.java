package resource.view;

import com.google.gson.annotations.Expose;
import resource.LectureTimeSlot;
import resource.Person;

/**
 * @author Alberto Campeol
 */
public class WeeklyCalendarSlot {
    @Expose
    private Person trainer;
    @Expose
    private Person substituition;
    @Expose
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
