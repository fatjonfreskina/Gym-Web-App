package resource;

import java.sql.Timestamp;

/**
 * @author Francesco Caldivezzi
 */
public class EmailConfirmation {

    private final String person;
    private final String token;
    private Timestamp expirationDate;

    public EmailConfirmation(final String person, final String token, final Timestamp expirationDate) {
        this.person = person;
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public EmailConfirmation(final String person) {
        this.person = person;
        this.token = null;
        this.expirationDate = null;
    }

    public String getPerson() {
        return person;
    }

    public String getToken() {
        return token;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

}
