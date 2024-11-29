package ca2;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
    github link:
    https://github.com/shane-smyth/comp-security-CA2
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
                        System.out.print("\nenter file name to encrypt: ");
                        String encryptFileName = keyboard.nextLine();
                        encryptFileName = TxtFileValidation.validateFileName(encryptFileName);
                        while (!Files.exists(Path.of(encryptFileName))) {//https://stackoverflow.com/questions/1816673/how-do-i-check-if-a-file-exists-in-java
                            System.out.print("File not found, try again: ");
                            encryptFileName = keyboard.nextLine();
                            encryptFileName = TxtFileValidation.validateFileName(encryptFileName);
                        }
                        String plaintext = readFile(encryptFileName);
                        EncryptionUtil.encrypt(plaintext, secretKey);

                        //https://stackoverflow.com/questions/5355466/converting-secret-key-into-a-string-and-vice-versa#:~:text=You%20can%20convert%20the%20SecretKey,to%20rebuild%20your%20original%20SecretKey%20.
                        String displayKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
                        System.out.println("The file has been encrypted is in ciphertext.txt \nkey used is: "+displayKey+"\n");
                        break;
                    case 2:
                        System.out.print("\nenter file name to decrypt: ");
                        String decryptFileName = keyboard.nextLine();
                        decryptFileName = TxtFileValidation.validateFileName(decryptFileName);
                        while (!Files.exists(Path.of(decryptFileName))) {
                            System.out.print("File not found, try again: ");
                            decryptFileName = keyboard.nextLine();
                            decryptFileName = TxtFileValidation.validateFileName(decryptFileName);
                        }
                        String cipherText = readFile(decryptFileName);

                        System.out.print("enter valid key: ");
                        String userKey = keyboard.nextLine();

                        //https://stackoverflow.com/questions/5355466/converting-secret-key-into-a-string-and-vice-versa#:~:text=You%20can%20convert%20the%20SecretKey,to%20rebuild%20your%20original%20SecretKey%20.
                        byte[] decodeUserKey = Base64.getDecoder().decode(userKey);
                        SecretKey originalUserKey = new SecretKeySpec(decodeUserKey, 0, decodeUserKey.length, "AES");
                        EncryptionUtil.decrypt(cipherText, originalUserKey);

                        System.out.println("file has been decrypted and is now in plaintext.txt\n");
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