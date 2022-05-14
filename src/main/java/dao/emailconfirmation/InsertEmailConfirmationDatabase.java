package dao.emailconfirmation;

import resource.EmailConfirmation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Inserts a new EmailConfirmation
 *
 * @author Alberto Campeol
 */
public class InsertEmailConfirmationDatabase {

    /**
     * Query to execute on the database
     */
    private static final String STATEMENT = "INSERT INTO emailconfirmation(person, token, expirationdate) VALUES (?, ?, ?)";

    /**
     * Database connection
     */
    private final Connection con;

    /**
     * EmailConfirmation object to insert in the database
     */
    private final EmailConfirmation emailConfirmation;

    /**
     * Parametric constructor
     *
     * @param con               database connection
     * @param emailConfirmation EmailConfirmation object to insert
     */
    public InsertEmailConfirmationDatabase(final Connection con, final EmailConfirmation emailConfirmation) {
        this.con = con;
        this.emailConfirmation = emailConfirmation;
    }

    /**
     * Executes the statement to insert the object in the database
     *
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
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
