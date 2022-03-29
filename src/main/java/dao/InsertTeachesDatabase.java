package dao;


import resource.CourseEdition;
import resource.Person;
import resource.SubscriptionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTeachesDatabase {

    private static final String STATEMENT = "INSERT INTO teaches (courseeditionid, coursename, trainer) VALUES (?, ?, ?)";

    private final Connection con;
    private final CourseEdition courseEdition;
    private final Person trainer;

    public InsertTeachesDatabase(final Connection con, final CourseEdition courseEdition, final Person trainer)
    {
        this.con = con;
        this.courseEdition = courseEdition;
        this.trainer = trainer;
    }

    public void insertSubscriptionType() throws SQLException
    {
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement= con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, courseEdition.getId()); 
            preparedStatement.setString(2, courseEdition.getCourseName());
            preparedStatement.setString(3, trainer.getEmail());

        }
        finally {
            if(preparedStatement!=null){
                preparedStatement.close();
            }
            con.close();
        }
    }

}
