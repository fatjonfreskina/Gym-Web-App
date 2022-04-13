package dao.room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import constants.Constants;
import resource.Room;

/**
 * @author Andrea Pasin
 * @author Harjot Singh
 */
public class GetRoomByNameDatabase {
  private static final String STATEMENT = "SELECT * FROM room WHERE name=?;";

  private final Connection con;
  private final Room room;

  public GetRoomByNameDatabase(final Connection con, Room room) {
    this.con = con;
    this.room = room;
  }

  public Room execute() throws SQLException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Room result = null;
    try {
      pstmt = con.prepareStatement(STATEMENT);
      pstmt.setString(1, room.getName());
      rs = pstmt.executeQuery();
      if (rs.next())
        result = new Room(rs.getString(Constants.ROOM_NAME), rs.getInt(Constants.ROOM_SLOTS));
    } finally {
      if (rs != null)
        rs.close();
      if (pstmt != null)
        pstmt.close();
      con.close();
    }
    return result;
  }
}
