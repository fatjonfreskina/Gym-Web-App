package resource.view;

import resource.Person;
import resource.Subscription;

import java.util.Date;

/**
 * Java Bean representing a valid subscription
 *
 * @author Riccardo Tumiati
 */
public class ValidSubscription {
    private final Subscription subscription;
    private final Person trainer;
    private final Date expiration;

    /**
     * Constructor for this class
     * @param subscription  the subscription
     * @param trainer  the trainer
     * @param expiration  the expiration date
     */
    public ValidSubscription(Subscription subscription, Person trainer, Date expiration){
        this.subscription = subscription;
        this.trainer = trainer;
        this.expiration = expiration;
    }

    /**
     * Gets the expiration date
     * @return  the expiration date
     */
    public Date getExpiration(){
        return expiration;
    }

    /**
     * Gets the subscription
     * @return  the subscription
     */
    public Subscription getSubscription(){
        return subscription;
    }

    /**
     * Gets the trainer
     * @return  the trainer
     */
    public Person getTrainer(){
        return trainer;
    }

}
