package servlet.access;

import constants.Constants;
import constants.ErrorCodes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import resource.Message;
import servlet.AbstractServlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterServlet extends AbstractServlet
{

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException
    {


        req.getRequestDispatcher(Constants.PATH_REGISTER).forward(req, res);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException
    {
        Logger log = LogManager.getLogger("francesco_caldivezzi_logger");
        String taxCode = null;
        String firstName = null;
        String lastName = null;
        String address = null;
        String email = null;
        String password= null;
        String confirmPassword= null;
        Integer telephoneNumber = null;
        Date birthDate = null;
        ErrorCodes error = null;
        Message message = null;
        boolean registrable = true;
        try
        {
            taxCode = req.getParameter(Constants.TAX_CODE);
            firstName = req.getParameter(Constants.FIRST_NAME);
            lastName = req.getParameter(Constants.LAST_NAME);
            address = req.getParameter(Constants.ADDRESS);
            email = req.getParameter(Constants.EMAIL);
            password = req.getParameter(Constants.PASSWORD);
            confirmPassword = req.getParameter(Constants.CONFIRM_PASSWORD);
            telephoneNumber = Integer.parseInt(req.getParameter(Constants.TELEPHONE_NUMBER));
            birthDate = Date.valueOf(req.getParameter(Constants.BIRTH_DATE));
        }catch (Exception e)
        {

        }
        if(taxCode == null || firstName == null || lastName == null || address == null || email == null || password == null
                || confirmPassword == null || telephoneNumber == null || birthDate == null)
        {
            error = ErrorCodes.EMPTY_INPUT_FIELDS;
            message = new Message(error.getErrorMessage(),true);
            registrable = false;
        }else if(!password.equals(confirmPassword))
        {
            error = ErrorCodes.DIFFERENT_PASSWORDS;
            message = new Message(error.getErrorMessage(),true);
            registrable = false;
        }else if(Period.between(LocalDate.parse(birthDate.toString()), LocalDate.now()).getYears() < Constants.MIN_AGE)
        {
            error = ErrorCodes.MINIMUM_AGE;
            message = new Message(error.getErrorMessage(),true);
            registrable = false;
        }
        if(registrable)
        {


        }else
        {
            res.setStatus(error.getHTTPCode());
            req.setAttribute(Constants.MESSAGE,message);
            req.getRequestDispatcher(Constants.PATH_REGISTER).forward(req, res);
        }






    }
}
