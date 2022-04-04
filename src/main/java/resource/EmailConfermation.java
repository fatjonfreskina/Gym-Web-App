package resource;

import java.sql.Timestamp;

public class EmailConfermation {

    private final Person person;
    private final String token;
    private Timestamp expirationDate;

    public EmailConfermation(final Person person, final String token, final Timestamp expirationDate) {
        this.person = person;
        this.token = token;
        this.expirationDate = expirationDate;
    }

    public Person getPerson() {
        return person;
    }

    public String getToken() {
        return token;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

}
