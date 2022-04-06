package servlet;

import utils.MailManager;

public class TestingEmail
{
    //NOTE: configuration parameters (host and port) available in a comment within MailManager class.
    private static final String HOST = "ssl0.ovh.net";
    private static final int PORT = 465;

    private static final String FROM_EMAIL = "test@projectzero.me";
    private static final String FROM_PASSWORD = "RgrfDS34678@fgreq.few73";
    private static final String TO_EMAIL = "";

    private static final String SUBJECT = "Test Subject";
    private static final String TEXT = "Test Text";

    public static void main(String[] args) throws Exception
    {
        MailManager manager = new MailManager(HOST, PORT, FROM_EMAIL, FROM_PASSWORD);

        //manager.sendMail(TO_EMAIL, SUBJECT, TEXT);
        manager.sendMail__Testing(TO_EMAIL, SUBJECT);
    }
}