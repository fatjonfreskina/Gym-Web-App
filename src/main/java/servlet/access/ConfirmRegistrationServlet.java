package servlet.access;


import constants.Constants;
import constants.ErrorCodes;
import dao.emailconfermation.DeleteEmailConfirmationByPersonDatabase;
import dao.emailconfermation.GetEmailConfirmationByTokenDatabase;
import dao.person.DeleteUserByEmailDatabase;
import dao.person.GetUserByEmailDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.EmailConfermation;
import resource.Message;
import resource.Person;
import servlet.AbstractServlet;
import utils.MailTypes;
import utils.MailWrapper;

import javax.naming.NamingException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ConfirmRegistrationServlet extends AbstractServlet
{

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        String tokenUser = req.getParameter("token");
        EmailConfermation emailConfermation = null;
        ErrorCodes error = ErrorCodes.OK;
        Message message = new Message(error.getErrorMessage(),false);

        if(tokenUser != null)
        {
            try
            {
                emailConfermation = (new GetEmailConfirmationByTokenDatabase(getDataSource().getConnection(),new EmailConfermation(null,tokenUser,null))).execute();

                if(emailConfermation != null) //ok there's the record =>2 possible cases it is not expired or it is expired
                {
                    Person p = (new GetUserByEmailDatabase(getDataSource().getConnection(),emailConfermation.getPerson())).execute();

                    (new DeleteEmailConfirmationByPersonDatabase(getDataSource().getConnection(),p)).execute();

                    if(emailConfermation.getExpirationDate().getTime() < System.currentTimeMillis())
                    {
                        //need to remove eventually the directory and file inside of it of the path
                        if(p.getAvatarPath() != null)
                            removeAvatar(p.getAvatarPath(),p.getTaxCode());
                        //expired need to remove the user
                        (new DeleteUserByEmailDatabase(getDataSource().getConnection(),p)).execute();
                    }
                }else //already removed by the process or someone tries a number at random
                {
                    error = ErrorCodes.CONFIRMATION_NOT_FOUND;
                }
            }catch (NamingException | SQLException e)
            {
                error = ErrorCodes.INTERNAL_ERROR;
            }

            if(error.getErrorCode() == ErrorCodes.OK.getErrorCode())
            {
                //Ok ridireziona tutto apposto ridireziona a CONFIRMED REGISTRATION

                //MailTypes.;

                message = new Message(error.getErrorMessage(),false);
                res.setStatus(error.getHTTPCode());
                req.setAttribute(Constants.MESSAGE,message);
                req.getRequestDispatcher(Constants.PATH_CONFIRMED_REGISTRATION).forward(req, res);
            }else
            {
                message = new Message(error.getErrorMessage(),true);
                res.setStatus(error.getHTTPCode());
                req.setAttribute(Constants.MESSAGE,message);
                req.getRequestDispatcher(Constants.PATH_CONFIRMED_REGISTRATION).forward(req, res);
            }
        }else
        {
            error = ErrorCodes.BAD_REQUEST;
            message = new Message(error.getErrorMessage(),true);
            res.setStatus(error.getHTTPCode());
            req.setAttribute(Constants.MESSAGE,message);
            req.getRequestDispatcher(Constants.PATH_CONFIRMED_REGISTRATION).forward(req, res);
        }
    }


    private void removeAvatar(String path,String taxCode)
    {
        File fileToremove = new File(path);
        fileToremove.delete();
        fileToremove = new File(Constants.AVATAR_PATH_FOLDER + File.separator + taxCode);
        fileToremove.delete();
    }
}
