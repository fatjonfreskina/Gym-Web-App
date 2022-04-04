package dao;

import resource.Person;

import java.sql.*;

public class InsertNewUserDatabase {
    private static final String STATEMENT = "INSERT INTO person(email, ARRAY['%s']::roles[], name, surname, psw, taxcode, birthdate, telephone, address, avatarpath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final Connection conn;
    private final Person p;

    public InsertNewUserDatabase(final Connection conn, final Person p) {
        this.conn = conn;
        this.p = p;
    }

    public void execute() throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(STATEMENT);
            ps.setString(1, p.getEmail());
            //TODO: check if the type of the array is correct.
            //Array role = conn.createArrayOf("\"gwa\".\"role\"", p.getRole());
            //ps.setString(2, Person.roles[p.getRole()[0]]);
            ps.setString(3, p.getName());
            ps.setString(4, p.getSurname());
            ps.setString(5, p.getPassword());
            ps.setString(6, p.getTaxCode());
            ps.setDate(7, p.getBirthDate());
            ps.setString(8, p.getTelephone());
            ps.setString(9, p.getAddress());
            ps.setString(10, p.getPathImg());

            ps.execute();
        } finally {
            if (ps != null)
                ps.close();
            conn.close();
        }
    }
}
