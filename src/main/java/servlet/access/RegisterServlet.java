package servlet.access;

import constants.Constants;
import constants.ErrorCodes;
import dao.GetUserByEmail;
import dao.GetUserByTaxCode;
import dao.InsertEmailConfermation;
import dao.InsertNewUserDatabase;
import jakarta.activation.MimeType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import resource.Message;
import resource.Person;
import servlet.AbstractServlet;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.EncryptionManager;
import utils.MailManager2;

import javax.naming.NamingException;


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
        String taxCode = null;
        String firstName = null;
        String lastName = null;
        String address = null;
        String email = null;
        String password= null;
        String confirmPassword= null;
        String telephoneNumber = null;
        Date birthDate = null;
        Part avatar = null;
        String[] roles = new String[]{Person.ROLE_TRAINEE,"",""};

        boolean registrable = true;
        Message message = null;
        ErrorCodes error = parseParams(req,res);


        if(error.getErrorCode() != ErrorCodes.OK.getErrorCode())
        {
            message = new Message(error.getErrorMessage(),true);
            registrable = false;
        }

        if(registrable) // it means that params are ok for now
        {
            taxCode = req.getParameter(Constants.TAX_CODE);
            firstName = req.getParameter(Constants.FIRST_NAME);
            lastName = req.getParameter(Constants.LAST_NAME);
            address = req.getParameter(Constants.ADDRESS);
            email = req.getParameter(Constants.EMAIL);
            password = req.getParameter(Constants.PASSWORD);
            confirmPassword = req.getParameter(Constants.CONFIRM_PASSWORD);
            telephoneNumber = req.getParameter(Constants.TELEPHONE_NUMBER);
            birthDate = Date.valueOf(req.getParameter(Constants.BIRTH_DATE));
            avatar = req.getPart(Constants.AVATAR);

            //insertUser
            error = insertUser(taxCode,firstName,lastName,address,email,password,telephoneNumber,birthDate,avatar,roles);
            if(error.getErrorCode() != ErrorCodes.OK.getErrorCode())
            {
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
            /*
            if((new GetUserByEmail(getDataSource().getConnection(),
                    new Person(null,email,null,null,null,null,null,null,null))) == null &&
            (new GetUserByEmail(getDataSource().getConnection(),
                    new Person(null,email,null,null,null,null,null,null,null)) == null ))
            {
                //
            }*/
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


    public ErrorCodes parseParams(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        String taxCode = null;
        String firstName = null;
        String lastName = null;
        String address = null;
        String email = null;
        String password= null;
        String confirmPassword= null;
        String telephoneNumber = null;
        Date birthDate = null;

        ErrorCodes error = ErrorCodes.OK;
        try
        {
            taxCode = req.getParameter(Constants.TAX_CODE);
            firstName = req.getParameter(Constants.FIRST_NAME);
            lastName = req.getParameter(Constants.LAST_NAME);
            address = req.getParameter(Constants.ADDRESS);
            email = req.getParameter(Constants.EMAIL);
            password = req.getParameter(Constants.PASSWORD);
            confirmPassword = req.getParameter(Constants.CONFIRM_PASSWORD);
            telephoneNumber = req.getParameter(Constants.TELEPHONE_NUMBER);
            Integer.parseInt(telephoneNumber);
            birthDate = Date.valueOf(req.getParameter(Constants.BIRTH_DATE));

        }catch ( IllegalArgumentException e) //Either Telephone isn't a telephone or birthDate isn't a Date
        {
            error = ErrorCodes.INVALID_FIELDS;
        }

        if(error.getErrorCode() == ErrorCodes.OK.getErrorCode()) //Phone is a phone and birthDate is a Date
        {
            if(taxCode == null || taxCode.isEmpty() ||
                    firstName == null || firstName.isEmpty() ||
                    lastName == null || lastName.isEmpty() ||
                    address == null || address.isEmpty() ||
                    email == null || email.isEmpty() ||
                    password == null || password.isEmpty() ||
                    confirmPassword == null || confirmPassword.isEmpty() ||
                    telephoneNumber == null || telephoneNumber.isEmpty() ||
                    birthDate == null) // Check if some of the fields are empty
            {
                error = ErrorCodes.EMPTY_INPUT_FIELDS;
            }else if(!password.equals(confirmPassword)) //all fields are not empty => check if the meaning is ok
            {
                error = ErrorCodes.DIFFERENT_PASSWORDS;
            }else if(telephoneNumber.length() != Person.LENGTH_TELEPHONE)
            {
                error = ErrorCodes.NOT_TELEPHONE_NUMBER;
            }else if ((Period.between(LocalDate.parse(birthDate.toString()), LocalDate.now()).getYears() < Person.MIN_AGE))
            {
                error = ErrorCodes.MINIMUM_AGE;
            }
        }
        return error;
    }

    public ErrorCodes insertUser(String taxCode,String firstName, String lastName,String address,String email,
                                 String password, String telephoneNumber,Date birthDate,Part avatar,String[] roles)
    {
        ErrorCodes error = ErrorCodes.OK;
        Person p1 = null;
        Person p2 = null;
        try
        {
            p1 = (new GetUserByEmail(getDataSource().getConnection(),new Person(email,Constants.EMAIL))).execute();
            p2 = (new GetUserByTaxCode(getDataSource().getConnection(),new Person(email,Constants.TAX_CODE))).execute();

            if(p1 == null || p2 == null)//It's a new user, so need to add it !
            {

                try
                {
                    error = saveFile(avatar,taxCode);
                    if(error.getErrorCode() == ErrorCodes.OK.getErrorCode())
                    {
                        //File saved ok can proceed with writing on the database and send email and log to test max dimension of the file??
                        String pathImg = null;
                        if(avatar != null)
                            pathImg = Constants.AVATAR_PATH_FOLDER + File.separator + taxCode +
                                    File.separator + Constants.AVATAR + avatar.getContentType().split(File.separator)[1];
                        try
                        {
                            Person p = new Person(roles,email,pathImg, EncryptionManager.encrypt(password),address,firstName,lastName,taxCode,birthDate,telephoneNumber);
                            new InsertNewUserDatabase(getDataSource().getConnection(),p);
                            String msg = "please confirm your email at this address : " + Constants.CONFIRMATION_URL + EncryptionManager.encrypt(email);

                            new InsertEmailConfermation(getDataSource().getConnection(),p,EncryptionManager.encrypt(email), )

                            MailManager2.sendMail("WELCOME TO GWA : CONFIRM YOUR REGISTRATION", msg, p);


                        }catch (NoSuchAlgorithmException e)
                        {
                            error = ErrorCodes.INTERNAL_ERROR;
                        }




                    }
                }catch (IOException e)
                {
                    error = ErrorCodes.INTERNAL_ERROR;
                }

            }else
                error = ErrorCodes.USER_ALREADY_PRESENT;
        }catch (SQLException | NamingException e )
        {
            error = ErrorCodes.INTERNAL_ERROR;
        }
        return error;
    }


    private ErrorCodes saveFile(Part file, String taxCode) throws IOException
    {
        ErrorCodes error = ErrorCodes.OK;
        File createDirectory = null;
        OutputStream writer = null;
        InputStream content = null;
        String path = null;
        Logger log = LogManager.getLogger("francesco_caldivezzi_logger");

        if(file != null)
        {
            if(!Arrays.stream(Constants.ACCPETED_EXTENSIONS_AVATAR).
                    anyMatch(file.getContentType().split(File.separator)[1]::equals))
                error = ErrorCodes.INVALID_FILE_TYPE;
            else
                path = Constants.AVATAR_PATH_FOLDER + File.separator + taxCode;

            if(error.getErrorCode() == ErrorCodes.OK.getErrorCode()) //can proceed to save
            {
                createDirectory = new File(path);
                if(!createDirectory.exists())
                    createDirectory.mkdir();

                path = path + File.separator + Constants.AVATAR + "." + file.getContentType().split(File.separator)[1];
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
                }finally
                {
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
