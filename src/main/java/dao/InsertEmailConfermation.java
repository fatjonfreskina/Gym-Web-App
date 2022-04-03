package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.MedicalCertificate;
import resource.Person;

import java.sql.*;

public class InsertEmailConfermation {

    private static final String STATEMENT = "INSERT INTO gwa.emailconfermation(person,token, expirationday) VALUES (?, ?, ?)";

    private final Connection con;

    private final Person person;
    private final String token;
    private final Date expirationDate;


    public InsertEmailConfermation (final Connection con, EmailConfermation)
    {
        this.con = con;
        this.token = token;
        this.expirationDate = expirationDate;
        this.person = person;
    }

    public void execute() throws SQLException
    {
        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1, person.getEmail());
            preparedStatement.setString(2, token);
            preparedStatement.setDate(3, expirationDate);

            preparedStatement.execute();

        }
        finally
        {
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }
    }
}
