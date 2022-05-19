package dao.passwordreset;

import constants.Constants;
import resource.PasswordReset;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Get all password reset
 * @author Andrea Pasin
 */
public class GetListPasswordReset {

    /**
     * Select statement to execute on the database
     */
    private static final String STATEMENT = """
            SELECT *
            FROM passwordreset
            where expirationdate <= ?
            """;

    /**
     * Connection to the database
     */
    private final Connection con;

    /**
     * Date parameter
     */
    private Timestamp date;

    /**
     * Parametric constructor
     *
     * @param con   database connection
     * @param date       maximum date
     */
    public GetListPasswordReset(final Connection con, Timestamp date) {
        this.con = con;
        this.date = date;
    }

    /**
     * Gets a password reset from the database
     *
     * @return List of PasswordReset object associated with the token string given
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<PasswordReset> execute() throws SQLException {

        List<PasswordReset> item = new ArrayList<>();

        PreparedStatement stm = null;

        stm = con.prepareStatement(STATEMENT);
        stm.setTimestamp(1, date);
        try (ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                final String person = rs.getString(Constants.PASSWORDRESET_PERSON);
                final Timestamp expDate = rs.getTimestamp(Constants.PASSWORDRESET_EXPIRATIONDATE);
                final String token = rs.getString(Constants.PASSWORDRESET_TOKEN);
                item.add(new PasswordReset(token,expDate,person));
            }
        } finally {
            con.close();
        }
        return item;
    }
}