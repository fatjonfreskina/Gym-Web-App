package servlet;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Date;
import java.util.Properties;

public class TestingEmail
{
    private static final String FROM = "";
    private static final String FROM_PASSWORD = "";
    private static final String TO = "";

    private static final String SUBJECT = "Test Subject";
    private static final String TEXT = "Test Text";

    public static void main(String[] args) throws Exception
    {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        //properties.put("mail.smtp.host", "smtp.gmail.com");
        //properties.put("mail.smtp.host", "smtp.gmail.com");

        Session session = Session.getInstance(properties, new Authenticator()
            {
                @Override
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(FROM, FROM_PASSWORD);
                }
            });
        session.setDebug(true);

        InternetAddress from = new InternetAddress(FROM);
        InternetAddress to = new InternetAddress(TO);

        Message msg = new MimeMessage(session);
        msg.setFrom(from);
        msg.setRecipient(Message.RecipientType.TO, to);

        msg.setSubject(SUBJECT);
        msg.setText(TEXT);
        msg.setSentDate(new Date());

        Transport.send(msg);
    }
}