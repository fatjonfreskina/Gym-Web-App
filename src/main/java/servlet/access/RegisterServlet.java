package servlet.access;

import constants.Codes;
import constants.Constants;
import dao.emailconfirmation.InsertEmailConfirmationDatabase;
import dao.person.GetPersonByEmailDatabase;
import dao.person.GetPersonByTaxCodeDatabase;
import dao.person.InsertNewPersonDatabase;
import dao.person.InsertPersonRoleDatabase;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import resource.EmailConfirmation;
import resource.Message;
import resource.Person;
import servlet.AbstractServlet;
import utils.EncryptionManager;
import utils.InputValidation;
import utils.MailTypes;

import javax.naming.NamingException;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.regex.Matcher;

/**
 * Servlet used to register a user
 * @author Francesco Caldivezzi
 */
public class RegisterServlet extends AbstractServlet {

    /**
     * Handles the get request by providing the correct page to register
     * @param req  the request
     * @param res  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher(Constants.PATH_REGISTER).forward(req, res);
    }

    /**
     * Handles the post request by adding a user to the database if his/her credentials
     * are valid
     *
     * @param req  the request
     * @param res  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String taxCode = null;
        String firstName = null;
        String lastName = null;
        String address = null;
        String email = null;
        String password = null;
        String telephoneNumber = null;
        Date birthDate = null;
        Part avatar = null;

        boolean registrable = true;
        Message message = new Message(Codes.OK.getErrorMessage(), false);
        Codes error = parseParams(req, res);


        if (error.getErrorCode() != Codes.OK.getErrorCode()) {
            message = new Message(error.getErrorMessage(), true);
            registrable = false;
        }

        if (registrable) // it means that params are ok for now
        {
            taxCode = req.getParameter(Constants.TAX_CODE);
            firstName = req.getParameter(Constants.FIRST_NAME);
            lastName = req.getParameter(Constants.LAST_NAME);
            address = req.getParameter(Constants.ADDRESS);
            email = req.getParameter(Constants.EMAIL);
            password = req.getParameter(Constants.PASSWORD);
            telephoneNumber = req.getParameter(Constants.TELEPHONE_NUMBER);
            birthDate = Date.valueOf(req.getParameter(Constants.BIRTH_DATE));
            avatar = req.getPart(Constants.AVATAR);

            //insertUser
            error = insertUser(taxCode, firstName, lastName, address, email, password, telephoneNumber, birthDate, avatar, Person.ROLE_TRAINEE);
            if (error.getErrorCode() != Codes.OK.getErrorCode()) {
                message = new Message(error.getErrorMessage(), true);
                registrable = false;
            }
        }

        res.setStatus(error.getHTTPCode());
        req.setAttribute(Constants.MESSAGE, message);

        if (!registrable)
            req.getRequestDispatcher(Constants.PATH_REGISTER).forward(req, res);
        else
            req.getRequestDispatcher(Constants.PATH_CONFIRM_REGISTRATION).forward(req, res);

    }


    /**
     * Checks if the different parameters are well formatted
     *
     * @param req  the request
     * @param res  the response
     * @return  a confirmation/error message
     */
    public Codes parseParams(HttpServletRequest req, HttpServletResponse res) {
        String taxCode = null;
        String firstName = null;
        String lastName = null;
        String address = null;
        String email = null;
        String password = null;
        String confirmPassword = null;
        String telephoneNumber = null;
        Date birthDate = null;

        Codes error = Codes.OK;
        try {
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

        } catch (IllegalArgumentException e) //Either Telephone isn't a telephone or birthDate isn't a Date
        {
            error = Codes.INVALID_FIELDS;
        }

        if (error.getErrorCode() == Codes.OK.getErrorCode()) //Phone is a phone and birthDate is a Date
        {
            if (taxCode == null || taxCode.isEmpty() ||
                    firstName == null || firstName.isEmpty() ||
                    lastName == null || lastName.isEmpty() ||
                    address == null || address.isEmpty() ||
                    email == null || email.isEmpty() ||
                    password == null || password.isEmpty() ||
                    confirmPassword == null || confirmPassword.isEmpty() ||
                    telephoneNumber == null || telephoneNumber.isEmpty() ||
                    birthDate == null) // Check if some of the fields are empty
            {
                error = Codes.EMPTY_INPUT_FIELDS;
            } else if (!password.equals(confirmPassword)) //all fields are not empty => check if the meaning is ok
            {
                error = Codes.DIFFERENT_PASSWORDS;
            } else if (telephoneNumber.length() != Person.LENGTH_TELEPHONE) {
                error = Codes.NOT_TELEPHONE_NUMBER;
            } else if ((Period.between(LocalDate.parse(birthDate.toString()), LocalDate.now()).getYears() < Person.MIN_AGE)) {
                error = Codes.MINIMUM_AGE;
            } else if (!InputValidation.isValidEmailAddress(email)) {
                error = Codes.NOT_A_MAIL;
            }
        }
        return error;
    }

    /**
     * Inserts a user into the database
     * @param taxCode  the user's tax code
     * @param firstName  the user's first name
     * @param lastName  the user's last name
     * @param address  the user's address
     * @param email  the user's email
     * @param password  the user's password
     * @param telephoneNumber  the user's telephone number
     * @param birthDate  the user's birthdate
     * @param avatar  the user's avatar
     * @param role  the user's role
     * @return an error/confirmation message
     */
    public Codes insertUser(String taxCode, String firstName, String lastName, String address, String email,
                            String password, String telephoneNumber, Date birthDate, Part avatar, String role) {
        Codes error = Codes.OK;
        Person p1 = null;
        Person p2 = null;
        try {
            p1 = (new GetPersonByEmailDatabase(getDataSource().getConnection(), email)).execute();

            p2 = (new GetPersonByTaxCodeDatabase(getDataSource().getConnection(), new Person(null, null, null,
                    null, taxCode, null, null, null, null))).execute();

            if (p1 == null && p2 == null)//It's a new user, so need to add it !
            {

                try {
                    error = saveFile(avatar, taxCode);
                    if (error.getErrorCode() == Codes.OK.getErrorCode()) {
                        //File saved ok can proceed with writing on the database and send email and log to test max dimension of the file??
                        String pathImg = null;
                        if ((avatar != null) && (avatar.getSize() != 0)) //.png da aggiungere
                            pathImg = Constants.AVATAR_PATH_FOLDER + File.separator + taxCode +
                                    File.separator + Constants.AVATAR + "." + avatar.getContentType().split(File.separator)[1];
                        try {
                            Person p = new Person(email, firstName, lastName, EncryptionManager.encrypt(password)
                                    , taxCode, birthDate, telephoneNumber, address, pathImg);

                            new InsertNewPersonDatabase(getDataSource().getConnection(), p).execute();
                            new InsertPersonRoleDatabase(getDataSource().getConnection(), p, role).execute();
                            (new InsertEmailConfirmationDatabase(getDataSource().getConnection(), new EmailConfirmation(p.getEmail(), EncryptionManager.encrypt(email),
                                    new Timestamp(System.currentTimeMillis() + Constants.DAY)))).execute();

                            MailTypes.mailForConfirmRegistration(p);
                        } catch (NoSuchAlgorithmException | MessagingException e) {
                            error = Codes.INTERNAL_ERROR;
                        }
                    }
                } catch (IOException e) {
                    error = Codes.INTERNAL_ERROR;
                }

            } else
                error = Codes.USER_ALREADY_PRESENT;
        } catch (SQLException | NamingException e) {
            error = Codes.INTERNAL_ERROR;
        }
        return error;
    }


    /**
     * Saves the avatar file
     * @param file  the avatar file
     * @param taxCode  the user's tax code
     * @return an error/confirmation message
     * @throws IOException if there is an issue when writing the file
     */
    private Codes saveFile(Part file, String taxCode) throws IOException {
        OutputStream writer = null;
        InputStream content = null;

        if ((file != null) && file.getSize() != 0)
        {
            final String filename = file.getSubmittedFileName();
            final Matcher matcher = Constants.ACCEPTED_AVATAR_FILENAME_REGEX.matcher(filename);
            if (!matcher.find())
                return Codes.INVALID_FILE_TYPE;

            final String extension = matcher.group(2);

            final String dirPath = (Constants.AVATAR_PATH_FOLDER + "/" + taxCode).replace('/', File.separatorChar);

            final File createDirectory = new File(dirPath);
            if (!createDirectory.exists())
                createDirectory.mkdir();

            File[] files = createDirectory.listFiles();
            if(files != null)
            {
                for (File f: files)
                {
                    f.delete();
                }
            }

            final String filePath = dirPath + File.separator + Constants.AVATAR + "." + extension;

            try {

                writer = new FileOutputStream(filePath,false);
                content = file.getInputStream();
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = content.read(bytes)) != -1)
                    writer.write(bytes, 0, read);
            } catch (IOException e) {
                return Codes.CANNOT_UPLOAD_FILE;
            } finally {
                if (content != null)
                    content.close();
                if (writer != null)
                    writer.close();
            }
        }
        return Codes.OK;
    }
}