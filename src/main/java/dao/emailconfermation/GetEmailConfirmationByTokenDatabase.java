package dao.emailconfermation;

import resource.EmailConfermation;
import resource.LectureTimeSlot;
import resource.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetEmailConfirmationByTokenDatabase {
    private static final String STATEMENT = "SELECT * FROM emailconfirmation WHERE token = ?";
    private final Connection connection;
    private final EmailConfermation emailConfermation;

    public GetEmailConfirmationByTokenDatabase(final Connection connection, final EmailConfermation emailConfermation) {
        this.connection = connection;
        this.emailConfermation = emailConfermation;
    }

    public EmailConfermation execute() throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        EmailConfermation result = null;
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1, emailConfermation.getToken());
            rs = ps.executeQuery();

            if(rs.next())
                result = new EmailConfermation(rs.getString("person"),
                        rs.getString("token"),rs.getTimestamp("expirationdate"));
        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            connection.close();
        }
        return result;
    }

}
