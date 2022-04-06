package servlet.access;


import constants.Constants;
import constants.ErrorCodes;
import dao.emailconfirmation.DeleteEmailConfirmationByPersonDatabase;
import dao.emailconfirmation.GetEmailConfirmationByTokenDatabase;
import dao.person.DeleteUserByEmailDatabase;
import dao.person.GetUserByEmailDatabase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import resource.EmailConfirmation;
import resource.Message;
import resource.Person;
import servlet.AbstractServlet;

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
        EmailConfirmation emailConfirmation = null;
        ErrorCodes error = ErrorCodes.OK;
        Message message = new Message(error.getErrorMessage(),false);

        if(tokenUser != null)
        {
            try
            {
                emailConfirmation = (new GetEmailConfirmationByTokenDatabase(getDataSource().getConnection(),new EmailConfirmation(null,tokenUser,null))).execute();

                if(emailConfirmation != null) //ok there's the record =>2 possible cases it is not expired or it is expired
                {
                    Person p = (new GetUserByEmailDatabase(getDataSource().getConnection(),emailConfirmation.getPerson())).execute();

                    (new DeleteEmailConfirmationByPersonDatabase(getDataSource().getConnection(),p)).execute();

                    if(emailConfirmation.getExpirationDate().getTime() < System.currentTimeMillis())
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
