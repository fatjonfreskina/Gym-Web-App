package dao.person;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Associates a role to a person
 *
 * @author Francesco Caldivezzi
 */
public class InsertPersonRoleDatabase {

    /**
     * Query to be executed
     */
    private static final String STATEMENT = "INSERT INTO personroles VALUES (?,?)";

    /**
     * Database connection
     */
    private final Connection conn;

    /**
     * Person to which the role must be associated
     */
    private final Person p;

    /**
     * Role to associate to the person
     */
    private final String role;

    /**
     * Parametric constructor
     *
     * @param conn database connection
     * @param p    person to which the role must be associated
     * @param role role to associate to the person
     */
    public InsertPersonRoleDatabase(final Connection conn, final Person p, final String role) {
        this.conn = conn;
        this.p = p;
        this.role = role;
    }

    /**
     * Executes the query
     *
     * @throws SQLException thrown if something goes wrong while querying the database
     */
    public void execute() throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(STATEMENT)) {
            ps.setString(1, p.getEmail());
            ps.setString(2, role);
            ps.execute();
        } finally {
            conn.close();
        }
    }

}
