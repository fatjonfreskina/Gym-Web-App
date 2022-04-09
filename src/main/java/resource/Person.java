package resource;

import com.google.gson.annotations.Expose;

import java.sql.Date;
import java.util.Objects;

/**
 * @author Francesco Caldivezzi
 * @author Riccardo Forzan
 * @author Simone D'Antimo
 */
public class Person {
    public static final String ROLE_TRAINEE = "trainee";
    public static final String ROLE_TRAINER = "trainer";
    public static final String ROLE_SECRETARY = "secretary";

    public static final int LENGTH_TELEPHONE = 10;
    public static final int MIN_AGE = 14;

    @Expose
    private final String email;
    @Expose
    private final String name;
    @Expose
    private final String surname;
    private final String psw;
    @Expose
    private final String taxCode;
    @Expose
    private final Date birthDate;
    @Expose
    private final String telephone;
    @Expose
    private final String address;
    @Expose
    private final String avatarPath;

    public Person(String email, String name, String surname, String psw, String taxCode, Date birthDate, String telephone, String address, String avatarPath) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.psw = psw;
        this.taxCode = taxCode;
        this.birthDate = birthDate;
        this.telephone = telephone;
        this.address = address;
        this.avatarPath = avatarPath;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPsw() {
        return psw;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(email, person.email);
    }

    @Override
    public String toString() {
        return "Person{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", psw='" + psw + '\'' +
                ", taxCode='" + taxCode + '\'' +
                ", birthDate=" + birthDate +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                '}';
    }
}