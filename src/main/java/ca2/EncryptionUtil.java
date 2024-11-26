package ca2;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileWriter;
import java.util.Base64;

/*
    video used for AES encryption and decryption
    https://www.youtube.com/watch?v=LtUU8Q3rgjM
 */

public class EncryptionUtil {

    public static SecretKey getKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

        // Writing to file https://www.geeksforgeeks.org/java-program-to-write-into-a-file/
        FileWriter fileWriter = new FileWriter("ciphertext.txt");
        fileWriter.write(encryptedText);
        fileWriter.close();

        return encryptedText;
    }

    public static String decrypt(String cipherText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        String plainText =  new String(decryptedBytes);

        FileWriter fileWriter = new FileWriter("plaintext.txt");
        fileWriter.write(plainText);
        fileWriter.close();

        return plainText;
    }
}
