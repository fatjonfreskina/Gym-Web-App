package dao.medicalcertificate;

import resource.MedicalCertificate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * This DAO is used to update a medical certificateof a user in the database
 *
 * @author Alberto Campeol
 */
public class UpdateMedicalCertificateDatabase {

    /**
     * The UPDATE query to be executed
     */
    private static final String STATEMENT = "UPDATE medicalcertificate SET expirationdate = ?, doctorname = ?, path = ? WHERE person = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The medical certificate object
     */
    private final MedicalCertificate mc;

    /**
     *
     * @param con the connection to the database
     * @param mc the medical certificate object to update
     */
    public UpdateMedicalCertificateDatabase(final Connection con, final MedicalCertificate mc) {
        this.con = con;
        this.mc = mc;
    }

    /**
     * Executes the update
     *
     * @throws SQLException
     */
    public void execute() throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(STATEMENT)) {
            preparedStatement.setDate(1, mc.getExpirationDate());
            preparedStatement.setString(2, mc.getDoctorName());
            preparedStatement.setString(3, mc.getPath());
            preparedStatement.setString(4, mc.getPersonEmail());

            preparedStatement.execute();

        } finally {
            con.close();
        }
    }
}
