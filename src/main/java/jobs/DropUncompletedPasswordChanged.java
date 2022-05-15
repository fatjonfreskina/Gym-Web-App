package jobs;

import constants.Constants;
import dao.emailconfirmation.GetListEmailConfimationsExpiredDatabase;
import dao.passwordreset.DeletePasswordResetDatabase;
import dao.passwordreset.GetListPasswordReset;
import dao.person.DeletePersonByEmailDatabase;
import dao.person.GetPersonByEmailDatabase;
import resource.EmailConfirmation;
import resource.PasswordReset;
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
 * This class is used to drop users that have not completed the password reset
 *
 * @author Francesco Caldivezzi
 */
public class DropUncompletedPasswordChanged implements Runnable{

    /**
     * Runs this job by dropping users who have not completed their password reset yet
     */
    @Override
    public void run() {
        Context ctx = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(Constants.DATASOURCE);
            var gau = new GetListPasswordReset(ds.getConnection(),new Timestamp(System.currentTimeMillis()));
            List<PasswordReset> emailConfirmations = gau.execute();
            for (PasswordReset psw : emailConfirmations) {
                new DeletePasswordResetDatabase(ds.getConnection(),psw).execute();
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }
}
