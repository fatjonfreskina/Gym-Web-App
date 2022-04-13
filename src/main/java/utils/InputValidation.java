package utils;

import com.google.common.html.HtmlEscapers;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

public class InputValidation {

    /**
     * Check if the email address provided as parameter is valid (compliant with RFC822 format).
     *
     * @param email The email address to verify.
     * @return {@code true} if the address is valid, otherwise {@code false}.
     * @author Marco Alessio
     */
    public static boolean isValidEmailAddress(String email) {
        if (email == null || email.isEmpty())
            return false;

        try {
            InternetAddress address = new InternetAddress(email, true);
            address.validate();
        } catch (AddressException e) {
            return false;
        }

        return true;
    }

    /**
     * Checks if a string contains control characters to exploit XSS
     * @param string string to check
     * @return true if string may contain something harmful, false otherwise
     * @author Riccardo Forzan
     */
    public static boolean containsXSS(String string){
        String sanitized = HtmlEscapers.htmlEscaper().escape(string);
        return !string.equals(sanitized);
    }
}