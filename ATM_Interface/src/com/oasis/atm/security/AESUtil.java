package com.oasis.atm.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES-256 encryption/decryption utility for securing PIN data.
 * Uses CBC mode with a random IV per encryption for real security.
 *
 * Format stored: Base64(IV) + ":" + Base64(CipherText)
 */
public class AESUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 256;

    // For demo purposes, we use a hardcoded 256-bit key.
    private static final byte[] SECRET_KEY_BYTES = {
            0x4f, 0x61, 0x73, 0x69, 0x73, 0x41, 0x54, 0x4d,
            0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65,
            0x79, 0x32, 0x30, 0x32, 0x34, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };

    /**
     * Encrypts plaintext using AES-256 CBC with a random IV.
     * 
     * @param plainText the PIN or sensitive data to encrypt
     * @return Base64(IV):Base64(CipherText)
     */
    public static String encrypt(String plainText) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY_BYTES, "AES");

        // Generate a fresh random IV for every encryption
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));

        // Store IV with ciphertext so we can decrypt later
        String ivBase64 = Base64.getEncoder().encodeToString(iv);
        String cipherBase64 = Base64.getEncoder().encodeToString(encrypted);
        return ivBase64 + ":" + cipherBase64;
    }

    /**
     * Decrypts a stored AES-encrypted string.
     * 
     * @param encryptedText Base64(IV):Base64(CipherText)
     * @return original plaintext
     */
    public static String decrypt(String encryptedText) throws Exception {
        String[] parts = encryptedText.split(":");
        if (parts.length != 2)
            throw new IllegalArgumentException("Invalid encrypted format");

        byte[] iv = Base64.getDecoder().decode(parts[0]);
        byte[] cipherBytes = Base64.getDecoder().decode(parts[1]);

        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY_BYTES, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decrypted = cipher.doFinal(cipherBytes);

        return new String(decrypted, "UTF-8");
    }
}
