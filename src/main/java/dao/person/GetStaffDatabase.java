package dao.person;

import resource.Person;
import resource.Teaches;
import resource.Trainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetStaffDatabase {
    private final String statement = "SELECT name, surname, avatarpath, coursename" +
            " FROM teaches JOIN person ON teaches.trainer = person.email"+
            " ORDER BY email ASC";
    private final Connection conn;

    public GetStaffDatabase(Connection conn){
        this.conn = conn;
    }

    public List<Trainer> execute() throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs =  null;
        List<Trainer> l_trainer =new ArrayList<>();
        try
        {
            stm = conn.prepareStatement(statement);
            rs = stm.executeQuery();

            while(rs.next()){
                Person p = new Person(
                        null,
                        rs.getString("name"),
                        rs.getString("surname"),
                        null,
                        null,
                        null,
                        null,
                        null,
                        rs.getString("avatarpath")
                );
                Teaches t = new Teaches(
                        -1,
                        rs.getString("coursename"),
                        null
                );
                l_trainer.add(new Trainer(p,t));
            }
        }
        finally
        {
            if (stm != null)
                stm.close();
            if (rs != null)
                rs.close();

            conn.close();
        }
        return l_trainer;
    }
}
