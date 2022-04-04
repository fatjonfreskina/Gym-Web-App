package dao.person;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertRolePersonDatabase {
    private static final String STATEMENT = "INSERT INTO personroles VALUES (?,?)";

    private final Connection conn;
    private final Person p;
    private final String role;

    public InsertRolePersonDatabase(final Connection conn, final Person p, final String role) {
        this.conn = conn;
        this.p = p;
        this.role = role;
    }

    public void execute() throws SQLException {
        PreparedStatement ps = null;
        try
        {
            ps = conn.prepareStatement(STATEMENT);
            ps.setString(1, p.getEmail());
            ps.setString(2, role);
        } finally
        {
            if (ps != null)
                ps.close();
            conn.close();
        }
    }


}
