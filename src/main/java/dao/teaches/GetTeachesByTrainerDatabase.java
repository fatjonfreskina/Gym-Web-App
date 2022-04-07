package dao.teaches;

import resource.Person;
import resource.Teaches;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Francesco Caldivezzi
 */
public class GetTeachesByTrainerDatabase {

    private static final String STATEMENT = "SELECT * FROM teaches WHERE trainer = ?";
    private final Connection con;
    private final Person trainer;


    public GetTeachesByTrainerDatabase(final Connection con, final Person trainer) {
        this.con = con;
        this.trainer = trainer;
    }

    public List<Teaches> execute() throws SQLException {
        List<Teaches> result = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = con.prepareStatement(STATEMENT);
            stm.setString(1, trainer.getEmail());

            rs = stm.executeQuery();
            while (rs.next())
                result.add(new Teaches(rs.getInt("courseeditionid"), rs.getString("coursename"), rs.getString("trainer")));
        } finally {
            if (stm != null)
                stm.close();
            if (rs != null)
                rs.close();
            con.close();
        }
        return result;
    }

}
