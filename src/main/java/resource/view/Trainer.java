package resource.view;

import resource.Person;
import resource.Teaches;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Java Bean representing a trainer
 * @author Riccardo Tumiati
 */
public class Trainer {
    private final Person person;
    private final List<Teaches> teaches;

    /**
     * Constructor for this class
     * @param person  the trainer
     */
    public Trainer(Person person){
        this.person = person;
        this.teaches = new ArrayList<>();
    }
    /**
     * Constructor for this class
     * @param person  the trainer
     * @param teaches  the courses taught by the trainer
     */
    public Trainer(Person person, Teaches teaches){
        this.person = person;
        this.teaches = new ArrayList<>();
        this.teaches.add(teaches);
    }

    /**
     * Adds a taught course
     * @param t  the taught course
     */
    public void addTeaches(Teaches t){
        teaches.add(t);
    }

    /**
     * Gets the trainer
     * @return  the trainer
     */
    public final Person getPerson(){
        return person;
    }

    /**
     * Gets the courses taught
     * @return  the courses taught
     */
    public final List<Teaches> getTeaches(){
        return teaches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return Objects.equals(person, trainer.person);
    }
}
