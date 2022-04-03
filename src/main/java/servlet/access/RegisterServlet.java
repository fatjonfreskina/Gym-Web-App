package servlet.access;

import constants.Constants;
import constants.ErrorCodes;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import resource.Message;
import servlet.AbstractServlet;

import java.awt.*;
import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

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
        Part medicalCertificate = null;
        Part avatar = null;


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

            avatar = req.getPart(Constants.AVATAR);
            medicalCertificate = req.getPart(Constants.MEDICAL_CERTIFICATE);

            if(taxCode == null || taxCode.isEmpty())
            {
                error = ErrorCodes.EMPTY_INPUT_FIELDS;
                message = new Message(error.getErrorMessage(),true);
                registrable = false;
            }else
            {
                error = saveFile (avatar, Constants.AVATAR, taxCode);
                error = saveFile (medicalCertificate,Constants.AVATAR,taxCode);
            }

        }catch ( IllegalArgumentException e)
        {
            error = ErrorCodes.INVALID_FIELDS;
            message = new Message(error.getErrorMessage(),true);
            registrable = false;
        }

        if(registrable)
        {
            if(firstName == null || lastName == null || address == null || email == null || password == null
                    || confirmPassword == null || birthDate == null || firstName.isEmpty() || lastName.isEmpty()
            || address.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() )
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
                log.info("ANNI : "+ Period.between(LocalDate.parse(birthDate.toString()), LocalDate.now()).getYears());
                error = ErrorCodes.MINIMUM_AGE;
                message = new Message(error.getErrorMessage(),true);
                registrable = false;
            }else if(req.getParameter(Constants.TELEPHONE_NUMBER).length() != Constants.MIN_LENGTH_PHONE_NUMBER)
            {
                error = ErrorCodes.NOT_TELEPHONE_NUMBER;
                message = new Message(error.getErrorMessage(),true);
                registrable = false;
            }
        }
        if(!registrable)
        {
            res.setStatus(error.getHTTPCode());
            req.setAttribute(Constants.MESSAGE,message);
            req.getRequestDispatcher(Constants.PATH_REGISTER).forward(req, res);
        }
    }

    private ErrorCodes saveFile(Part file, String type, String taxCode)
    {
        ErrorCodes error = ErrorCodes.OK;
        File createDirectory = null;
        OutputStream writer = null;
        InputStream content = null;
        String path = null;

        if(taxCode == null || taxCode.isEmpty())
        {
            error = ErrorCodes.EMPTY_INPUT_FIELDS;
        }else if(file != null)
        {
            if(type.equals(Constants.MEDICAL_CERTIFICATE))
            {
                if(!Arrays.stream(Constants.ACCPETED_EXTENSIONS_MEDICAL_CERTIFICATE).anyMatch(file.getContentType()::equals))
                    error = ErrorCodes.INVALID_FILE_TYPE;
                else
                    path = Constants.MEDICAL_CERTIFICATE_PATH_FOLDER + File.separator+ taxCode + file.getContentType() ;
            }
            else if(type.equals(Constants.AVATAR))
            {
                if(!Arrays.stream(Constants.ACCPETED_EXTENSIONS_AVATAR).anyMatch(file.getContentType()::equals))
                    error = ErrorCodes.INVALID_FILE_TYPE;
                else
                    path = Constants.AVATAR_PATH_FOLDER + File.separator+ taxCode + file.getContentType();
            }
            if(error.getErrorCode() == ErrorCodes.OK.getErrorCode())
            {
                createDirectory = new File(path);
                if(!createDirectory.exists())
                    createDirectory.mkdir();

                try
                {
                    writer = new FileOutputStream(path);

                    content = file.getInputStream();
                    int read = 0;
                    final byte[] bytes = new byte[1024];
                    while ((read = content.read(bytes)) != -1)
                        writer.write(bytes, 0, read);
                }catch (IOException e)
                {
                    error = ErrorCodes.CANNOT_UPLOAD_FILE;
                }
            }
        }
        return error;
    }
}
