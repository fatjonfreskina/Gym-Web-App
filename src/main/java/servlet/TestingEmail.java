package servlet;

import utils.MailManager;

public class TestingEmail
{
    //NOTE: configuration parameters (host and port) available in a comment within MailManager class.
    private static final String HOST = "smtp.libero.it";
    private static final int PORT = 465;

    private static final String FROM_EMAIL = "alemarco96@libero.it";
    private static final String FROM_PASSWORD = "Vgn-Fz21m";
    private static final String TO_EMAIL = "alessiomar1996@gmail.com";

    private static final String SUBJECT = "Test Subject";
    private static final String TEXT = "Test Text";

    public static void main(String[] args) throws Exception
    {
        MailManager manager = new MailManager(HOST, PORT, FROM_EMAIL, FROM_PASSWORD);

        manager.sendMail(TO_EMAIL, SUBJECT, TEXT);
    }
}