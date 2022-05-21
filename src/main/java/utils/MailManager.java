package utils;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;


import java.util.Date;
import java.util.Properties;

/**
 * This class is used to manage e-mail messages.
 *
 * @author Marco Alessio, Simone D'Antimo
 */
public class MailManager {

    private final String fromEmail;
    private final Session session;

    /**
     * Setup the mail manager with the correct parameters.
     *
     * @param host     The SMTP host used in order to send the message
     * @param port     The SMTP host port used in order to send the message
     * @param email    The e-mail address of the sender
     * @param password The password of the sender's e-mail address
     */

    public MailManager(String host, int port, String email, String password) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", Integer.toString(port));

        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        session.setDebug(false);

        fromEmail = email;
    }


    /**
     * This method is used to send a simple e-mail message, composed only of plain text.
     *
     * @param email   : The e-mail address of the single receiver
     * @param subject : The subject of the e-mail
     * @param text    : The text of the e-mail
     * @throws MessagingException : If the sending of the e-mail fails for any reason
     */
    /*
    public void sendMail(String email, String subject, String text) throws MessagingException {
        final InternetAddress fromAddress = new InternetAddress(fromEmail);
        final InternetAddress toAddress = new InternetAddress(email);

        Message msg = new MimeMessage(session);
        msg.setFrom(fromAddress);
        msg.setRecipient(Message.RecipientType.TO, toAddress);

        msg.setSentDate(new Date());
        msg.setSubject(subject);
        msg.setText(text);

        Transport.send(msg);
    }
    */

    /**
     * This method is used to send a complex e-mail message, and not limited to only plain text.
     *
     * @param email   : The e-mail address of the single receiver
     * @param subject : The subject of the e-mail
     * @param text : The details
     * @throws MessagingException : If the sending of the e-mail fails for any reason
     */

    //To make this work : Add a logo.jpg in Tomcat folder/gwa (same folder where medicalCertificate and Avatar folders are)

    public void sendMail(String email, String subject, String text) throws MessagingException {
        final InternetAddress fromAddress = new InternetAddress(fromEmail);
        final InternetAddress toAddress = new InternetAddress(email);
        Message msg = new MimeMessage(session);
        msg.setFrom(fromAddress);
        msg.setRecipient(Message.RecipientType.TO, toAddress);

        Multipart multipart = new MimeMultipart();
        BodyPart textPart = new MimeBodyPart();

        StringBuilder htmlText = new StringBuilder();

         htmlText.append("""
                <div style="width:100%; height:100%;">
                     <div style="width:500px; height:220px;">
                          <img src="cid:logo_cid" name="gwa_logo.jpg" alt="gwa_logo" width="500" height="200">
                     </div>
                </div>
                <div> <pre>""");
         htmlText.append(text);
         htmlText.append("</pre> </div>");


        textPart.setContent(htmlText.toString(), "text/html");

        BodyPart imgPart = new MimeBodyPart();
        imgPart.setDataHandler(new DataHandler(new FileDataSource("../gwa/logo.jpg")));
        imgPart.setHeader("Content-ID", "<logo_cid>");
        imgPart.setFileName("logo.jpg");

        //Add text and image to the body
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(imgPart);

        //Set email fields
        msg.setSentDate(new Date());
        msg.setSubject(subject);
        msg.setContent(multipart);

        //Send message
        Transport.send(msg);

    }
}