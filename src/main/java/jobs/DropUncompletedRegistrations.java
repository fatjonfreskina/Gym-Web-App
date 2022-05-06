package jobs;

import constants.Constants;
import dao.emailconfirmation.GetListEmailConfimationsExpiredDatabase;
import dao.person.DeletePersonByEmailDatabase;
import dao.person.GetPersonByEmailDatabase;
import resource.EmailConfirmation;
import resource.Person;
import utils.FileManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * This class is used to drop users that have not completed the registration
 *
 * @author Francesco Caldivezzi
 */
public class DropUncompletedRegistrations implements Runnable {
    /**
     * Runs this job by dropping users who have not completed their registration yet
     */
    @Override
    public void run() {
        Context ctx = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(Constants.DATASOURCE);
            var gau = new GetListEmailConfimationsExpiredDatabase(ds.getConnection(), new Timestamp(System.currentTimeMillis()));
            List<EmailConfirmation> emailConfirmations = gau.execute();
            for (EmailConfirmation email : emailConfirmations) {
                Person toRemove = new GetPersonByEmailDatabase(ds.getConnection(), email.getPerson()).execute();
                new DeletePersonByEmailDatabase(ds.getConnection(), toRemove).execute();

                if (toRemove.getAvatarPath() != null)
                    FileManager.removeAvatar(toRemove.getAvatarPath(), toRemove.getTaxCode());

            }
        } catch (SQLException | NamingException e) {
            System.out.println("ERROR CANNOT REMOVE USERS THAT HAVEN'T UNCOMPLETED THEIR REGISTRATION ");
            e.printStackTrace();
        }
    }

}