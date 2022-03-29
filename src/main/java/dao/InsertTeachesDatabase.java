package dao;

import resource.SubscriptionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTeachesDatabase {

    private static final String STATEMENT = "INSERT INTO teaches (courseeditionid, coursename, trainer) VALUES (?, ?, ?)";
    private final Connection con;

    private final SubscriptionType subscriptionType;

    /**
     * Constructor for the InsertSubscriptionTypeDatabase class
     *
     * @param con  the connection to the database
     * @param subscriptionType  the type of subscription to be added into the database
     */
    public InsertTeachesDatabase(final Connection con, final int courseEditionId, final Course)
    {
        this.con = con;
        this.subscriptionType=subscriptionType;
    }

    /**
     * Execute an Insert query to insert the provided SubscriptionType into the subscriptiontype table
     */
    public void insertSubscriptionType() throws SQLException
    {
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement=con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, subscriptionType.getCourseEditionID());
            preparedStatement.setString(2, subscriptionType.getCourseName());
            preparedStatement.setInt(3, subscriptionType.getDuration());
            preparedStatement.setFloat(4, subscriptionType.getCost());
        }
        finally {
            if(preparedStatement!=null){
                preparedStatement.close();
            }
            con.close();
        }

    }

}
