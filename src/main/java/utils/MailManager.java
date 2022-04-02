package utils;

import constants.Constants;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import resource.Person;
import java.util.Properties;

/**
 * This class is used to manage mail massages
 * @author Francesco Caldivezzi
 * */
public class MailManager
{
    /**
     * This method is used to send a mail message given a title a message and the person to send it
     * @param title : the title of the mail
     * @param msg : the message to send
     * @param person : the person to which we need to send the message
     * */
    public static void sendMail(String title ,String msg, final Person person)
    {
        Properties properties = System.getProperties();
        properties.setProperty(Constants.MAILSMTPHOST, Constants.HOST);
        Session session = Session.getDefaultInstance(properties);
        try
        {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Constants.NOREPLYEMAIL));
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(person.getEmail()));
            message.setSubject(title);
            message.setText("Dear "+person.getName()+" "+person.getSurname()+",\n"+
                    "\n\n" +
                    msg +
                    "Kind regards,\n"+
                    "The Gwa Team");
            Transport.send(message);
        }
        catch (MessagingException mex)
        {
            mex.printStackTrace();
        }
    }
}