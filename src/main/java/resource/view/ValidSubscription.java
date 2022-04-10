package resource.view;

import resource.Person;
import resource.Subscription;

import java.util.List;

public class ValidSubscription {
    private final Subscription sub;
    private final List<Person> trainers;

    public ValidSubscription(Subscription sub, List<Person> trainers){
        this.sub = sub;
        this.trainers = trainers;
    }

    public Subscription getSub(){
        return sub;
    }

    public List<Person> trainers(){
        return trainers;
    }

}
