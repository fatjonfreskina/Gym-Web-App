package dao.emailconfermation;

import resource.EmailConfermation;

import java.sql.*;

public class InsertEmailConfermationDatabase {

    private static final String STATEMENT = "INSERT INTO emailconfermation(person, token, expirationdate) VALUES (?, ?, ?)";

    private final Connection con;
    private final EmailConfermation emailConfermation;


    public InsertEmailConfermationDatabase(final Connection con, final EmailConfermation emailConfermation) {
        this.con = con;
        this.emailConfermation = emailConfermation;
    }

    public void execute() throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(STATEMENT)) {
            preparedStatement.setString(1, emailConfermation.getPerson());
            preparedStatement.setString(2, emailConfermation.getToken());
            preparedStatement.setTimestamp(3, emailConfermation.getExpirationDate());
            preparedStatement.execute();
        } finally {
            con.close();
        }
    }
}
