package dao.person;

import constants.Constants;
import resource.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Riccardo Forzan
 */
public class GetAllPersonsDatabase {

    private static final String STATEMENT = "SELECT * FROM person";
    private final Connection con;

    public GetAllPersonsDatabase(Connection con) {
        this.con = con;
    }

    public List<Person> execute() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Person> list = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            //Execute the query
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(new Person(rs.getString(Constants.PERSON_EMAIL), rs.getString(Constants.PERSON_NAME), rs.getString(Constants.PERSON_SURNAME),
                        rs.getString(Constants.PERSON_PSW), rs.getString(Constants.PERSON_TAXCODE), rs.getDate(Constants.PERSON_BIRTHDATE),
                        rs.getString(Constants.PERSON_TELEPHONE), rs.getString(Constants.PERSON_ADDRESS), rs.getString(Constants.PERSON_AVATARPATH)));
            }
        } finally {
            if (rs != null)
                rs.close();
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }

        return list;
    }

}
