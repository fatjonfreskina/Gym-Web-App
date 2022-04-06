package dao.person;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Riccardo Forzan
 */
public class UpdateUserDatabase {

    private static final String STATEMENT = "UPDATE person SET name = ?, surname = ?, pws = ?, taxcode = ?, birthdate = ?, telephone = ?, address = ?, avatarpath = ? WHERE email = ?";

    private final Connection conn;
    private final Person p;

    public UpdateUserDatabase(final Connection conn, final Person p) {
        this.conn = conn;
        this.p = p;
    }

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
