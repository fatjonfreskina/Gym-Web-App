package dao.person;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Riccardo Forzan
 */
public class UpdatePersonDatabase {

    /**
     * Query to be executed
     */
    private static final String STATEMENT = "UPDATE person SET name = ?, surname = ?, psw = ?, taxcode = ?, birthdate = ?, telephone = ?, address = ?, avatarpath = ? WHERE email = ?";

    /**
     * Database connection
     */
    private final Connection conn;

    /**
     * Person object to update
     */
    private final Person p;

    /**
     * Parametric constructor
     *
     * @param conn database connection
     * @param p    person to update in the database (with its new values)
     */
    public UpdatePersonDatabase(final Connection conn, final Person p) {
        this.conn = conn;
        this.p = p;
    }

    /**
     * Inserts this object into the database
     *
     * @throws SQLException if a database access error occurs
     */
    public void execute() throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(STATEMENT)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getSurname());
            ps.setString(3, p.getPsw());
            ps.setString(4, p.getTaxCode());
            ps.setDate(5, p.getBirthDate());
            ps.setString(6, p.getTelephone());
            ps.setString(7, p.getAddress());
            ps.setString(8, p.getAvatarPath());
            ps.setString(9, p.getEmail());
            ps.execute();
        } finally {
            conn.close();
        }
    }

}
