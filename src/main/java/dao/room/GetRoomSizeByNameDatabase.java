package dao.room;

import constants.Constants;
import resource.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetRoomSizeByNameDatabase {
    private static final String STATEMENT = "SELECT * FROM room WHERE name=?;";

    private final Connection con;
    private final Room room;

    public GetRoomSizeByNameDatabase(final Connection con,Room room) {
        this.con = con;
        this.room=room;
    }

    public List<Room> execute() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Room> listRooms = new ArrayList<>();
        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, room.getName());
            rs = pstmt.executeQuery();
            while (rs.next())
                listRooms.add(new Room(rs.getString(Constants.ROOM_NAME), rs.getInt(Constants.ROOM_SLOTS)));
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
