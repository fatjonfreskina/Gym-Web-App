package dao.emailconfirmation;

import constants.Constants;
import resource.EmailConfirmation;

import java.sql.*;

public class GetEmailConfirmationByTokenDatabase
{
    private static final String STATEMENT = "SELECT * FROM emailconfirmation WHERE token = ?";
    private final Connection connection;
    private final EmailConfirmation emailConfirmation;

    public GetEmailConfirmationByTokenDatabase(final Connection connection, final EmailConfirmation emailConfirmation)
    {
        this.connection = connection;
        this.emailConfirmation = emailConfirmation;
    }

    public EmailConfirmation execute() throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        EmailConfirmation result = null;
        try
        {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1, emailConfirmation.getToken());
            rs = ps.executeQuery();

            if(rs.next())
                result = new EmailConfirmation(rs.getString(Constants.EMAILCONFIRMATION_PERSON), rs.getString(Constants.EMAILCONFIRMATION_TOKEN),rs.getTimestamp(Constants.EMAILCONFIRMATION_EXPIRATIONDATE));
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
