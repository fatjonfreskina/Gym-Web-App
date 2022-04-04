package dao.subscriptiontype;

import resource.SubscriptionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class that can be used to retrieve the list of all possible types of subscription
 * that are present currently in the database as a list of SubscriptionType
 *
 * @author Andrea Pasin
 */
public class GetListSubscriptionTypeDatabase {

    private static final String STATEMENT = "SELECT * FROM subscriptiontype where duration != 7";
    private final Connection con;

    /**
     * Constructor for the GetListSubscriptionTypeDatabase class
     *
     * @param con  the connection to the database
     */
    public GetListSubscriptionTypeDatabase(final Connection con)
    {
        this.con = con;
    }

    /**
     * Execute a select query to retrieve the list of types of subscription from the database
     *
     * @return  the list of SubscriptionType retrieved from the database
     */
    public List<SubscriptionType> execute() throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<SubscriptionType> listSubscriptionTypes = new ArrayList<SubscriptionType>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                listSubscriptionTypes.add(new SubscriptionType(resultSet.getInt("courseeditionid"),
                        resultSet.getString("coursename"),
                        resultSet.getInt("duration"),
                        resultSet.getFloat("cost")));
            }

        }
        finally {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }
        return listSubscriptionTypes;
    }
}
