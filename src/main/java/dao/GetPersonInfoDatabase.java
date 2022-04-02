package dao;

import resource.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class GetPersonInfoDatabase {
    private final String statement = "SELECT * FROM gwa.person WHERE email = ? ";

    private final Connection con;
    private final Person person;

    public GetPersonInfoDatabase(Connection con, String email, final Person person){
        this.con = con;
        this.person = person;
    }

    public Person execute() throws SQLException {
        PreparedStatement prstm = null;
        ResultSet res =  null;
        Person output = null;

        try{
            prstm = con.prepareStatement(statement);
            prstm.setString(1,person.getEmail());

            res = prstm.executeQuery();
            if(res.next()){
                output = new Person(
                        (Integer[]) res.getArray("role").getArray(),
                        res.getString("email"),
                        res.getString("avatarpath"),
                        res.getString("password"),
                        res.getString("address"),
                        res.getString("name"),
                        res.getString("surname"),
                        res.getString("TaxCode"),
                        res.getDate("birthdate"),
                        res.getString("telephone")
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
