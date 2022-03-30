package dao;

import resource.Course;
import resource.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetAvailableCourses {
    private final String statement =
            "SELECT * "+
            "FROM (courseedition as CE JOIN lecturetimeslot as LTS ON CE.id=LTS.courseeditionid AND CE.coursename=LTS.coursename) as A JOIN course as C ON A.coursename=C.name"+
            "WHERE date >= ? ";
    private final Connection con;
    private final Date today;

    public GetAvailableCourses(Connection con){
        this.con = con;
        this.today = new Date(System.currentTimeMillis());
    }

    public List<Course> getPersonInfo() throws SQLException {
        PreparedStatement prstm = null;
        ResultSet res =  null;
        List<Course> output = new ArrayList<>();

        try{
            prstm = con.prepareStatement(statement);
            prstm.setDate(1,today);

            res = prstm.executeQuery();
            while(res.next()){
                output.add(new Course(
                        res.getString("name"),
                        res.getString("description")
                ));
            }
        }finally {
            if(res != null)
                res.close();
            if(prstm != null)
                prstm.close();

            con.close();
        }

        return output;
    }
}
