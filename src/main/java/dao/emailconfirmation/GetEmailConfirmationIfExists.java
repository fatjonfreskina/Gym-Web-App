package dao.emailconfirmation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.EmailConfirmation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetEmailConfirmationIfExists {

    private static final String STATEMENT = "SELECT * FROM emailconfirmation WHERE person = ?";
    private final Connection connection;
    private final EmailConfirmation emailConfirmation;

    /**
     * Constructor for this class
     *
     * @param connection  the connection to the database
     * @param emailConfirmation  the email of the person to check if exists
     */
    public GetEmailConfirmationIfExists(final Connection connection, final EmailConfirmation emailConfirmation) {
        this.connection = connection;
        this.emailConfirmation = emailConfirmation;
    }

    public EmailConfirmation execute() throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        EmailConfirmation result = null;
        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1, emailConfirmation.getPerson());
            rs = ps.executeQuery();
            if(rs.next())
                result = new EmailConfirmation(rs.getString("person"),
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
