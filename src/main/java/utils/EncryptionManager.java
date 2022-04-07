package utils;

import jakarta.xml.bind.DatatypeConverter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Francesco Caldivezzi
 */
public class EncryptionManager {
    private static final String TYPE_ENCRIPTION = "SHA-256";

    public static String encrypt(String string) throws NoSuchAlgorithmException {
        return DatatypeConverter.printHexBinary(MessageDigest.getInstance(TYPE_ENCRIPTION).digest(string.getBytes(StandardCharsets.UTF_8)));
    }
}
