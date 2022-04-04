package dao;

import resource.SubscriptionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class that can be used to retrieve a given type of subscription
 * that is present currently in the database as a SubscriptionType
 *
 * @author Andrea Pasin
 */
public class GetSubscriptionTypeDatabase {
    private static final String STATEMENT = "SELECT cost FROM subscriptiontype WHERE ()AND()AND()";
    private final Connection con;
    private final SubscriptionType subscriptionType;

    /**
     * Constructor for the GetSubscriptionTypeDatabase class
     *
     * @param con  the connection to the database
     * @param subscriptionType  the type of subscription to look for its cost
     */
    public GetSubscriptionTypeDatabase(final Connection con, final SubscriptionType subscriptionType)
    {
        this.con = con;
        this.subscriptionType=subscriptionType;
    }

    /**
     * Execute a select query to retrieve the type of subscription from the database with its cost
     *
     * @return  the SubscriptionType retrieved from the database with the corresponding cost. May be null in case there is no SubscriptionType found
     */
    public SubscriptionType execute() throws SQLException
    {
        if(this.subscriptionType.getCost()>=0){
            return this.subscriptionType;
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        SubscriptionType sub=null;
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                sub=new SubscriptionType(this.subscriptionType.getCourseEditionID(),
                        this.subscriptionType.getCourseName(),
                        this.subscriptionType.getDuration(),
                        resultSet.getFloat("cost")
                        );
            }
        }
        finally {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }
        return sub;
    }
}
