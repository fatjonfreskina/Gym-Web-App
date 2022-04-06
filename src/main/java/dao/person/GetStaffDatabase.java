package dao.person;

import resource.Person;
import resource.Teaches;
import resource.Trainer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetStaffDatabase {
    private final String statement = "SELECT email, name, surname, avatarpath, A1.coursename" +
            " FROM (teaches JOIN person ON teaches.trainer = person.email) AS A1 JOIN lecturetimeslot ON (A1.coursename = lecturetimeslot.coursename AND A1.courseeditionid = lecturetimeslot.courseeditionid)" +
            " WHERE date >= ?" +
            " GROUP BY email, name, surname, avatarpath, A1.coursename;";

    private final Connection conn;

    public GetStaffDatabase(Connection conn){
        this.conn = conn;
    }

    public List<Trainer> execute() throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs =  null;
        List<Trainer> l_trainer =new ArrayList<>();
        Date date = new Date(System.currentTimeMillis());

        try
        {
            stm = conn.prepareStatement(statement);
            stm.setDate(1,date);
            rs = stm.executeQuery();

            while(rs.next()){
                Person p = new Person(
                        rs.getString("email"),
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

                int idx = l_trainer.indexOf(new Trainer(p));
                if(idx == -1){
                    System.out.println("New element"+p);
                    l_trainer.add(new Trainer(p,t));
                }
                else{
                    System.out.println("Append element"+p);
                    l_trainer.get(idx).addTeaches(t);
                }
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
