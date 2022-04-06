package dao.emailconfirmation;

import resource.EmailConfirmation;

import java.sql.*;

public class InsertEmailConfirmationDatabase {

    private static final String STATEMENT = "INSERT INTO emailconfirmation(person, token, expirationdate) VALUES (?, ?, ?)";

    private final Connection con;
    private final EmailConfirmation emailConfirmation;


    public InsertEmailConfirmationDatabase(final Connection con, final EmailConfirmation emailConfirmation) {
        this.con = con;
        this.emailConfirmation = emailConfirmation;
    }

    public void execute() throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(STATEMENT)) {
            preparedStatement.setString(1, emailConfirmation.getPerson());
            preparedStatement.setString(2, emailConfirmation.getToken());
            preparedStatement.setTimestamp(3, emailConfirmation.getExpirationDate());
            preparedStatement.execute();
        } finally {
            con.close();
        }
    }
}
