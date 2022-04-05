package resource;

public class Trainer {
    private final Person person;
    private final Teaches teaches;

    public Trainer(Person person, Teaches teaches){
        this.person = person;
        this.teaches = teaches;
    }

    public final Person getPerson(){
        return person;
    }

    public final Teaches getTeaches(){
        return teaches;
    }
}
