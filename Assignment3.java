import java.security.*;
import javax.crypto.Cipher;

public class Assignment3 {
    public static void main(String[] args) throws Exception {
        int[] keySizes = {1024, 2048, 3072}; //bits RSA key
        int[] messageSizes = {64, 128, 256, 384};

        for (int size : keySizes) {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(size);

            long start = System.nanoTime();
            KeyPair pair = gen.generateKeyPair();
            long keyTime = System.nanoTime() - start;

            PublicKey pub = pair.getPublic();
            PrivateKey pri = pair.getPrivate();

            System.out.println("\nKey Size: " + size);
            System.out.printf("Key Generation: %.3f ms\n", keyTime / 1e6);
            System.out.println("Message(bits) | Encrypt(ms) | Decrypt(ms)");

            for (int bits : messageSizes) {
                byte[] msg = new byte[bits / 8];

                Cipher enc = Cipher.getInstance("RSA");
                enc.init(Cipher.ENCRYPT_MODE, pub);

                start = System.nanoTime();
                byte[] encrypted = enc.doFinal(msg);
                long encTime = System.nanoTime() - start;

                Cipher dec = Cipher.getInstance("RSA");
                dec.init(Cipher.DECRYPT_MODE, pri);

                start = System.nanoTime();
                dec.doFinal(encrypted);
                long decTime = System.nanoTime() - start;

                System.out.printf("%13d | %11.3f | %11.3f%n",
                    bits, encTime / 1e6, decTime / 1e6);
            }
        }
    }
}

/*
 * This program measures how long RSA key generation, encryption and decryption take.
 * It tests three key sizes: 1024, 2048 and 3072 bits.
 * For each size, it starts a timer and generates a public and private key pair.
 * It subtracts the start time from the current time to find the key generation time.
 * It then tests messages of 64, 128, 256 and 384 bits with that key pair.
 * For each message, it creates a byte array filled with zeros.
 * It prepares the RSA cipher with the public key and times the encryption call.
 * It prepares another cipher with the private key and times the decryption call.
 * It converts the times from nanoseconds to milliseconds and prints them in a table.
 * The table lets us compare the operations, and the times can vary between runs.
 */