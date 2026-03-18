import java.security.*;
import javax.crypto.*;

public class SymmetricExample_DES {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java SymmetricExample_DES text");
            System.exit(1);
        }
        // DES/ECB/NoPadding requires input length multiple of 8 bytes
        String input = args[0];
        // Pad input to multiple of 8
        while (input.length() % 8 != 0) {
            input += " ";
        }
        byte[] plainText = input.getBytes("UTF8");

        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56);
        Key key = keyGen.generateKey();

        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        System.out.println("\n" + cipher.getProvider().getInfo());

        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(plainText);
        System.out.println(new String(cipherText, "UTF8"));

        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] newPlainText = cipher.doFinal(cipherText);
        System.out.println(new String(newPlainText, "UTF8"));
    }
}
