package dao.person;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Francesco Caldivezzi
 */
public class DeleteUserByEmailDatabase {
    private static final String STATEMENT = "DELETE FROM person WHERE email=?";

    private final Connection connection;
    private final Person person;


    public DeleteUserByEmailDatabase(final Connection connection, final Person person) {
        this.connection = connection;
        this.person = person;
    }

    public void execute() throws SQLException {

        try (PreparedStatement ps = connection.prepareStatement(STATEMENT)) {
            ps.setString(1, person.getEmail());
            ps.execute();
        } finally {

            connection.close();
        }
    }
}
