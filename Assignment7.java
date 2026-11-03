
import java.security.*;
import java.nio.charset.StandardCharsets;

public class Assignment7{
    public static void main(String[] args) throws Exception {

        // 1. Generate public and private keys
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();

        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        // 2. Original tender message
        String message = "Tender ID: 101, Amount: 500000";
        byte[] data = message.getBytes(StandardCharsets.UTF_8);

        // 3. Sign the message using the private key
        Signature signer = Signature.getInstance("SHA256withDSA");
        signer.initSign(privateKey);
        signer.update(data);
        byte[] digitalSignature = signer.sign();

        System.out.println("Original Message: " + message);
        System.out.println("Digital Signature: "+ java.util.Base64.getEncoder().encodeToString(digitalSignature));

        // 4. Verify using the public key
        Signature verifier = Signature.getInstance("SHA256withDSA");
        verifier.initVerify(publicKey);
        verifier.update(data);

        boolean valid = verifier.verify(digitalSignature);
        System.out.println("Signature Valid: " + valid);

        // 5. Change the message to simulate tampering
        String modifiedMessage = "Tender ID: 101, Amount: 900000";

        verifier.initVerify(publicKey);
        verifier.update(modifiedMessage.getBytes(StandardCharsets.UTF_8));

        boolean tamperedValid = verifier.verify(digitalSignature);
        System.out.println("Modified Message: " + modifiedMessage);
        System.out.println("Modified Signature Valid: " + tamperedValid);
    }
}

/*
 * This program shows how to sign a tender message and verify its signature.
 * It first generates a 2048-bit DSA public and private key pair.
 * The original message contains tender ID 101 and an amount of 500000.
 * The program converts the message into UTF-8 bytes.
 * It uses SHA256withDSA to hash the message and sign it with the private key.
 * It displays the signature in Base64 so the signature bytes can be read as text.
 * It then uses the public key to check the signature against the original message.
 * The first check prints true because the signature matches the original message.
 * The program changes the amount to 900000 and checks it with the original signature.
 * The second check prints false because the message was changed after signing.
 */