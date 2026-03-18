import java.security.*;
import javax.crypto.*;
//
// This example uses the digital signature features to generate and
// verify a signature 
public class DigitalSignatureExample_02 {
 
  public static void main (String[] args) throws Exception {
    if (args.length !=1) {
      System.err.println("Usage: java DigitalSignatureExample_02 text");
      System.exit(1);
    }
    byte[] plainText = args[0].getBytes("UTF8");

    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    keyGen.initialize(1024);
 
    KeyPair key = keyGen.generateKeyPair();
 
    Signature sig = Signature.getInstance("SHA3-512WithRSA");
    sig.initSign(key.getPrivate());
    sig.update(plainText);
    byte[] signature = sig.sign();
    System.out.println( sig.getProvider().getInfo() );
    System.out.println( "\nSignature:" );
    System.out.println( new String(signature, "UTF8") );
    //
    // verify the signature with the public key
    sig.initVerify(key.getPublic());
    sig.update(plainText);
    try {
      if (sig.verify(signature)) {
        System.out.println( "Signature verified" );
      } else System.out.println( "Signature failed" );
    } catch (SignatureException se) {
      System.out.println( "Signature failed" );
    }
  }
}
