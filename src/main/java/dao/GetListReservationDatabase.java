package dao;

import resource.Reservation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
*
Get list of (future) reservations with some order
*
*/

public class GetListReservationDatabase {

    private static final Logger logger = LogManager.getLogger("fatjon_freskina_appender");

    

    private static final String STATEMENT = """
        
        """;

    private final Connection con;

    

    public GetListReservationDatabase(final Connection con)
    {
        this.con = con;
    }


}
