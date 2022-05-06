package resource.rest;

import java.sql.Date;
import java.util.Objects;

/**
 * Java Bean representing a trainee
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

  /**
   * Constructor for this class
   * @param email  the email address
   * @param name  the name
   * @param surname  the last name
   * @param birthDate  the birthdate
   * @param telephone  the telephone number
   * @param address  the address
   * @param avatarPath  the path to the avatar
   */
  public Trainee(String email, String name, String surname, Date birthDate, String telephone, String address, String avatarPath) {
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.birthDate = birthDate;
    this.telephone = telephone;
    this.address = address;
    this.avatarPath = avatarPath;
  }

  /**
   * Gets the email address
   * @return  the email address
   */
  public String getEmail() {
    return email;
  }

  /**
   * Gets the name
   * @return  the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the last name
   * @return  the last name
   */
  public String getSurname() {
    return surname;
  }

  /**
   * Gets the birthdate
   * @return  the birthdate
   */
  public Date getBirthDate() {
    return birthDate;
  }

  /**
   * Gets the telephone number
   * @return  the telephone number
   */
  public String getTelephone() {
    return telephone;
  }

  /**
   * Gets the address
   * @return  the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * Gets the path to the avatar
   * @return  the path to the avatar
   */
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
    StringBuilder sb=new StringBuilder();
    sb.append("Trainee{email='" );
    sb.append(email );
    sb.append("', name='" );
    sb.append(name );
    sb.append("', surname='" );
    sb.append(surname );
    sb.append("', birthDate=" );
    sb.append(birthDate );
    sb.append(", telephone='" );
    sb.append(telephone );
    sb.append("', address='" );
    sb.append(address );
    sb.append("', avatarPath='" );
    sb.append(avatarPath );
    sb.append("'}" );
    return sb.toString();
  }
}