package resource;

import com.google.gson.annotations.Expose;

import java.sql.Date;
import java.util.Objects;

/**
 * Java Bean used to represent a person
 *
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
    public static final int ADULT_AGE = 18;

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

    /**
     * Constructor for this class
     *
     * @param email      the person's email
     * @param name       the person's name
     * @param surname    the person's surname
     * @param psw        the person's password
     * @param taxCode    the person's tax code
     * @param birthDate  the person's birthdate
     * @param telephone  the person's telephone
     * @param address    the person's address
     * @param avatarPath the person's avatar path
     */
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

    /**
     * Constructor for this class
     *
     * @param email the person's email
     */
    public Person(String email) {
        this.email = email;
        this.name = null;
        this.surname = null;
        this.psw = null;
        this.taxCode = null;
        this.birthDate = null;
        this.telephone = null;
        this.address = null;
        this.avatarPath = null;
    }

    /**
     * Gets the person's email
     *
     * @return the person's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the person's name
     *
     * @return the person's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the person's surname
     *
     * @return the person's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Gets the person's password
     *
     * @return the person's password
     */
    public String getPsw() {
        return psw;
    }

    /**
     * Gets the person's tax code
     *
     * @return the person's tax code
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * Gets the person's birthdate
     *
     * @return the person's birthdate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Gets the person's telephone number
     *
     * @return the person's telephone number
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Gets the person's address
     *
     * @return the person's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the person's avatar path
     *
     * @return the person's avatar path
     */
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
    //TODO: Refactor this method
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