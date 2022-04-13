package dao.person;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Alberto Campeol
 */
public class DeletePersonRoleDatabase {
    private static final String STATEMENT = "DELETE FROM personroles WHERE person = ?";

    private final Connection conn;
    private final Person p;

    public DeletePersonRoleDatabase(final Connection conn, final Person p) {
        this.conn = conn;
        this.p = p;
    }

    public void execute() throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(STATEMENT)) {
            ps.setString(1, p.getEmail());
            ps.execute();
        } finally {
            conn.close();
        }
    }


}
