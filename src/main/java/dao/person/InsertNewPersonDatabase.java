package dao.person;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Inserts a new person in the database
 *
 * @author Riccardo Forzan
 */
public class InsertNewPersonDatabase {

    /**
     * Query to be executed
     */
    private static final String STATEMENT = "INSERT INTO person VALUES (?,?,?,?,?,?,?,?,?)";

    /**
     * Database connection
     */
    private final Connection conn;

    /**
     * Person to insert in the database
     */
    private final Person p;

    /**
     * Parametric constructor
     *
     * @param conn database connection
     * @param p    person to insert in the database
     */
    public InsertNewPersonDatabase(final Connection conn, final Person p) {
        this.conn = conn;
        this.p = p;
    }

    /**
     * Executes the query
     *
     * @throws SQLException thrown if something goes wrong while querying the database
     */
    public void execute() throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(STATEMENT)) {
            ps.setString(1, p.getEmail());
            ps.setString(2, p.getName());
            ps.setString(3, p.getSurname());
            ps.setString(4, p.getPsw());
            ps.setString(5, p.getTaxCode());
            ps.setDate(6, p.getBirthDate());
            ps.setString(7, p.getTelephone());
            ps.setString(8, p.getAddress());
            ps.setString(9, p.getAvatarPath());
            ps.execute();
        } finally {
            conn.close();
        }
    }
}
