package dao.person;


import constants.Constants;
import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This DAO is used to GET the list of users by searching for a pattern in the email
 *
 * @author Francesco Caldivezzi
 * */
public class GetListPersonByLikeEmailDatabase {

    /**
     * The SELECT query to be executed
     */
    private static final String STATEMENT = "SELECT * from person where email like ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The String pattern to be passed to the query
     */
    private final String like;

    /**
     *
     * Parametric constructor
     *
     * @param con the connection to the database
     * @param like the String pattern to be passed to the query
     */
    public GetListPersonByLikeEmailDatabase(Connection con,String like)
    {
        this.con = con;
        this.like = like;
    }

    /**
     *
     * Executes the SELECT query
     *
     * @return a list containing Person objects that matched the query
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<Person> execute() throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Person> list = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1,"%"+like+"%");

            //Execute the query

            rs = preparedStatement.executeQuery();
            while (rs.next())
            {
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
