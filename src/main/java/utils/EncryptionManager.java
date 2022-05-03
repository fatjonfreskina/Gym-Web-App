package utils;

import jakarta.xml.bind.DatatypeConverter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class that provides an encryption method
 *
 * @author Francesco Caldivezzi
 */
public class EncryptionManager {
    private static final String TYPE_ENCRIPTION = "SHA-256";

    /**
     * Encrypts a provided {@code String} through SHA-256
     * @param string  the {@code String} to encrypt
     * @return  the encrypted {@code String}
     * @throws NoSuchAlgorithmException
     */
    public static String encrypt(String string) throws NoSuchAlgorithmException {
        return DatatypeConverter.printHexBinary(MessageDigest.getInstance(TYPE_ENCRIPTION).digest(string.getBytes(StandardCharsets.UTF_8)));
    }
}
