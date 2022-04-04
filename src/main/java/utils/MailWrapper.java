package utils;

import jakarta.mail.internet.AddressException;

/**
 * @author Riccardo Forzan
 */
public class MailWrapper {

    /**
     * PLEASE DO NOT USE THIS SMTP FOR OTHER PURPOSES
     */
    private static final String HOST = "ssl0.ovh.net";
    private static final int PORT = 465;
    private static final String FROM_EMAIL = "test@projectzero.me";
    private static final String FROM_PASSWORD = "RgrfDS34678@fgreq.few73";

    private final MailManager manager;

    public MailWrapper() throws AddressException {
        this.manager = new MailManager(HOST, PORT, FROM_EMAIL, FROM_PASSWORD);
    }

    public MailManager getManager() {
        return manager;
    }

    public static void main(String[] args) throws Exception {

        String text = "TEST";
        String subject = "TEST";
        String recipient = "francesco.caldivezzi@hotmail.it";

        var manager = new MailWrapper();
        manager.getManager().sendMail(recipient, subject, text);

    }


}
