package dao;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class GetPersonInfo {
    private final String statement = "SELECT * FROM person WHERE email = ? ";
    private final Connection con;
    private final String email;

    public GetPersonInfo(Connection con, String email){
        this.con = con;
        this.email = email;
    }

    public Person getPersonInfo() throws SQLException {
        PreparedStatement prstm = null;
        ResultSet res =  null;
        Person output = null;

        try{
            prstm = con.prepareStatement(statement);
            prstm.setString(1,email);

            res = prstm.executeQuery();
            if(res.next()){
                output = new Person(
                        (int[]) res.getArray("role").getArray(),
                        res.getString("email"),
                        res.getString("Path_img"),
                        res.getString("password"),
                        res.getString("address"),
                        res.getString("name"),
                        res.getString("surname"),
                        res.getString("TaxCode"),
                        res.getString("Birth_date")
                );
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
