package dao.teaches;

import constants.Constants;
import resource.Person;
import resource.Teaches;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class used to get the course editions and trainers associated to them
 *
 * @author Francesco Caldivezzi
 */
public class GetTeachesByTrainerDatabase {

    private static final String STATEMENT = "SELECT * FROM teaches WHERE trainer = ?";
    private final Connection con;
    private final Person trainer;


    /**
     * Constructor for this class
     *
     * @param con     the connection to the database
     * @param trainer the trainer
     */
    public GetTeachesByTrainerDatabase(final Connection con, final Person trainer) {
        this.con = con;
        this.trainer = trainer;
    }

    /**
     * Executes the sql statement retrieving the courses and their corresponding trainers
     *
     * @return the courses and their corresponding trainers
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<Teaches> execute() throws SQLException {
        List<Teaches> result = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = con.prepareStatement(STATEMENT);
            stm.setString(1, trainer.getEmail());

            rs = stm.executeQuery();
            while (rs.next())
                result.add(new Teaches(rs.getInt(Constants.TEACHES_COURSEEDITIONID), rs.getString(Constants.TEACHES_COURSENAME), rs.getString(Constants.TEACHES_TRAINER)));
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
