package dao.emailconfermation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.EmailConfermation;
import resource.MedicalCertificate;
import resource.Person;

import java.sql.*;

public class InsertEmailConfermation {

    private static final String STATEMENT = "INSERT INTO emailconfermation(person, token, expirationday) VALUES (?, ?, ?)";

    private final Connection con;
    private final EmailConfermation emailConfermation;


    public InsertEmailConfermation (final Connection con, final EmailConfermation emailConfermation)
    {
        this.con = con;
        this.emailConfermation = emailConfermation;
    }

    public void execute() throws SQLException
    {
        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1, emailConfermation.getPerson().getEmail());
            preparedStatement.setString(2, emailConfermation.getToken());
            preparedStatement.setTimestamp(3, emailConfermation.getExpirationDate());
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
