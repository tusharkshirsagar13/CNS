import java.util.Scanner;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

public class Assignment6 {

    static String calculateSHA1(String text) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hash = md.digest(text.getBytes(StandardCharsets.UTF_8));

        return HexFormat.of().formatHex(hash);
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter original text: ");
        String original = sc.nextLine();

        String originalHash = calculateSHA1(original);

        System.out.println("\nOriginal Text: " + original);
        System.out.println("SHA-1 Digest: " + originalHash);

    
        String modified = original.toLowerCase();

        
        String modifiedHash = calculateSHA1(modified);

        System.out.println("\nModified Text: " + modified);
        System.out.println("SHA-1 Digest: " + modifiedHash);

        System.out.println("\n=== Integrity Verification ===");

        if (originalHash.equals(modifiedHash)) {
            System.out.println("Digests match.");
            System.out.println("No difference detected.");
        } else {
            System.out.println("Digests do not match.");
            System.out.println("Input has changed or contents differ.");
        }

        sc.close();
    }
}

/*
 * This program reads a line of text and calculates its SHA-1 digest.
 * A digest is a fixed-length value that acts like a fingerprint for the input.
 * The program converts the text to UTF-8 bytes before hashing it.
 * It uses Java's MessageDigest class to calculate the SHA-1 hash.
 * SHA-1 produces 20 bytes, which the program displays as 40 hexadecimal digits.
 * The program prints the original text and its digest.
 * It then creates a lowercase version of the original text.
 * It calculates and prints the digest of the lowercase version in the same way.
 * It compares the two digest strings and reports whether they match.
 * For example, changing Hello to hello changes one letter and produces a different digest.
 */