package jobs;

import constants.Constants;
import dao.GetAllUsersDatabase;
import dao.medicalcertificate.GetMedicalCertificateDatabase;
import resource.MedicalCertificate;
import resource.Person;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * This class is used to check expiration of medical certificate and notify the users
 * @author Riccardo Forzan
 */
public class MedicalCertificateExpiration implements Runnable {

    @Override
    public void run() {

        // Get DataSource
        Context ctx = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup(Constants.DATASOURCE);
            Connection c = ds.getConnection();

            //Get all the users
            var gau = new GetAllUsersDatabase(c);
            List<Person> personList = gau.execute();

            Date actualDate = new Date();

            //Iterate over all the users
            for (Person p: personList) {

                boolean hasValidCertificate = false;
                var gmc = new GetMedicalCertificateDatabase(c,p);
                List<MedicalCertificate> certificateList = gmc.execute();

                //Check if the user has at least one non expired medical certificate
                for(MedicalCertificate mc : certificateList){

                    //Convert from java.sql.Date to java.util.Date
                    var expirationDate = new Date(mc.getExpirationDate().getTime());

                    if(expirationDate.after(actualDate)){
                        //The user has a valid certificate
                        hasValidCertificate = true;
                        break;
                    }

                }

                //If the medical certificate of the user has expired, then send a mail
                if(!hasValidCertificate){
                    String email = p.getEmail();
                    //TODO: Send an email to the user
                }

            }

        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }

        System.out.print("\n\n LAUNCHED JOB TO CHECK MEDICAL CERTIFICATE EXPIRATION \n\n");

    }

}
