package resource;

public class Person {

    private final int[] role;
    private final String email;
    private final String Path_img;
    private final String password;
    private final String address;
    private final String name;
    private final String surname;
    private final String TaxCode;
    private final String Birth_date;
    private final long telephone;

    //Constructor with all the attributes
    public Person(int[] role, String email, String path_img, String password, String address, String name, String surname, String taxCode, String birth_date, long telephone) {
        this.role = role;
        this.email = email;
        this.Path_img = path_img;
        this.password = password;
        this.address = address;
        this.name = name;
        this.surname = surname;
        this.TaxCode = taxCode;
        this.Birth_date = birth_date;
        this.telephone = telephone;
    }

    //Constructor with no Path_img
    public Person(int[] role, String email, String password, String address, String name, String surname, String taxCode, String birth_date, long telephone) {
        this.role = role;
        this.email = email;
        this.Path_img = "";
        this.password = password;
        this.address = address;
        this.name = name;
        this.surname = surname;
        this.TaxCode = taxCode;
        this.Birth_date = birth_date;
        this.telephone = telephone;
    }

    public final String getBirth_date() {
        return Birth_date;
    }

    public final String getTaxCode() {
        return TaxCode;
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

    public final String getPath_img() {
        return Path_img;
    }

    public final String getEmail() {
        return email;
    }

    public final int[] getRole() {
        return role;
    }

    public final long getTelephone() {return telephone;}

}
