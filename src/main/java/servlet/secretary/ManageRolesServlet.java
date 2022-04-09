package servlet.secretary;

import constants.Constants;
import constants.ErrorCodes;
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
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

/**
 * @author Alberto Campeol
 */
public class ManageRolesServlet extends AbstractServlet {


}