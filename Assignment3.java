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
