package dao.subscriptiontype;

import constants.Constants;
import resource.SubscriptionType;
import resource.view.PricesView;

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
public class GetListForPrices {

    private static final String STATEMENT = """
            SELECT courseeditionid,coursename,duration,T1.cost, max(date) as maxdate ,min(date) as mindate
            FROM
            (SELECT coursename,courseeditionid,duration,date, subscriptiontype.cost
            FROM subscriptiontype natural join lecturetimeslot
            where duration != 7 and date >= current_date
            group by (coursename,courseeditionid,duration,date,subscriptiontype.cost)) AS T1\s
            group by coursename,courseeditionid,duration,T1.cost
            """;
    private final Connection con;

    /**
     * Constructor for the GetListSubscriptionTypeDatabase class
     *
     * @param con the connection to the database
     */
    public GetListForPrices(final Connection con) {
        this.con = con;
    }

    /**
     * Execute a select query to retrieve the list of types of subscription from the database
     *
     * @return the list of SubscriptionType retrieved from the database
     */
    public List<PricesView> execute() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PricesView> list = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new PricesView(resultSet.getInt("courseeditionid"),resultSet.getString("coursename"),
                        resultSet.getInt("duration"),
                        resultSet.getFloat("cost"),
                        resultSet.getDate("maxdate"),
                        resultSet.getDate("mindate")));
            }

        }
        finally{
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }
        return list;
    }
}
