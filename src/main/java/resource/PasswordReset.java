package resource;

import java.sql.Timestamp;

public class PasswordReset {
    private final String token;
    private final Timestamp expirationDate;
    private final String person;

    public PasswordReset(String token, Timestamp expirationDate, String person) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.person = person;
    }

    public String getToken() {
        return token;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public String getPerson() {
        return person;
    }

}