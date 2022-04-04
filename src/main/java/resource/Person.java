package resource;

import constants.Constants;

import java.sql.Date;

public class Person {

    public static final int LENGTH_TELEPHONE = 10;
    public static final int MIN_AGE = 14;

    private final String email;
    private final String name;
    private final String surname;
    private final String psw;
    private final String taxcode;
    private final Date birthdate;
    private final String telephone;
    private final String address;
    private final String avatarpath;

    public Person(String email, String name, String surname, String psw, String taxcode, Date birthdate, String telephone, String address, String avatarpath) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.psw = psw;
        this.taxcode = taxcode;
        this.birthdate = birthdate;
        this.telephone = telephone;
        this.address = address;
        this.avatarpath = avatarpath;
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

    public String getTaxcode() {
        return taxcode;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    public String getAvatarpath() {
        return avatarpath;
    }

}
