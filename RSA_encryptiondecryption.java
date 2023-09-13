import java.security.*;
import javax.crypto.Cipher;
import java.util.Base64;

public class RSASimpleExample {

    public static void main(String[] args) {
        try {
            // Generate key pairs
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // Key size
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            
            // Get the public and private keys
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            
            // Original message to be encrypted
            String originalMessage = "Hello, RSA!";
            
            // Encryption
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(originalMessage.getBytes());
            
            // Convert the encrypted bytes to a Base64-encoded string
            String encryptedMessage = Base64.getEncoder().encodeToString(encryptedBytes);
            
            // Display the encrypted message
            System.out.println("Encrypted message: " + encryptedMessage);
            
            // Decryption
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            
            // Convert the decrypted bytes back to a string
            String decryptedMessage = new String(decryptedBytes);
            
            // Display the decrypted message
            System.out.println("Decrypted message: " + decryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
