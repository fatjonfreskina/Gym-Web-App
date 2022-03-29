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

    //Constructor with all the attributes
    public Person(int[] role, String email, String path_img, String password, String address, String name, String surname, String taxCode, String birth_date) {
        this.role = role;
        this.email = email;
        Path_img = path_img;
        this.password = password;
        this.address = address;
        this.name = name;
        this.surname = surname;
        TaxCode = taxCode;
        Birth_date = birth_date;
    }

    //Constructor with no Path_img
    public Person(int id, int[] role, String email, String password, String address, String name, String surname, String taxCode, String birth_date) {
        this.role = role;
        this.email = email;
        Path_img = "";
        this.password = password;
        this.address = address;
        this.name = name;
        this.surname = surname;
        TaxCode = taxCode;
        Birth_date = birth_date;
    }

    public String getBirth_date() {
        return Birth_date;
    }

    public String getTaxCode() {
        return TaxCode;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getPath_img() {
        return Path_img;
    }

    public String getEmail() {
        return email;
    }

    public int[] getRole() {
        return role;
    }

}
