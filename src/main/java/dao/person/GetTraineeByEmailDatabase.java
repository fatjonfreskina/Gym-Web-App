package dao.person;

import constants.Constants;
import resource.LectureTimeSlot;
import resource.Person;
import resource.rest.Trainee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Harjot Singh
 */
public class GetTraineeByEmailDatabase {
  private static final String STATEMENT = """
      SELECT * FROM person, personroles as roles 
      WHERE person.email = ? AND person.email = roles.person AND roles.role = 'trainee';
      """;

  private final Connection connection;
  private final String email;

  public GetTraineeByEmailDatabase(final Connection connection, final String email) {
    this.connection = connection;
    this.email = email;
  }

  public Trainee execute() throws SQLException {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Trainee result = null;
    try {
      ps = connection.prepareStatement(STATEMENT);
      ps.setString(1, email);

      rs = ps.executeQuery();

      if (rs.next()) {
        //String email = rs.getString(Constants.PERSON_EMAIL);
        String name = rs.getString(Constants.PERSON_NAME),
            surname = rs.getString(Constants.PERSON_SURNAME),
            //taxCode = rs.getString(Constants.PERSON_TAXCODE),
            tel = rs.getString(Constants.PERSON_TELEPHONE),
            address = rs.getString(Constants.PERSON_ADDRESS),
            avatar = rs.getString(Constants.PERSON_AVATARPATH);
        Date dob = rs.getDate(Constants.PERSON_BIRTHDATE);
        result = new Trainee(email, name, surname, dob, tel, address, avatar);
      }
    } finally {
      if (ps != null)
        ps.close();
      if (rs != null)
        rs.close();
      connection.close();
    }
    return result;
  }
}