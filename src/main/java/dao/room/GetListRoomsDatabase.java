package dao.room;

import resource.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This DAO is used to show the name of the rooms and capacity in the page called "TheGym"
 *
 * @author Francesco Caldivezzi
 */
public class GetListRoomsDatabase {
    private static final String STATEMENT = "SELECT name, slots FROM room;";

    private final Connection con;

    /**
     * Constructor for the GetListRoomsDatabase class
     *
     * @param con the connection to the database
     */
    public GetListRoomsDatabase(final Connection con) {
        this.con = con;
    }

    /**
     * Execute an the query and provide the list of rooms of the gym
     *
     * @return the list of rooms in the gym
     */
    public List<Room> execute() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Room> listRooms = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(STATEMENT);
            rs = pstmt.executeQuery();
            while (rs.next())
                listRooms.add(new Room(rs.getString("name"), rs.getInt("slots")));
        } finally {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            con.close();
        }
        return listRooms;
    }
}
