package dao;

import resource.Reservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
*
Get list of (future) reservations FOR GIVEN TRAINEE 

NOT FINISHED YET

*
*/

//TODO: input sanitization? 
//TODO: find out what is this DAO for and define the query

public class GetListReservationDatabase {

    private static final Logger logger = LogManager.getLogger("fatjon_freskina_appender");

    private static final String STATEMENT = """
        SELECT * 
        FROM reservation
        WHERE trainee = ?
        AND 
        """;

    private final Connection con;

    public GetListReservationDatabase(Connection con){
        this.con = con;
    }

    public List<Reservation> listTraineeReservationDatabase(String trainee) throws SQLException{

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Reservation> listReservation = new ArrayList<>();

        try{
            pstmt = con.prepareStatement(STATEMENT);
            rs = pstmt.executeQuery();
            while (rs.next()){
            }

        }
    }


}
