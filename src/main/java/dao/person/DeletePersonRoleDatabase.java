package dao.person;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This DAO deletes the role of a person from the database
 *
 * @author Alberto Campeol
 */
public class DeletePersonRoleDatabase {

    /**
     * The DELETE query to be executed
     */
    private static final String STATEMENT = "DELETE FROM personroles WHERE person = ?";

    /**
     * The connection to the database
     */
    private final Connection conn;

    /**
     * The person object whose role needs to be deleted
     */
    private final Person p;

    /**
     * Parametric constructor
     *
     * @param conn the connection to the database
     * @param p    the person object whose role needs to be deleted
     */
    public DeletePersonRoleDatabase(final Connection conn, final Person p) {
        this.conn = conn;
        this.p = p;
    }

    /**
     * Executes the delete query
     *
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public void execute() throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(STATEMENT)) {
            ps.setString(1, p.getEmail());
            ps.execute();
        } finally {
            conn.close();
        }
    }


}
