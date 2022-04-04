package dao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.MedicalCertificate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UpdateMedicalCertificateDatabase
{
    private static final String STATEMENT = "UPDATE medicalcertificate SET expirationdate = ?, doctorname = ?, path = ? WHERE person = ?";
    private final Connection con;
    private final MedicalCertificate mc;
    private static final Logger logger = LogManager.getLogger("alberto_campeol_appender");

    public UpdateMedicalCertificateDatabase (final Connection con, final MedicalCertificate mc)
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
            preparedStatement.setDate(1, mc.getExpirationDate());
            preparedStatement.setString(2, mc.getDoctorName());
            preparedStatement.setString(3, mc.getPath());
            preparedStatement.setString(4, mc.getPersonEmail());

            preparedStatement.execute();
            logger.debug("[INFO] UpdateMedicalCertificateDatabase - %s - Medical Certificate updated successfully.\n".formatted(
                    new Timestamp(System.currentTimeMillis())));
        } catch (SQLException ex)
        {
            logger.error("[INFO] UpdateMedicalCertificateDatabase - %s - An exception occurred during the update of the medical certificate.\n%s\n".
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
