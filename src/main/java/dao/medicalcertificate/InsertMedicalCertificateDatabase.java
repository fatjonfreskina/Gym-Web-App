package dao.medicalcertificate;

import resource.MedicalCertificate;

import java.sql.*;

/**
 *
 * This DAO is used to insert a medical certificate of a user in the database
 *
 * @author Alberto Campeol
 */
public class InsertMedicalCertificateDatabase {

    /**
     * The query to be executed
     */
    private static final String STATEMENT = "INSERT INTO medicalcertificate(person, expirationdate, doctorname, path) VALUES (?, ?, ?, ?)";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The Medical certificate object
     */
    private final MedicalCertificate mc;

    /**
     *
     * Parametric constructor
     *
     * @param con the connection to the database
     * @param mc the medical certificate object to be inserted
     */
    public InsertMedicalCertificateDatabase(final Connection con, final MedicalCertificate mc) {
        this.con = con;
        this.mc = mc;
    }

    /**
     *
     * Executes the query
     *
     * @throws SQLException
     */
    public void execute() throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(STATEMENT)) {
            preparedStatement.setString(1, mc.getPersonEmail());
            preparedStatement.setDate(2, (Date) mc.getExpirationDate());
            preparedStatement.setString(3, mc.getDoctorName());
            preparedStatement.setString(4, mc.getPath());

            preparedStatement.execute();

        } finally {
            con.close();
        }
    }
}
