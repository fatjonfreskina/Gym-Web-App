package utils;

import constants.Constants;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import resource.Person;
import java.util.Properties;

public class MailManager
{
    public static void sendMail(String to,String title ,String msg, final Person person)
    {
        Properties properties = System.getProperties();
        properties.setProperty(Constants.MAILSMTPHOST, Constants.HOST);
        Session session = Session.getDefaultInstance(properties);

        try
        {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Constants.NOREPLYEMAIL));
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
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