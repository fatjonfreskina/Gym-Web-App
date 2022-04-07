package resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Riccardo Tumiati
 */
public class Trainer {
    private final Person person;
    private final List<Teaches> teaches;

    public Trainer(Person person){
        this.person = person;
        this.teaches = new ArrayList<>();
    }

    public Trainer(Person person, Teaches teaches){
        this.person = person;
        this.teaches = new ArrayList<>();
        this.teaches.add(teaches);
    }

    public void addTeaches(Teaches t){
        teaches.add(t);
    }

    public final Person getPerson(){
        return person;
    }

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
