package dao.emailconfermation;

import resource.EmailConfermation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetListEmailConfimationsExpired
{

    private static final String STATEMENT = "select * from emailconfermation where expirationdate <= ?";
    private final Connection connection;
    private Timestamp date;

    public GetListEmailConfimationsExpired(final Connection connection, Timestamp date)
    {
        this.connection = connection;
        this.date = date;
    }

    public List<EmailConfermation> execute() throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<EmailConfermation> result = null;
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setTimestamp(1,date);
            rs = ps.executeQuery();

            result = new ArrayList<>();
            while (rs.next())
                result.add(new EmailConfermation(rs.getString("person"),rs.getString("token"),rs.getTimestamp("expirationdate")));

        } finally
        {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            connection.close();
        }
        return result;
    }

}
