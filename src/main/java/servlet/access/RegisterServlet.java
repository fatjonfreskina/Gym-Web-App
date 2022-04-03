package servlet.access;

import constants.Constants;
import constants.ErrorCodes;
import jakarta.activation.MimeType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import resource.Message;
import resource.Person;
import servlet.AbstractServlet;

import java.awt.*;
import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Da testare max size upload!!!
 *
 * */
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
        String[] roles = new String[]{Person.ROLE_TRAINEE,"",""};

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



        }catch ( IllegalArgumentException e)
        {
            error = ErrorCodes.INVALID_FIELDS;
            message = new Message(error.getErrorMessage(),true);
            registrable = false;
        }


        if(registrable)
        {
            if(taxCode == null || taxCode.isEmpty() ||firstName == null || lastName == null || address == null || email == null || password == null
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
        }else
        {
            //here the user will be registered
            /*
            * Thing to do :
            * 1) check if it is already in the db by testing email and tax code and the save files if i need it!
            * 2) if can register => add in db all the things needed
            * 3) send email for confirmation of registration ?
            * That's it!
            *
            * if(taxCode == null || taxCode.isEmpty())
            {
                error = ErrorCodes.EMPTY_INPUT_FIELDS;
                message = new Message(error.getErrorMessage(),true);
                registrable = false;
            }else
            {
                error = saveFile (avatar, Constants.AVATAR, taxCode);
                if(error.getErrorCode() != ErrorCodes.OK.getErrorCode())
                {
                    message = new Message(error.getErrorMessage(),true);
                    registrable = false;
                }else
                {
                    error = saveFile (medicalCertificate,Constants.MEDICAL_CERTIFICATE,taxCode);
                    if(error.getErrorCode() != ErrorCodes.OK.getErrorCode())
                    {
                        message = new Message(error.getErrorMessage(),true);
                        registrable = false;
                    }
                }
            }
            * */

        }
    }

    private ErrorCodes saveFile(Part file, String type, String taxCode) throws IOException
    {
        ErrorCodes error = ErrorCodes.OK;
        File createDirectory = null;
        OutputStream writer = null;
        InputStream content = null;
        String path = null;
        Logger log = LogManager.getLogger("francesco_caldivezzi_logger");
        if(taxCode == null || taxCode.isEmpty())
        {
            error = ErrorCodes.EMPTY_INPUT_FIELDS;
        }else if(file != null)
        {
            if(type.equals(Constants.MEDICAL_CERTIFICATE))
            {

                log.info(file.getContentType());
                if(!Arrays.stream(Constants.ACCPETED_EXTENSIONS_MEDICAL_CERTIFICATE).anyMatch(file.getContentType().split("/")[1]::equals))
                    error = ErrorCodes.INVALID_FILE_TYPE;
                else
                    path = Constants.MEDICAL_CERTIFICATE_PATH_FOLDER + "/"+ taxCode;
            }
            else if(type.equals(Constants.AVATAR))
            {
                log.info(file.getContentType());
                if(!Arrays.stream(Constants.ACCPETED_EXTENSIONS_AVATAR).anyMatch(file.getContentType().split("/")[1]::equals))
                    error = ErrorCodes.INVALID_FILE_TYPE;
                else
                    path = Constants.AVATAR_PATH_FOLDER + "/"+ taxCode;
            }
            //log.info(Constants.MEDICAL_CERTIFICATE_PATH_FOLDER+"/"+taxCode);
            log.info(path);

            if(error.getErrorCode() == ErrorCodes.OK.getErrorCode())
            {
                createDirectory = new File(path);
                if(!createDirectory.exists())
                    createDirectory.mkdir();

                path = path + "/"+type+"."+file.getContentType().split("/")[1];
                log.info(path);
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
                }finally {
                    if(content != null)
                        content.close();
                    if(writer != null)
                        writer.close();
                }

            }
        }
        return error;
    }
}
