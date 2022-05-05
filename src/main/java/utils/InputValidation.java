package utils;

import com.google.common.html.HtmlEscapers;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

/**
 * Class used to perform some common validations on the input fields
 * @author Marco Alessio
 * @author Riccardo Forzan
 */
public class InputValidation {

    /**
     * Check if the email address provided as parameter is valid (compliant with RFC822 format).
     *
     * @param email The email address to verify.
     * @return {@code true} if the address is valid, otherwise {@code false}
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
     *
     * @param string string to check
     * @return {@code true} if string may contain something harmful, {@code false} otherwise
     */
    public static boolean containsXSS(String string) {
        String sanitized = HtmlEscapers.htmlEscaper().escape(string);
        return !string.equals(sanitized);
    }
}