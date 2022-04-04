package dao;

import resource.MedicalCertificate;
import resource.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Riccardo Forzan
 */
public class GetMedicalCertificateDatabase {

    private static final String STATEMENT = "SELECT * FROM medicalcertificate WHERE person = ?";
    private final Connection con;
    private final Person person;

    public GetMedicalCertificateDatabase(Connection con, Person person) {
        this.con = con;
        this.person = person;
    }

    public List<MedicalCertificate> execute() throws SQLException {

        ResultSet resultSet = null;
        List<MedicalCertificate> mcl = new ArrayList<>();

        try (PreparedStatement preparedStatement = con.prepareStatement(STATEMENT)) {
            preparedStatement.setString(1, person.getEmail());

            //Execute the query
            resultSet = preparedStatement.executeQuery();

            //Iterate over all the certificates
            while (resultSet.next()){
                //Parse data
                String person = resultSet.getString("person");
                Date expirationdate = resultSet.getDate("expirationdate");
                String doctorname = resultSet.getString("doctorname");
                String path = resultSet.getString("path");
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
