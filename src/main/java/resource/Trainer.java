package resource;

public class Trainer {
    private final Person p;
    private final Teaches t;

    public Trainer(Person p, Teaches t){
        this.p = p;
        this.t = t;
    }

    public final Person getPerson(){
        return p;
    }

    public final Teaches getTeaches(){
        return t;
    }
}
