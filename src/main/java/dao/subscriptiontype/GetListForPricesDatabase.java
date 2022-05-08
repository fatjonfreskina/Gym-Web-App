package dao.subscriptiontype;

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
public class GetListForPricesDatabase {

    private static final String STATEMENT = """
            SELECT DISTINCT T2.courseeditionid,T2.coursename,T2.duration,T2.cost, T2.maxdate ,T2.mindate, T4.lecturesperweek
            FROM
            (SELECT courseeditionid,coursename,duration,T1.cost, max(date) AS maxdate ,min(date) AS mindate
            FROM (SELECT coursename,courseeditionid,duration,date, subscriptiontype.cost
            	  FROM subscriptiontype NATURAL JOIN lecturetimeslot
            	  WHERE duration != 7 AND date >= current_date
            	  GROUP BY (coursename,courseeditionid,duration,date,subscriptiontype.cost)) AS T1
            GROUP BY coursename,courseeditionid,duration,T1.cost) AS T2
            NATURAL JOIN teaches
            JOIN person ON teaches.trainer=person.email
            NATURAL JOIN (SELECT T3.coursename,T3.courseeditionid,AVG(T3.perweek) as lecturesperweek FROM
            (SELECT coursename,courseeditionid, count(*) AS perweek
            	  FROM subscriptiontype NATURAL JOIN lecturetimeslot
            	  WHERE duration != 7 AND date >= current_date
            	  GROUP BY (coursename,courseeditionid,duration,DATE_PART('week',date),subscriptiontype.cost)) AS T3
            GROUP BY (T3.coursename,T3.courseeditionid)) AS T4
            """;


    private final Connection con;

    /**
     * Constructor for the GetListSubscriptionTypeDatabase class
     *
     * @param con the connection to the database
     */
    public GetListForPricesDatabase(final Connection con) {
        this.con = con;
    }

    /**
     * Execute a select query to retrieve the list of types of subscription from the database
     *
     * @return the list of SubscriptionType retrieved from the database
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<PricesView> execute() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<PricesView> list = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(new PricesView(resultSet.getInt("courseeditionid"), resultSet.getString("coursename"),
                        resultSet.getInt("duration"),
                        resultSet.getFloat("cost"),
                        resultSet.getDate("maxdate"),
                        resultSet.getDate("mindate"),
                        resultSet.getFloat("lecturesperweek")));
            }

        } finally {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }
        return list;
    }
}
