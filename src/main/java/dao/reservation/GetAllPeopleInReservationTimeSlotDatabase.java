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

/**
 * DAO class used to get users booked for a lecture
 *
 * @author Riccardo Forzan
 */
public class GetAllPeopleInReservationTimeSlotDatabase {

    /**
     * The query looks for all the users registered to a lecture given the lecture date, room, and start time
     */
    private static final String STATEMENT = "SELECT person.* FROM reservation INNER JOIN person ON person.email = reservation.trainee WHERE lecturedate = ? and lectureroom= ? and lecturestarttime= ?";

    private final Connection con;
    private final Reservation reservation;


    /**
     * Constructor for this class
     *
     * @param con         the database connection
     * @param reservation the reservation
     */
    public GetAllPeopleInReservationTimeSlotDatabase(final Connection con, final Reservation reservation) {
        this.con = con;
        this.reservation = reservation;
    }

    /**
     * Executes the sql statement retrieving the list of users containing that matched the query
     *
     * @return the list containing Person objects that matched the query: all the persons registered for a lecture time slot
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<Person> execute() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Person> list = new ArrayList<>();
        try {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setDate(1, reservation.getLectureDate());
            preparedStatement.setString(2, reservation.getRoom());
            preparedStatement.setTime(3, reservation.getLectureStartTime());
            //Execute the query
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(new Person(rs.getString(Constants.PERSON_EMAIL), rs.getString(Constants.PERSON_NAME), rs.getString(Constants.PERSON_SURNAME), rs.getString(Constants.PERSON_PSW), rs.getString(Constants.PERSON_TAXCODE), rs.getDate(Constants.PERSON_BIRTHDATE), rs.getString(Constants.PERSON_TELEPHONE), rs.getString(Constants.PERSON_ADDRESS), rs.getString(Constants.PERSON_AVATARPATH)));
            }
        } finally {
            if (rs != null) rs.close();
            if (preparedStatement != null) preparedStatement.close();
            con.close();
        }

        return list;

    }
}

