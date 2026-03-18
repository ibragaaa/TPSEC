import java.security.*;
import javax.crypto.*;
//
// Generate a Message Authentication Code
public class MessageAuthenticationCodeExample_01 {
 
  public static void main (String[] args) throws Exception {
    if (args.length !=1) {
      System.err.println
        ("Usage: java MessageAuthenticationCodeExample_01 text");
      System.exit(1);
    }
    byte[] plainText = args[0].getBytes("UTF8");

    KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
    SecretKey HmacSHA512key = keyGen.generateKey();

    Mac mac = Mac.getInstance("HmacSHA512");
    mac.init(HmacSHA512key);
    mac.update(plainText);

    System.out.println( "\n" + mac.getProvider().getInfo() );
    byte[] macResult = mac.doFinal();
    System.out.println( "\nMAC: " );
    System.out.println( new String( macResult, "UTF8") );
    StringBuilder macHex = new StringBuilder();
    for (byte b : macResult) macHex.append(String.format("%02x", b));
    System.out.println( "\nMAC (hex): " + macHex.toString() );

  }
}
