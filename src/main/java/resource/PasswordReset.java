package resource;

import java.sql.Timestamp;

/**
 * Java Bean used to represent a password reset
 *
 * @author Marco Alessio
 * @author Riccardo Forzan
 */
public class PasswordReset {

    private final String token;
    private final Timestamp expirationDate;
    private final String person;

    /**
     * Constructor for this class
     *
     * @param token          the token associated to this password reset
     * @param expirationDate the expiration date of this password reset
     * @param person         the person who requested the password reset
     */
    public PasswordReset(String token, Timestamp expirationDate, String person) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.person = person;
    }

    /**
     * Gets the token
     *
     * @return the token
     */
    public final String getToken() {
        return token;
    }

    /**
     * Gets the expiration date
     *
     * @return the expiration date
     */
    public final Timestamp getExpirationDate() {
        return expirationDate;
    }

    /**
     * Gets the person
     *
     * @return the person
     */
    public final String getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return String.format("PasswordReset{token='%s', expirationDate='%s', person='%s'}", token, expirationDate, person);
    }
}