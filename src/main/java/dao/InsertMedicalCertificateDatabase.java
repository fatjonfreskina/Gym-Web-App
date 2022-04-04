package dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.MedicalCertificate;

import java.sql.*;

public class InsertMedicalCertificateDatabase
{
    private static final String STATEMENT = "INSERT INTO medicalcertificate(person, expirationdate, doctorname, path) VALUES (?, ?, ?, ?)";
    private final Connection con;
    private final MedicalCertificate mc;
    private static final Logger logger = LogManager.getLogger("alberto_campeol_appender");

    public InsertMedicalCertificateDatabase (final Connection con, final MedicalCertificate mc)
    {
        this.con = con;
        this.mc = mc;
    }

    public void execute() throws SQLException
    {
        PreparedStatement preparedStatement = null;
        try
        {
            preparedStatement = con.prepareStatement(STATEMENT);
            preparedStatement.setString(1, mc.getPersonEmail());
            preparedStatement.setDate(2, (Date) mc.getExpirationDate());
            preparedStatement.setString(3, mc.getDoctorName());
            preparedStatement.setString(4, mc.getPath());

            preparedStatement.execute();
            logger.debug("[INFO] InsertMedicalCertificateDatabase - %s - Medical Certificate inserted successfully.\n".formatted(
                    new Timestamp(System.currentTimeMillis())));
        } catch (SQLException ex)
        {
            logger.error("[INFO] InsertMedicalCertificateDatabase - %s - An exception occurred during insertion.\n%s\n".
                    formatted(new Timestamp(System.currentTimeMillis()), ex.getMessage()));

            throw ex;
        }
        finally
        {
            if (preparedStatement != null)
                preparedStatement.close();
            con.close();
        }
    }
}
