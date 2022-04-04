package dao.emailconfermation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LectureTimeSlot;
import resource.Person;

import java.sql.*;

public class DeleteEmailConfirmationByPersonDatabase
{
    private static final String STATEMENT = "DELETE FROM emailconfirmation WHERE person=?";

    private final Connection connection;
    private final Person person;


    public DeleteEmailConfirmationByPersonDatabase(final Connection connection, final Person person)
    {
        this.connection = connection;
        this.person = person;
    }

    public void execute() throws SQLException
    {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(STATEMENT);
            ps.setString(1, person.getEmail());
            ps.execute();
        }
        finally
        {

            if (ps != null)
                ps.close();
            connection.close();
        }
    }

}
