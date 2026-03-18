import java.security.*;
import javax.crypto.*;

public class SymmetricExample_DES {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java SymmetricExample_DES text");
            System.exit(1);
        }
        byte[] rawBytes = args[0].getBytes("UTF8");
        int padding = (8 - (rawBytes.length % 8)) % 8;
        byte[] plainText = new byte[rawBytes.length + padding];
        System.arraycopy(rawBytes, 0, plainText, 0, rawBytes.length);
        for (int i = rawBytes.length; i < plainText.length; i++) {
            plainText[i] = (byte) ' ';
        }

        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56);
        Key key = keyGen.generateKey();

        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        System.out.println("\n" + cipher.getProvider().getInfo());

        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(plainText);
        System.out.println(new String(cipherText, "UTF8"));
        StringBuilder hex = new StringBuilder();
        for (byte b : cipherText) hex.append(String.format("%02x", b));
        System.out.println("Chiffre (hex): " + hex.toString());

        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] newPlainText = cipher.doFinal(cipherText);
        System.out.println(new String(newPlainText, "UTF8"));
    }
}
