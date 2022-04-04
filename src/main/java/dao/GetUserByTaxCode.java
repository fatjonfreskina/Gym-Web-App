package dao;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetUserByTaxCode {

    private static final String STATEMENT = "SELECT * FROM person WHERE taxcode = ?;";

    private final Connection con;

    private final Person user;

    public GetUserByTaxCode(final Connection con, final Person user)
    {
        this.con = con;
        this.user = user;
    }

    public Person execute() throws SQLException
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Person ret = null;
        try
        {
            pstmt = con.prepareStatement(STATEMENT);
            //pstmt.setString(1,user.getTaxCode());
            rs = pstmt.executeQuery();

            /*if (rs.next())
                ret = new Person(
                        (String[]) rs.getArray("role").getArray(),
                        rs.getString("email"),
                        rs.getString("avatarpath"),
                        rs.getString("psw"),
                        rs.getString("address"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("TaxCode"),
                        rs.getDate("birthdate"),
                        rs.getString("telephone")
                );*/
        } finally
        {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            con.close();
        }
        return ret;
    }
}
