package resource;

public class Person {

    private final int ID;
    private int[] role;
    private int email;
    private String Path_img;
    private String password;
    private String address;
    private String name;
    private String surname;
    private final String TaxCode;
    private String Birth_date;

    //Constructor with all the attributes
    public Person(int id, int[] role, int email, String path_img, String password, String address, String name, String surname, String taxCode, String birth_date) {

        ID = id;
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
    public Person(int id, int[] role, int email, String password, String address, String name, String surname, String taxCode, String birth_date) {

        ID = id;
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

    public int getEmail() {
        return email;
    }

    public int[] getRole() {
        return role;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name){
        this.name = name;
    }
    
    public void setRole(int[] role) {
        this.role = role;
    }

    public void setEmail(int email) {
        this.email = email;
    }

    public void setPath_img(String path_img) {
        Path_img = path_img;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirth_date(String birth_date) {
        Birth_date = birth_date;
    }
}
