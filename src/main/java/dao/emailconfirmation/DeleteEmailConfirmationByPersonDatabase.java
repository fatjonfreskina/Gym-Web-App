package dao.emailconfirmation;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Deletes the confirmation email from the database after a person has confirmed the registration
 *
 * @author Francesco Caldicezzi
 */
public class DeleteEmailConfirmationByPersonDatabase {

    /**
     * Query to execute on the database
     */
    private static final String STATEMENT = "DELETE FROM emailconfirmation WHERE person=?";

    /**
     * Database connection
     */
    private final Connection connection;

    /**
     * Person that has confirmed the registration
     */
    private final Person person;

    /**
     * Parametric constructor
     *
     * @param connection database connection
     * @param person     person that has confirmed the registration
     */
    public DeleteEmailConfirmationByPersonDatabase(final Connection connection, final Person person) {
        this.connection = connection;
        this.person = person;
    }

    /**
     * Executes the statement on the database
     *
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public void execute() throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(STATEMENT)) {
            ps.setString(1, person.getEmail());
            ps.execute();
        } finally {
            connection.close();
        }
    }

}
