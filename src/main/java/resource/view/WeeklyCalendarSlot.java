package resource.view;

import com.google.gson.annotations.Expose;
import resource.LectureTimeSlot;
import resource.Person;

/**
 * Java Bean representing a calendar slot
 * @author Alberto Campeol
 */
public class WeeklyCalendarSlot {
    @Expose
    private Person trainer;
    @Expose
    private Person substituition;
    @Expose
    private LectureTimeSlot lts;

    /**
     * Constructor for this class
     * @param lts  the lecture in the slot
     * @param trainer  the trainer for the lecture
     * @param substituition  the substitute for the trainer
     */
    public WeeklyCalendarSlot(LectureTimeSlot lts, Person trainer, Person substituition) {
        this.lts = lts;
        this.trainer = trainer;
        this.substituition = substituition;
    }

    /**
     * Gets the lecture
     * @return  the lecture
     */
    public LectureTimeSlot getLts() {
        return lts;
    }

    /**
     * Gets the trainer
     * @return  the trainer
     */
    public Person getTrainer() {
        return trainer;
    }

    /**
     * Gets the substitute
     * @return  the substitute
     */
    public Person getSubstituition() {
        return substituition;
    }
}
