import java.security.*;
import javax.crypto.*;

// Asymmetric (public/private) cryptography with RSA
public class AsymmetricExample_01 {

     public static void main (String[] args) throws Exception {
    //
    // check args and get plaintext
    if (args.length !=1) {
      System.err.println("Usage: java AsymmetricExample_01 text");
      System.exit(1);
    }
    byte[] plainText = args[0].getBytes("UTF8");

    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    keyGen.initialize(1024);
    KeyPair key = keyGen.generateKeyPair();

    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    System.out.println( "\n" + cipher.getProvider().getInfo() );

    cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());
    byte[] cipherText = cipher.doFinal(plainText);
    System.out.println( new String(cipherText, "UTF8") );
    StringBuilder rsaHex = new StringBuilder();
    for (byte b : cipherText) rsaHex.append(String.format("%02x", b));
    System.out.println( "Chiffre (hex): " + rsaHex.toString() );

    cipher.init(Cipher.DECRYPT_MODE, key.getPrivate());
    byte[] newPlainText = cipher.doFinal(cipherText);
    System.out.println( new String(newPlainText, "UTF8") );
  }
}
