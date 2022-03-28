package resource;

import java.sql.Timestamp;

public class PasswordReset {
    private String token;
    private Timestamp expirationDate;
    private String person;

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

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}