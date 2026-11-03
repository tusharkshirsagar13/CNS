import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.*;

public class Assignment9 {

    static final int NONCE_SIZE = 12;
    static final int TAG_SIZE = 128;
    static final Scanner sc = new Scanner(System.in);

    // Generate AES secret key
    static SecretKey generateKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(256);
        return kg.generateKey();
    }

    // Encrypt image
    static void encryptImage() throws Exception {
        System.out.print("Enter the image path: ");
        String input = sc.nextLine().trim();

        Path imagePath = Path.of(input);

        if (!Files.isRegularFile(imagePath)) {
            System.out.println("Error: Image file not found.");
            return;
        }

        System.out.print("Enter encrypted output file path: ");
        String output = sc.nextLine().trim();

        byte[] image = Files.readAllBytes(imagePath);
        SecretKey key = generateKey();

        // Save the key separately for the receiver
        System.out.print("Enter key file path: ");
        String keyFile = sc.nextLine().trim();
        Files.write(Path.of(keyFile), key.getEncoded());

        // Generate a fresh nonce
        byte[] nonce = new byte[NONCE_SIZE];
        new SecureRandom().nextBytes(nonce);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key,
                    new GCMParameterSpec(TAG_SIZE, nonce));

        byte[] ciphertext = cipher.doFinal(image);

        // File format: nonce + ciphertext + authentication tag
        byte[] result = new byte[nonce.length + ciphertext.length];

        System.arraycopy(nonce, 0, result, 0, nonce.length);
        System.arraycopy(ciphertext, 0, result, nonce.length,
                         ciphertext.length);

        Files.write(Path.of(output), result);

        System.out.println("\nImage encrypted successfully!");
        System.out.println("Encrypted file: " + output);
        System.out.println("Secret key saved separately: " + keyFile);
        System.out.println("Send the encrypted file to the receiver.");
        System.out.println("Share the key securely, not over the same unsecured network.");
    }

    // Decrypt and verify image
    static void decryptImage() throws Exception {
        System.out.print("Enter the encrypted image file path: ");
        String input = sc.nextLine().trim();

        System.out.print("Enter the secret key file path: ");
        String keyFile = sc.nextLine().trim();

        System.out.print("Enter output image path: ");
        String output = sc.nextLine().trim();

        byte[] data = Files.readAllBytes(Path.of(input));
        byte[] keyBytes = Files.readAllBytes(Path.of(keyFile));

        if (data.length < NONCE_SIZE + 16) {
            System.out.println("Invalid encrypted file.");
            return;
        }

        if (keyBytes.length != 32) {
            System.out.println("Invalid AES-256 key file.");
            return;
        }

        SecretKey key = new SecretKeySpec(keyBytes, "AES");

        byte[] nonce = Arrays.copyOfRange(data, 0, NONCE_SIZE);
        byte[] ciphertext = Arrays.copyOfRange(
            data, NONCE_SIZE, data.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key,
                    new GCMParameterSpec(TAG_SIZE, nonce));

        // Fails if the encrypted data was altered or the key is wrong
        byte[] image = cipher.doFinal(ciphertext);

        Files.write(Path.of(output), image);

        System.out.println("\nAuthentication successful!");
        System.out.println("Image decrypted successfully.");
        System.out.println("Recovered image: " + output);
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== IMAGE SECURITY SYSTEM =====");
            System.out.println("1. Encrypt image (Sender)");
            System.out.println("2. Decrypt image (Receiver)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        encryptImage();
                        break;
                    case "2":
                        decryptImage();
                        break;
                    case "3":
                        System.out.println("Exiting program.");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println(
                    "Operation failed: " + e.getMessage());
            }
        }
    }
}
