package resource;

import java.sql.Timestamp;

/**
 * Java Bean used to represent an email confirmation
 *
 * @author Francesco Caldivezzi
 */
public class EmailConfirmation {

    private final String person;
    private final String token;
    private Timestamp expirationDate;

    /**
     * The constructor for this class
     * @param person  the person associated to this email confirmation
     * @param token  the token associated to this email confirmation
     * @param expirationDate  the expiration date
     */
    public EmailConfirmation(final String person, final String token, final Timestamp expirationDate) {
        this.person = person;
        this.token = token;
        this.expirationDate = expirationDate;
    }
    /**
     * The constructor for this class
     * @param person  the person associated to this email confirmation
     */
    public EmailConfirmation(final String person) {
        this.person = person;
        this.token = null;
        this.expirationDate = null;
    }

    /**
     * Gets the person
     * @return  the person
     */
    public String getPerson() {
        return person;
    }

    /**
     * Gets the token
     * @return  the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the expiration date
     * @return  the expiration date
     */
    public Timestamp getExpirationDate() {
        return expirationDate;
    }

}
