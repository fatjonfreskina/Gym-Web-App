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

    public final String getToken() {
        return token;
    }

    public final Timestamp getExpirationDate() {
        return expirationDate;
    }

    public final String getPerson() {
        return person;
    }

}