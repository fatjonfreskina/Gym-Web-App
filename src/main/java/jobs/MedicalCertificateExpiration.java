package jobs;

import constants.Constants;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * This class is used to check expiration of medical certificate and notify the users
 * @author Riccardo Forzan
 */
public class MedicalCertificateExpiration implements Runnable {

    @Override
    public void run() {

        //TODO: Call the DAO to perform the operations
        System.out.print("\n\nWORKING -- Medical certificate expirations\n\n");
    }
}
