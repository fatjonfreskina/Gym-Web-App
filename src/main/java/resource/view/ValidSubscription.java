package resource.view;

import resource.Person;
import resource.Subscription;

import java.util.Date;

public class ValidSubscription {
    private final Subscription subscription;
    private final Person trainer;
    private final Date expiration;

    public ValidSubscription(Subscription subscription, Person trainer, Date expiration){
        this.subscription = subscription;
        this.trainer = trainer;
        this.expiration = expiration;
    }

    public Date getExpiration(){
        return expiration;
    }

    public Subscription getSubscription(){
        return subscription;
    }

    public Person getTrainer(){
        return trainer;
    }

}
