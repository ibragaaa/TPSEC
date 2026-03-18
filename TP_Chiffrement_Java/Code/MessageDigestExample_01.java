import java.security.*;
import javax.crypto.*;
//
// Generate a Message Digest
public class MessageDigestExample_01 {

  private static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) sb.append(String.format("%02x", b));
    return sb.toString();
  }
 
  public static void main (String[] args) throws Exception {
     if (args.length !=1) { 
      System.err.println("Usage: java MessageDigestExample_01 text");
      System.exit(1);
    }
    byte[] plainText = args[0].getBytes("UTF8");
 
    MessageDigest messageDigest = MessageDigest.getInstance("SHA3-512");
 
    System.out.println( "\n" + messageDigest.getProvider().getInfo() );
 
    messageDigest.update( plainText);
    byte[] digest = messageDigest.digest();
    System.out.println( "\nDigest: " );
    System.out.println( new String( digest, "UTF8") );
    System.out.println( "\nDigest (hex): " + bytesToHex(digest) );
  }
}
