package dao;

import resource.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
*
* This DAO is used to show the name of the rooms and capicity in the page called "TheGym"
*
*  */
public class GetListRoomsDatabase
{
    private static final String STATEMENT = "SELECT name, slots FROM room;";

    private final Connection con;

    public GetListRoomsDatabase(final Connection con)
    {
        this.con = con;
    }

    public List<Room> listRoomsDatabase() throws SQLException
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Room> listRooms = new ArrayList<>();
        try
        {
            pstmt = con.prepareStatement(STATEMENT);
            rs = pstmt.executeQuery();
            while (rs.next())
                listRooms.add(new Room(rs.getString("name"),rs.getInt("slots")));
        } finally
        {
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
            con.close();
        }
        return listRooms;
    }
}
