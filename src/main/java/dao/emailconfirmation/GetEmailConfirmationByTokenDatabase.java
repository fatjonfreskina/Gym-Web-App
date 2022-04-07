package dao.emailconfirmation;

import resource.EmailConfirmation;

import java.sql.*;

/**
 * @author Francesco Caldivezzi
 */
public class GetEmailConfirmationByTokenDatabase {
    private static final String STATEMENT = "SELECT * FROM emailconfirmation WHERE token = ?";
    private final Connection connection;
    private final EmailConfirmation emailConfirmation;

    public GetEmailConfirmationByTokenDatabase(final Connection connection, final EmailConfirmation emailConfirmation) {
        this.connection = connection;
        this.emailConfirmation = emailConfirmation;
    }

    public EmailConfirmation execute() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        EmailConfirmation result = null;
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1, emailConfirmation.getToken());
            rs = ps.executeQuery();

            if (rs.next())
                result = new EmailConfirmation(rs.getString("person"),
                        rs.getString("token"), rs.getTimestamp("expirationdate"));
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
