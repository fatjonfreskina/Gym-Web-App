package jobs;

import constants.Constants;
import dao.medicalcertificate.GetMedicalCertificateDatabase;
import dao.person.GetAllPersonsDatabase;
import resource.MedicalCertificate;
import resource.Person;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * This class is used to check expiration of medical certificate and notify the users
 *
 * @author Riccardo Forzan
 */
public class MedicalCertificateExpiration implements Runnable {

    @Override
    public void run() {

        // Get DataSource
        Context ctx = null;
        try {

            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(Constants.DATASOURCE);
            Connection conn = ds.getConnection();

            //Get all the users
            var gau = new GetAllPersonsDatabase(conn);
            List<Person> personList = gau.execute();

            //Actual date of today
            Date actual = new Date();
            //Date of today plus one week
            Date oneWeekFromNow = Date.from(actual.toInstant().plus(1, ChronoUnit.WEEKS));

            //Iterate over all the users
            for (Person p : personList) {

                MedicalCertificate expiringCertificate = null;
                boolean hasValidCertificate = false;

                var gmc = new GetMedicalCertificateDatabase(((DataSource) ctx.lookup(Constants.DATASOURCE)).getConnection(), p);
                List<MedicalCertificate> certificateList = gmc.execute();

                //Check if the user has at least one non expired medical certificate
                for (MedicalCertificate mc : certificateList) {

                    //Convert from java.sql.Date to java.util.Date
                    var expirationDate = new Date(mc.getExpirationDate().getTime());

                    if (expirationDate.after(oneWeekFromNow)) {
                        //The user has a valid certificate
                        hasValidCertificate = true;
                        break;
                    } else if (expirationDate.before(oneWeekFromNow) && expirationDate.after(actual)) {
                        //The user has a certification that is expiring, send an alert
                        expiringCertificate = mc;
                    }

                }

                //If the medical certificate of the user has expired, then send a mail
                if (!hasValidCertificate) {
                    //TODO: Unlock emails   MailTypes.mailForMedicalCertificateExpiring(p, expiringCertificate);
                }

            }

        } catch (NamingException | SQLException e) {
            //Print exception to logs of Tomcat
            System.out.println(e.getMessage());
        }

    }

}
