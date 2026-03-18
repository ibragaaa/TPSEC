import java.security.*;
import javax.crypto.*;

// encrypt and decrypt using the AES symmetric key algorithm
public class SymmetricExample_01 {

  private static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) sb.append(String.format("%02x", b));
    return sb.toString();
  }
 
  public static void main (String[] args) throws Exception {
    //
    // check args and get plaintext
    if (args.length !=1) {
      System.err.println("Usage: java SymmetricExample_01 text");
      System.exit(1);
    }
    byte[] plainText = args[0].getBytes("UTF8");

    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    keyGen.init(128);
    Key key = keyGen.generateKey();

    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");	
    System.out.println( "\n" + cipher.getProvider().getInfo() );

    cipher.init(Cipher.ENCRYPT_MODE, key);
    byte[] cipherText = cipher.doFinal(plainText);
    System.out.println( new String(cipherText, "UTF8") );
    System.out.println( "Chiffre (hex): " + bytesToHex(cipherText) );

    cipher.init(Cipher.DECRYPT_MODE, key);
    byte[] newPlainText = cipher.doFinal(cipherText);
 
    System.out.println( new String(newPlainText, "UTF8") );
  }
}
