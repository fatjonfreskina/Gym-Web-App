package dao.medicalcertificate;

import constants.Constants;
import resource.MedicalCertificate;
import resource.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This DAO is used to retrieve the medical certificate of a user from the database
 *
 * @author Riccardo Forzan
 */
public class GetMedicalCertificateDatabase {

    /**
     * The SELECT query to be executed
     */
    private static final String STATEMENT = "SELECT * FROM medicalcertificate WHERE person = ?";

    /**
     * Database connection object
     */
    private final Connection con;

    /**
     * Person for which the medical certificate should be retrieved
     */
    private final Person person;

    /**
     * Parametric constructor
     *
     * @param con the connection to the database
     * @param person the person object
     */
    public GetMedicalCertificateDatabase(Connection con, Person person) {
        this.con = con;
        this.person = person;
    }

    /**
     * Executes the statement for retrieving the medical certificate from the database
     *
     * @return the list containing MedicalCertificate object that matched the query
     * @throws SQLException is thrown if something goes wrong while querying the database
     */
    public List<MedicalCertificate> execute() throws SQLException {

        ResultSet resultSet = null;
        List<MedicalCertificate> mcl = new ArrayList<>();

        try (PreparedStatement preparedStatement = con.prepareStatement(STATEMENT)) {
            preparedStatement.setString(1, person.getEmail());

            //Execute the query
            resultSet = preparedStatement.executeQuery();

            //Iterate over all the certificates
            while (resultSet.next()) {
                //Parse data
                String person = resultSet.getString(Constants.MEDICALCERTIFICATE_PERSON);
                Date expirationdate = resultSet.getDate(Constants.MEDICALCERTIFICATE_EXPIRATIONDATE);
                String doctorname = resultSet.getString(Constants.MEDICALCERTIFICATE_DOCTORNAME);
                String path = resultSet.getString(Constants.MEDICALCERTIFICATE_PATH);
                //Construct the object
                var mc = new MedicalCertificate(person, expirationdate, doctorname, path);
                //Add the object to the array
                mcl.add(mc);
            }

        } finally {
            con.close();
        }

        return mcl;

    }

}
