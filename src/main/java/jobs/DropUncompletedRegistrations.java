package jobs;

import constants.Constants;
import dao.emailconfermation.DeleteEmailConfirmationByPersonDatabase;
import dao.emailconfermation.GetListEmailConfimationsExpired;
import dao.medicalcertificate.GetMedicalCertificateDatabase;
import dao.person.DeleteUserByEmailDatabase;
import dao.person.GetAllUsersDatabase;
import resource.EmailConfermation;
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
 * This class is used to drop users that have not completed the registration
 *
 * @author Francesco Caldivezzi
 */
public class DropUncompletedRegistrations implements Runnable
{

    @Override
    public void run() {
        // Get DataSource
        Context ctx = null;
        try
        {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(Constants.DATASOURCE);
            Connection c = ds.getConnection();
            var gau = new GetListEmailConfimationsExpired(c);
            List<EmailConfermation> emailConfermations = gau.execute();
            for (EmailConfermation email : emailConfermations)
            {
                Person toRemove = new Person(email.getPerson(), null, null, null, null, null, null, null, null);
                new DeleteUserByEmailDatabase(c, toRemove).execute();
                new DeleteEmailConfirmationByPersonDatabase(c, toRemove).execute();
            }
        }catch (SQLException | NamingException e)
        {
            System.out.println("ERROR CANNOT REMOVE USERS THAT HAVEN'T UNCOMPLETED THEIR REGISTRATION ");
            e.printStackTrace();
        }
    }
}