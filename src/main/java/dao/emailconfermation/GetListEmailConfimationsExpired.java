package dao.emailconfermation;

import resource.EmailConfermation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetListEmailConfimationsExpired
{

    private static final String STATEMENT = "select * from emailconfermation where expirationdate <= current_t//Get all the usersimestamp;";
    private final Connection connection;

    public GetListEmailConfimationsExpired(final Connection connection)
    {
        this.connection = connection;
    }

    public List<EmailConfermation> execute() throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<EmailConfermation> result = null;
        try {
            ps = connection.prepareStatement(STATEMENT);
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
