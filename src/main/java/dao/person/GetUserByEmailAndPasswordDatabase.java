package dao.person;
import constants.Constants;
import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetUserByEmailAndPasswordDatabase {
    private static final String STATEMENT = """
    SELECT * FROM person
    WHERE email = ? 
    AND  psw = ?;
    """;
    private final Connection connection;
    private final Person person;


    public GetUserByEmailAndPasswordDatabase(final Connection connection, final Person person) {
        this.connection = connection;
        this.person = person;
    }

    public Person execute() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        final List<Person> result = new ArrayList<>();

        try {
            pstmt = connection.prepareStatement(STATEMENT);
            pstmt.setString(1, person.getEmail());
            pstmt.setString(2, person.getPsw());
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                result.add(new Person(
                                rs.getString(Constants.PERSON_EMAIL),
                                rs.getString(Constants.PERSON_NAME),
                                rs.getString(Constants.PERSON_SURNAME),
                                rs.getString(Constants.PERSON_PSW), //password
                                rs.getString(Constants.PERSON_TAXCODE),
                                rs.getDate(Constants.PERSON_BIRTHDATE),
                                rs.getString(Constants.PERSON_TELEPHONE),
                                rs.getString(Constants.PERSON_ADDRESS),
                                rs.getString(Constants.PERSON_AVATARPATH)));
            }
        } finally {
            if (rs != null)
            {
                rs.close();
            }
            if (pstmt != null)
            {
                pstmt.close();
            }
            connection.close();
        }
        if(result.size()!=1){
            return null;
        }else
            return result.get(0);
    }
}
