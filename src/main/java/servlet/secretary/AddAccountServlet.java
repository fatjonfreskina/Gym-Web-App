package servlet.secretary;

import constants.Codes;
import constants.Constants;
import dao.passwordreset.InsertPasswordResetDatabase;
import dao.person.GetPersonByEmailDatabase;
import dao.person.GetPersonByTaxCodeDatabase;
import dao.person.InsertNewPersonDatabase;
import dao.person.InsertPersonRoleDatabase;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import resource.Message;
import resource.PasswordReset;
import resource.Person;
import servlet.AbstractServlet;
import utils.EncryptionManager;
import utils.InputValidation;
import utils.MailTypes;

import javax.naming.NamingException;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * Servlet used by a secretary to add an account for a user
 *
 * @author Alberto Campeol
 */
public class AddAccountServlet extends AbstractServlet {

    /**
     * Handles the get request by retrieving the opportune page
     *
     * @param req  the request
     * @param res  the response
     * @throws ServletException if some internal error happens
     * @throws IOException if it was not possible to forward the request and write the response
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher(Constants.PATH_SECRETARY_ADD_ACCOUNT).forward(req, res);
    }

    /**
     * Handles the post request by adding a user to the database with his/her corresponding
     * roles
     *
     * @param req  the request
     * @param res  the response
     * @throws ServletException If some internal error happens.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String taxCode = null;
        String firstName = null;
        String lastName = null;
        String address = null;
        String email = null;
        String password = generateRandomPassword(10);
        String telephoneNumber = null;
        Date birthDate = null;
        Part avatar = null;
        String role = null;

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
            telephoneNumber = req.getParameter(Constants.TELEPHONE_NUMBER);
            birthDate = Date.valueOf(req.getParameter(Constants.BIRTH_DATE));
            avatar = req.getPart(Constants.AVATAR);
            boolean isTrainer = false;
            if (req.getParameter(Person.ROLE_TRAINER) != null)
                isTrainer = req.getParameter(Person.ROLE_TRAINER).equals("on");
            boolean isSecretary = false;
            if (req.getParameter(Person.ROLE_SECRETARY) != null)
                isSecretary = req.getParameter(Person.ROLE_SECRETARY).equals("on");
            boolean isTrainee = false;
            if (req.getParameter(Person.ROLE_TRAINEE) != null)
                isTrainee = req.getParameter(Person.ROLE_TRAINEE).equals("on");
            boolean roles[] = {isSecretary, isTrainee, isTrainer};
            error = insertUser(taxCode, firstName, lastName, address, email, password, telephoneNumber, birthDate, avatar, roles,req);
            if (error.getErrorCode() != Codes.OK.getErrorCode()) {
                message = new Message(error.getErrorMessage(), true);
                registrable = false;
            }
        }

        res.setStatus(error.getHTTPCode());
        req.setAttribute(Constants.MESSAGE, message);

        //if (!registrable)
            req.getRequestDispatcher(Constants.PATH_SECRETARY_ADD_ACCOUNT).forward(req, res);
        //else
        //    req.getRequestDispatcher(Constants.PATH_CONFIRM_REGISTRATION).forward(req, res);

    }

    /**
     * Checks if the different parameters are well formatted
     *
     * @param req  the request
     * @param res  the response
     * @return  a confirmation/error message
     * @throws ServletException If some internal error happens.
     * @throws IOException If something happens when writing the response to the output stream.
     */
    public Codes parseParams(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String taxCode = null;
        String firstName = null;
        String lastName = null;
        String address = null;
        String email = null;
        String telephoneNumber = null;
        Date birthDate = null;

        Codes error = Codes.OK;
        try {
            taxCode = req.getParameter(Constants.TAX_CODE);
            firstName = req.getParameter(Constants.FIRST_NAME);
            lastName = req.getParameter(Constants.LAST_NAME);
            address = req.getParameter(Constants.ADDRESS);
            email = req.getParameter(Constants.EMAIL);
            telephoneNumber = req.getParameter(Constants.TELEPHONE_NUMBER);
            Long.parseLong(telephoneNumber);
            birthDate = Date.valueOf(req.getParameter(Constants.BIRTH_DATE));

        } catch (IllegalArgumentException e) //Either Telephone isn't a telephone or birthDate isn't a Date
        {
            error = Codes.INVALID_FIELDS;
        }

        if (error.getErrorCode() == Codes.OK.getErrorCode()) //Phone is a phone and birthDate is a Date
        {
            boolean isTrainer = false;
            if (req.getParameter(Person.ROLE_TRAINER) != null)
                isTrainer = req.getParameter(Person.ROLE_TRAINER).equals("on");
            boolean isSecretary = false;
            if (req.getParameter(Person.ROLE_SECRETARY) != null)
                isSecretary = req.getParameter(Person.ROLE_SECRETARY).equals("on");
            boolean isTrainee = false;
            if (req.getParameter(Person.ROLE_TRAINEE) != null)
                isTrainee = req.getParameter(Person.ROLE_TRAINEE).equals("on");
            if (!isSecretary && !isTrainee && !isTrainer)
                error = Codes.EMPTY_INPUT_FIELDS;
            if (taxCode == null || taxCode.isEmpty() ||
                    firstName == null || firstName.isEmpty() ||
                    lastName == null || lastName.isEmpty() ||
                    address == null || address.isEmpty() ||
                    email == null || email.isEmpty()  ||
                    telephoneNumber == null || telephoneNumber.isEmpty() ||
                    birthDate == null) // Check if some of the fields are empty
            {
                error = Codes.EMPTY_INPUT_FIELDS;
            } else if (telephoneNumber.length() != Person.LENGTH_TELEPHONE) {
                error = Codes.NOT_TELEPHONE_NUMBER;
            } else if (((isSecretary || isTrainer) && (Period.between(LocalDate.parse(birthDate.toString()), LocalDate.now()).getYears() < Person.ADULT_AGE))
                        || ((isTrainee && !isSecretary && !isTrainer) && (Period.between(LocalDate.parse(birthDate.toString()), LocalDate.now()).getYears() < Person.MIN_AGE))) {
                // a trainee can be even 14 years old, but secretary or trainer must be an adult
                error = Codes.MINIMUM_AGE;
            } else if (!InputValidation.isValidEmailAddress(email)) {
                error = Codes.NOT_A_MAIL;
            }
        }
        return error;
    }

    /**
     * Adds a user to the database
     * @param taxCode  the user's tax code
     * @param firstName  the user's first name
     * @param lastName  the user's last name
     * @param address  the user's address
     * @param email  the user's email
     * @param password  the user's password
     * @param telephoneNumber  the user's telephone number
     * @param birthDate  the user's birthdate
     * @param avatar  the user's avatar
     * @param roles  the user's role
     * @param req the request
     * @return an error/confirmation message
     */
    public Codes insertUser(String taxCode, String firstName, String lastName, String address, String email,
                            String password, String telephoneNumber, Date birthDate, Part avatar, boolean[] roles,HttpServletRequest req) {
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
                            if (roles[0])
                                new InsertPersonRoleDatabase(getDataSource().getConnection(), p, Person.ROLE_SECRETARY).execute();
                            if (roles[1])
                                new InsertPersonRoleDatabase(getDataSource().getConnection(), p, Person.ROLE_TRAINEE).execute();
                            if (roles[2])
                                new InsertPersonRoleDatabase(getDataSource().getConnection(), p, Person.ROLE_TRAINER).execute();
                            String token = EncryptionManager.encrypt(p.getAddress());
                            //Reopen a new connection
                            Connection conn = getDataSource().getConnection();
                            //Take the actual date and add 24H
                            java.util.Date now = new java.util.Date();
                            java.util.Date expiration = new java.util.Date(now.getTime() + TimeUnit.HOURS.toMillis(24));
                            Timestamp expirationTimestamp = new Timestamp(expiration.getTime());
                            //PasswordReset
                            PasswordReset passwordReset = new PasswordReset(token, expirationTimestamp,p.getEmail());
                            //Insert the password reset into the database
                            new InsertPasswordResetDatabase(conn, passwordReset).execute();

                            MailTypes.mailForPasswordChanges(p, passwordReset);
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
     * Saves the avatar of a user
     * @param file  the user's avatar file
     * @param taxCode  the user's tax code
     * @return a confirmation/error message
     * @throws IOException
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

    /**
     * Generates a random password
     * @param len  the password length
     * @return  the password as a {@code String}
     */
    public static String generateRandomPassword(int len)
    {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }
}