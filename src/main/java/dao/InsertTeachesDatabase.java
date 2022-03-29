package dao;


import resource.Course;
import resource.Person;
import resource.SubscriptionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTeachesDatabase {

    private static final String STATEMENT = "INSERT INTO teaches (courseeditionid, coursename, trainer) VALUES (?, ?, ?)";

    private final Connection con;
    private final CourseEdtion courseEdtion;
    private final Person trainer;

    public InsertTeachesDatabase(final Connection con, final CourseEdtion courseEdition, final Person trainer)
    {
        this.con = con;
        this.courseEdtion = courseEdition;
        this.trainer = trainer;
    }

    public void insertSubscriptionType() throws SQLException
    {
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement= con.prepareStatement(STATEMENT);
            preparedStatement.setInt(1, courseEdtion.getId());
            preparedStatement.setString(2, courseEdtion.getCourseName());
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
