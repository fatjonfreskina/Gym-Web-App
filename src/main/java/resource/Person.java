package resource;

import java.sql.Date;

public class Person {

    public static final String ROLE_TRAINEE = "Trainee";
    public static final String ROLE_TRAINER = "Trainer";
    public static final String ROLE_SECRETARY = "Secretary";

    private final String[] role;
    private final String email;
    private final String pathImg;
    private final String password;
    private final String address;
    private final String name;
    private final String surname;
    private final String taxCode;
    private final Date birthDate;
    private final String telephone;

    //Constructor with all the attributes
    public Person(String[] role, String email, String pathImg, String password, String address, String name, String surname, String taxCode, Date birthDate, String telephone) {
        this.role = role;
        this.email = email;
        this.pathImg = pathImg;
        this.password = password;
        this.address = address;
        this.name = name;
        this.surname = surname;
        this.taxCode = taxCode;
        this.birthDate = birthDate;
        this.telephone = telephone;
    }

    //Constructor with no Path_img
    public Person(String[] role, String email, String password, String address, String name, String surname, String taxCode, Date birthDate, String telephone) {
        this(role, email, "", password, address, name, surname, taxCode, birthDate, telephone);
    }

    public final Date getBirthDate() {
        return birthDate;
    }

    public final String getTaxCode() {
        return taxCode;
    }

    public final String getSurname() {
        return surname;
    }

    public final String getName() {
        return name;
    }

    public final String getAddress() {
        return address;
    }

    public final String getPassword() {
        return password;
    }

    public final String getPathImg() {
        return pathImg;
    }

    public final String getEmail() {
        return email;
    }

    public final String[] getRole() {
        return role;
    }

    public final String getTelephone() { return telephone; }

}
