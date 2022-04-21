package resource.rest;

import java.sql.Date;
import java.util.Objects;

/**
 * @author Harjot Singh
 */
public class Trainee {
  private final String email;
  private final String name;
  private final String surname;
  private final Date birthDate;
  private final String telephone;
  private final String address;
  private final String avatarPath;

  public Trainee(String email, String name, String surname, Date birthDate, String telephone, String address, String avatarPath) {
    this.email = email;
    this.name = name;
    this.surname = surname;
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
    Trainee trainee = (Trainee) o;
    return Objects.equals(email, trainee.email) && Objects.equals(name, trainee.name) && Objects.equals(surname, trainee.surname) && Objects.equals(birthDate, trainee.birthDate) && Objects.equals(telephone, trainee.telephone) && Objects.equals(address, trainee.address) && Objects.equals(avatarPath, trainee.avatarPath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, name, surname, birthDate, telephone, address, avatarPath);
  }

  @Override
  public String toString() {
    return "Trainee{" +
        "email='" + email + '\'' +
        ", name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", birthDate=" + birthDate +
        ", telephone='" + telephone + '\'' +
        ", address='" + address + '\'' +
        ", avatarPath='" + avatarPath + '\'' +
        '}';
  }
}