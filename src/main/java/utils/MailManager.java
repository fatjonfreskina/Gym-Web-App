package utils;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Date;
import java.util.Properties;

/**
 * This class is used to manage e-mail messages.
 * @author Marco Alessio
 * */
public class MailManager
{
    /*
    Connection parameters per e-mail provider:

                    Host:                       Port:           Cryptographic option:
    - Gmail         smtp.gmail.com              465             SSL (must enable sender account's option
                                                                "Less secure app access" first)
    - Hotmail       smtp.live.com               25 / 587        STARTTLS
                                                465             SSL
    - Libero        smtp.libero.it              465             SSL
    - Outlook       smtp-mail.outlook.com       587             STARTTLS
    */
    private final InternetAddress fromAddress;
    private final Session session;

    public MailManager(String host, int port, String email, String password) throws AddressException
    {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", Integer.toString(port));
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(email, password);
            }
        });
        //session.setDebug(true);
        session.setDebug(false);


        fromAddress = new InternetAddress(email);
    }


    /**
     * This method is used to send a simple e-mail message, composed only of plain text.
     * @param email : the e-mail address of the single receiver
     * @param subject : the subject of the e-mail
     * @param text : the text of the e-mail
     * */
    public void sendMail(String email, String subject, String text) throws MessagingException
    {
        final InternetAddress toAddress = new InternetAddress(email);

        Message msg = new MimeMessage(session);
        msg.setFrom(fromAddress);
        msg.setRecipient(Message.RecipientType.TO, toAddress);

        msg.setSentDate(new Date());
        msg.setSubject(subject);
        msg.setText(text);

        Transport.send(msg);
    }
}