package resource;

import constants.Constants;

import java.sql.Date;

public class Person {

    public static final String[] roles = {"Trainee","Trainer", "Secretary"};

    public static final String ROLE_TRAINEE = "Trainee";
    public static final String ROLE_TRAINER = "Trainer";
    public static final String ROLE_SECRETARY = "Secretary";
    public static final int LENGTH_TELEPHONE = 10;
    public static final int MIN_AGE = 14;

    private final Integer[] role;
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
    public Person(Integer[] role, String email, String pathImg, String password, String address, String name, String surname, String taxCode, Date birthDate, String telephone) {
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
    public Person(Integer[] role, String email, String password, String address, String name, String surname, String taxCode, Date birthDate, String telephone) {
        this(role, email, "", password, address, name, surname, taxCode, birthDate, telephone);
    }
    //Constructor for either only email or taxCode
    public Person(String string, String constant)
    {
        this.role = null;
        this.address = null;
        this.birthDate = null;
        this.password = null;
        this.surname = null;
        this.pathImg = null;
        this.name = null;
        this.telephone = null;
        if(constant.equals(Constants.EMAIL))
        {
            this.email = string;
            this.taxCode = null;
        }else
        {
            this.taxCode = string;
            this.email = null;
        }
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

    public final Integer[] getRole() {
        return role;
    }

    public final String getTelephone() { return telephone; }

}
