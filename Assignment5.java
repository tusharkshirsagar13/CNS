import java.util.Scanner;
import java.math.BigInteger;

public class Assignment5 {

    static BigInteger p = BigInteger.valueOf(23);
    static BigInteger g = BigInteger.valueOf(5);
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Alice's private key (1-21): ");
        int a = sc.nextInt();

        System.out.print("Enter Bob's private key (1-21): ");
        int b = sc.nextInt();

        BigInteger A = g.modPow(BigInteger.valueOf(a), p);
        BigInteger B = g.modPow(BigInteger.valueOf(b), p);

        System.out.println("\n=== NORMAL DIFFIE-HELLMAN ===");
        System.out.println("Alice's public key: " + A);
        System.out.println("Bob's public key: " + B);

        BigInteger aliceNormal = B.modPow(BigInteger.valueOf(a), p);
        BigInteger bobNormal = A.modPow(BigInteger.valueOf(b), p);

        System.out.println("Alice derives secret key: " + aliceNormal);
        System.out.println("Bob derives secret key: " + bobNormal);

        if (aliceNormal.equals(bobNormal))
            System.out.println("Result: Both derive the SAME secret key.");

        System.out.print("Enter Eve's private key for Alice (1-21): ");
        int e1 = sc.nextInt();

        System.out.print("Enter Eve's private key for Bob (1-21): ");
        int e2 = sc.nextInt();
        
        BigInteger fakeForAlice = g.modPow(BigInteger.valueOf(e1), p);
        BigInteger fakeForBob = g.modPow(BigInteger.valueOf(e2), p);

        System.out.println("\n=== MITM ATTACK ===");
        System.out.println("Eve sends fake key to Alice: " + fakeForAlice);
        System.out.println("Eve sends fake key to Bob: " + fakeForBob);

        BigInteger aliceAttack = fakeForAlice.modPow(BigInteger.valueOf(a), p);
        BigInteger bobAttack = fakeForBob.modPow(BigInteger.valueOf(b), p);

        BigInteger eveWithAlice = A.modPow(BigInteger.valueOf(e1), p);
        BigInteger eveWithBob = B.modPow(BigInteger.valueOf(e2), p);

        System.out.println("\nAlice derives secret key: " + aliceAttack);
        System.out.println("Eve derives Alice's key: " + eveWithAlice);
        System.out.println("Bob derives secret key: " + bobAttack);
        System.out.println("Eve derives Bob's key: " + eveWithBob);

        System.out.println("\n=== FINAL RESULT ===");

        if (aliceAttack.equals(bobAttack)) {
            System.out.println("Alice and Bob happen to derive the same value.");
            System.out.println("This does not prove the exchange is secure.");
        } else {
            System.out.println("Alice's secret key: " + aliceAttack);
            System.out.println("Bob's secret key: " + bobAttack);
            System.out.println("ATTACK SUCCESSFUL: Secret keys are DIFFERENT!");
        }

        sc.close();
    }
}

/*
 * This program shows a normal Diffie-Hellman exchange and then simulates an attack.
 * It uses the public values p = 23 and g = 5.
 * It reads private numbers for Alice and Bob from the keyboard.
 * Each person uses their private number to calculate a public value with modPow().
 * Alice combines Bob's public value with her private number, and Bob does the reverse.
 * Both get the same shared secret in the normal exchange.
 * The program then reads two private numbers for Eve and creates two fake public values.
 * Eve sends one fake value to Alice in place of Bob's and the other to Bob in place of Alice's.
 * Alice and Bob calculate their secrets using the fake values they received.
 * Eve uses the real public values and her own private numbers to calculate both secrets.
 * The program prints the results to show how Eve can learn the secrets by replacing public values.
 */