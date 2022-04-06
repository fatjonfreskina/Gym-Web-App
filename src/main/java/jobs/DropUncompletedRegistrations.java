package jobs;

import constants.Constants;
import dao.emailconfirmation.DeleteEmailConfirmationByPersonDatabase;
import dao.emailconfirmation.GetListEmailConfimationsExpired;
import dao.person.DeleteUserByEmailDatabase;
import dao.person.GetUserByEmailDatabase;
import resource.EmailConfirmation;
import resource.Person;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * This class is used to drop users that have not completed the registration
 *
 * @author Francesco Caldivezzi
 */
public class DropUncompletedRegistrations implements Runnable
{
    @Override
    public void run()
    {
        Context ctx = null;
        try
        {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(Constants.DATASOURCE);
            var gau = new GetListEmailConfimationsExpired(ds.getConnection(),new Timestamp(System.currentTimeMillis()));
            List<EmailConfirmation> emailConfirmations = gau.execute();
            for (EmailConfirmation email : emailConfirmations)
            {
                Person toRemove = new GetUserByEmailDatabase(ds.getConnection(),email.getPerson()).execute();
                new DeleteEmailConfirmationByPersonDatabase(ds.getConnection(), toRemove).execute();
                new DeleteUserByEmailDatabase(ds.getConnection(), toRemove).execute();

                if(toRemove.getAvatarPath() != null)
                    removeAvatar(toRemove.getAvatarPath(),toRemove.getTaxCode());

            }
            System.out.println("REMOVED ALL USERS THAT HAVEN'T COMPLETED REGISTRATION YET!!!");
        }catch (SQLException | NamingException e)
        {
            System.out.println("ERROR CANNOT REMOVE USERS THAT HAVEN'T UNCOMPLETED THEIR REGISTRATION ");
            e.printStackTrace();
        }
    }

    private void removeAvatar(String path,String taxCode) //add remove medical certificate file
    {
        File fileToremove = new File(path);
        fileToremove.delete();
        fileToremove = new File(Constants.AVATAR_PATH_FOLDER + File.separator + taxCode);
        fileToremove.delete();
    }

}