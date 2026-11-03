import java.util.Scanner;

public class Assignment4 {
    private static final int KEY = 73; // Secret key
    // Left rotate 8-bit value
    public static int leftRotate(int value, int shift) {
        return ((value << shift) | (value >>> (8 - shift))) & 0xFF;
    }
    // Right rotate 8-bit value
    public static int rightRotate(int value, int shift) {
        return ((value >>> shift) | (value << (8 - shift))) & 0xFF;
    }
    // Encryption
    public static String encrypt(String text) {
        StringBuilder cipher = new StringBuilder();

        for(char ch : text.toCharArray()) {

            int x = ch;
            // Step 1: XOR
            x ^= KEY;
            // Step 2: Rotate
            x = leftRotate(x,3);
            // Step 3: Convert to Hex
            cipher.append(String.format("%02X",x));
        }
        return cipher.toString();
    }

    // Decryption
    public static String decrypt(String cipher) {
        StringBuilder plain = new StringBuilder();
        for(int i=0;i<cipher.length();i+=2){
            String hex = cipher.substring(i,i+2);
            int x = Integer.parseInt(hex,16);

            // Reverse Rotate
            x = rightRotate(x,3);
            // Reverse XOR
            x ^= KEY;
            plain.append((char)x);
        }
        return plain.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Message : ");
        String message = sc.nextLine();
        String encrypted = encrypt(message);
        System.out.println("Encrypted : " + encrypted);
        String decrypted = decrypt(encrypted);
        System.out.println("Decrypted : " + decrypted);
        sc.close();
    }
}