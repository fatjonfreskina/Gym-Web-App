package servlet;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import utils.MailManager;

import java.util.Date;
import java.util.Properties;

public class TestingEmail
{
    private static final String HOST = "";
    private static final int PORT = 465;

    private static final String FROM_EMAIL = "";
    private static final String FROM_PASSWORD = "";
    private static final String TO_EMAIL = "";

    private static final String SUBJECT = "Test Subject";
    private static final String TEXT = "Test Text";

    public static void main(String[] args) throws Exception
    {
        MailManager manager = new MailManager(HOST, PORT, FROM_EMAIL, FROM_PASSWORD);

        manager.sendMail(TO_EMAIL, SUBJECT, TEXT);
    }
}