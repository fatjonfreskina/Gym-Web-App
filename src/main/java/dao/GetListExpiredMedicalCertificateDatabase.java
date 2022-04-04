package dao;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
* This DAO is used to show the users who subscribed to a current course,
 * but their medical certificate expired
* @author Alberto Campeol
*  */
public class GetListExpiredMedicalCertificateDatabase
{
    private static final String STATEMENT = "SELECT mc.person, name, surname, address, telephone, taxCode," +
        " birthdate, avatarpath, psw, courseeditionid, coursename, " +
        "startday, date(startday + duration * INTERVAL '1 day') AS finalday, expirationdate " +
        "FROM subscription AS s JOIN medicalcertificate AS mc ON s.trainee = mc.person " +
        "JOIN person AS p ON s.trainee = p.email " +
        "WHERE startday + duration * INTERVAL '1 day' >= NOW() AND expirationdate <= now() " +
        "AND NOT EXISTS (SELECT person FROM medicalcertificate WHERE person = mc.person AND expirationdate > now())";

    private final Connection conn;

    /**
     * Constructor for the GetListExpiredMedicalCertificateDatabase class
     * @param conn  the connection to the database
     */
    public GetListExpiredMedicalCertificateDatabase(final Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Execute the query
     * @return the list of people who have an expired certificate, but they're subscribed to a current course
     */
    public List<Person> execute() throws SQLException
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Person> listPeople = new ArrayList<>();
        try
        {
            pstmt = conn.prepareStatement(STATEMENT);
            rs = pstmt.executeQuery();
            while (rs.next())
                if (rs.getString("avatar_path") == null)
                {/*
                    listPeople.add(new Person((String[]) rs.getArray("role").getArray(),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("address"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("TaxCode"),
                            rs.getDate("Birth_date"),
                            rs.getString("Telephone")
                    ));*/
                }
                else
                {
                    /*listPeople.add(new Person((String[]) rs.getArray("role").getArray(),
                            rs.getString("email"),
                            rs.getString("Path_img"),
                            rs.getString("password"),
                            rs.getString("address"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("TaxCode"),
                            rs.getDate("Birth_date"),
                            rs.getString("Telephone")
                    ));*/
                }
        } finally
        {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            conn.close();
        }
        return listPeople;
    }
}
