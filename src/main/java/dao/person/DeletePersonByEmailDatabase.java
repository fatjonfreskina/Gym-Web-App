package dao.person;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This DAO is used to delete a Person from the database
 *
 * @author Francesco Caldivezzi
 */
public class DeletePersonByEmailDatabase {

    /**
     * The DELETE query to be executed
     */
    private static final String STATEMENT = "DELETE FROM person WHERE email=?";

    /**
     * The connection to the database
     */
    private final Connection connection;

    /**
     * The person object to be deleted
     */
    private final Person person;

    /**
     * Parametric constructor
     *
     * @param connection the connection to the database
     * @param person     the person object to be deleted
     */
    public DeletePersonByEmailDatabase(final Connection connection, final Person person) {
        this.connection = connection;
        this.person = person;
    }

    /**
     * Executes the delete query
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
