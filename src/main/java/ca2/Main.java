package ca2;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
                "1. Encrypt a File",
                "2. Decrypt a File",
                "0. Exit",
        };

        int menuChoice = -1;
        SecretKey secretKey = EncryptionUtil.getKey();
        do {
            MenuUtil.displayMenu(menuOptions, "Encryption Menu");
            try {
                menuChoice = MenuUtil.getMenuChoice(menuOptions.length);
                switch (menuChoice) {
                    case 1:
                        System.out.println("Encrypt");

                        System.out.println("enter file name: ");
                        String fileName = keyboard.nextLine();
                        fileName = TxtFileValidation.validateFileName(fileName);
                        String plaintext = readFile(fileName);

                        System.out.println(EncryptionUtil.encrypt(plaintext, secretKey)+"\n\n");
                        System.out.println(plaintext);
                        break;
                    case 2:
                        System.out.println("Decrypt");

                        System.out.println("enter cipher text: ");
                        String cipherText = keyboard.nextLine();

                        System.out.println(EncryptionUtil.decrypt(cipherText, secretKey)+"\n\n");
                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid - Please enter a valid option");
            }
        }
        while (menuChoice != 0);
    }

    // https://www.geeksforgeeks.org/java-io-filereader-class/
    public static String readFile(String fileName) throws Exception {
        FileReader fileReader = new FileReader(fileName);
        String fileContent = "";
        int i;
        while ((i = fileReader.read()) != -1) {
            fileContent += (char)i;
        }
        fileReader.close();
        return fileContent;
    }
}