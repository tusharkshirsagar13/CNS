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
