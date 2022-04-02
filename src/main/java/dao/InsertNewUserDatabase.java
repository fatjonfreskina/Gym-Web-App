package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;
import resource.Person;

import java.sql.*;

public class InsertNewUserDatabase {
    private final String STATEMENT = "INSERT INTO person(email, role, name, surname, psw, taxcode, birthdate, telephone, address, avatarpath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final Connection conn;
    private final Person p;

    public InsertNewUserDatabase(Connection conn, Person p) {
        this.conn = conn;
        this.p = p;
    }

    public void insertNewUser() throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(STATEMENT);
            ps.setString(1, p.getEmail());
            Array role = conn.createArrayOf("integer", p.getRole());
            ps.setArray(2, role);
            ps.setString(3, p.getName());
            ps.setString(4, p.getSurname());
            ps.setString(5, p.getPassword());
            ps.setString(6, p.getTaxCode());
            ps.setDate(7, p.getBirth_date());
            ps.setString(8, p.getTelephone());
            ps.setString(9, p.getAddress());
            ps.setString(10, p.getPath_img());

            ps.execute();
        } finally {
            if (ps != null)
                ps.close();
            conn.close();
        }
    }
}
