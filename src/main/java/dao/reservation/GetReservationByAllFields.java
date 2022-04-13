package dao.reservation;

import constants.Constants;
import resource.Person;
import resource.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
    @author Riccardo Tumiati
 */
public class GetReservationByAllFields {
    private static final String STATEMENT = "SELECT * FROM reservation WHERE lecturedate = ? and lectureroom= ? and lecturestarttime= ? and trainee = ? ";
    private final Connection con;
    private final Reservation reservation;


    public GetReservationByAllFields(final Connection con, final Reservation reservation) {
        this.con = con;
        this.reservation = reservation;
    }

    public Reservation execute() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Reservation r = null;
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setDate(1, reservation.getLectureDate());
            preparedStatement.setString(2, reservation.getRoom());
            preparedStatement.setTime(3, reservation.getLectureStartTime());
            preparedStatement.setString(4, reservation.getTrainee());
            //Execute the query
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                r = new Reservation(
                        rs.getString(Constants.RESERVATION_TRAINEE),
                        rs.getString(Constants.RESERVATION_LECTUREROOM),
                        rs.getDate(Constants.RESERVATION_LECTUREDATE),
                        rs.getTime(Constants.RESERVATION_LECTURESTARTTIME)
                );
            }
        } finally {
            if (rs != null) rs.close();
            if (preparedStatement != null) preparedStatement.close();
            con.close();
        }

        return r;

    }
}
