import java.security.*;
import javax.crypto.*;
//
// Generate a Message Digest
public class MessageDigestExample_01 {
 
  public static void main (String[] args) throws Exception {
     if (args.length !=1) { 
      System.err.println("Usage: java MessageDigestExample_01 text");
      System.exit(1);
    }
    byte[] plainText = args[0].getBytes("UTF8");
 
    MessageDigest messageDigest = MessageDigest.getInstance("SHA3-512");
 
    System.out.println( "\n" + messageDigest.getProvider().getInfo() );
 
    messageDigest.update( plainText);
    System.out.println( "\nDigest: " );
    System.out.println( new String( messageDigest.digest(), "UTF8") );
  }
}
