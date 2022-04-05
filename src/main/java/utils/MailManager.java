package utils;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;

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

    /**
     * Setup the mail manager with the correct parameters.
     * @param host The SMTP host used in order to send the message
     * @param port The SMTP host port used in order to send the message
     * @param email The e-mail address of the sender
     * @param password The password of the sender's e-mail address
     * @throws AddressException If the email parameter is not valid
     */
    public MailManager(String host, int port, String email, String password) throws AddressException
    {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", Integer.toString(port));

        // The two options below SEEMS to be useless, but I'm not sure yet.
        //properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //properties.setProperty("mail.smtp.socketFactory.port", "465");

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
     * @param email : The e-mail address of the single receiver
     * @param subject : The subject of the e-mail
     * @param text : The text of the e-mail
     * @throws MessagingException : If the sending of the e-mail fails for any reason
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


    //TODO: Marco Alessio's testing code, do not touch!
    //Currently it sends an e-mail with:
    //- image + side text, at the top
    //- text, below
    //as HTML code.
    public void sendMail__Testing(String email, String subject) throws Exception
    {
        final InternetAddress toAddress = new InternetAddress(email);

        session.setDebug(true);

        Message msg = new MimeMessage(session);
        msg.setFrom(fromAddress);
        msg.setRecipient(Message.RecipientType.TO, toAddress);

        final String htmlText =
               """
               <div style="width:100%; height:128px;">
                   <div style="width:150px; height:100%; float:left;">
                       <img src="cid:logo_cid" alt="gwa_logo" width="128" height="128">
                   </div>
                   <div style="width:100%; height:100%;">
                       <h3>GWA Gym</h3>
                       Padua, Italy
                   </div>
               </div>
               <h1>Useless text just to try.</h1>
               """;


        Multipart multipart = new MimeMultipart();

        BodyPart textPart = new MimeBodyPart();
        //For text + image as attachment, use instead:
        //textPart.setText("Trial text with no real use.");
        textPart.setContent(htmlText, "text/html");

        BodyPart imgPart = new MimeBodyPart();
        imgPart.setDataHandler(new DataHandler(new FileDataSource(
                "src\\main\\resources\\logo.png")));
        //For text + image as attachment, use instead:
        //imgPart.setFileName("logo.png");
        imgPart.setHeader("Content-ID", "<logo_cid>");

        //Add another HTML part => appended below that comes before.
        BodyPart textPart2 = new MimeBodyPart();
        textPart2.setContent("<h2>** Footer **</h2>", "text/html");

        multipart.addBodyPart(textPart);
        multipart.addBodyPart(imgPart);
        multipart.addBodyPart(textPart2);

        msg.setSentDate(new Date());
        msg.setSubject(subject);
        msg.setContent(multipart);

        Transport.send(msg);
    }
}