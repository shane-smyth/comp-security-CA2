package ca2;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
    video used for AES encryption and decryption
    https://www.youtube.com/watch?v=LtUU8Q3rgjM
 */
public class Main {
    public static void main(String[] args) throws Exception {

        Scanner keyboard = new Scanner(System.in);
        String[] menuOptions = {
                "0. Exit",
                "1. Encrypt a File",
                "2. Decrypt a File"
        };

        int menuChoice = -1;
        do {
            MenuUtil.displayMenu(menuOptions, "Encryption Menu");
            try {
                menuChoice = MenuUtil.getMenuChoice(menuOptions.length);
                switch (menuChoice) {
                    case 1:
                        System.out.println("Encrypt");

                        System.out.println("enter text: ");
                        String plaintext = keyboard.nextLine();

                        System.out.println(encrypt(plaintext,getKey()));
                        break;
                    case 2:
                        System.out.println("Decrypt");
                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid - Please enter a valid option");
            }
        }
        while (menuChoice != 0);
    }

    public static SecretKey getKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}