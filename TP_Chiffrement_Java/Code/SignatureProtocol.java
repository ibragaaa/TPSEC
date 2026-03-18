import java.security.*;
import javax.crypto.*;
import java.util.Arrays;

public class SignatureProtocol {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java SignatureProtocol text");
            System.exit(1);
        }

        // 1. Recuperer le texte
        byte[] plainText = args[0].getBytes("UTF8");
        System.out.println("=== Protocole de Signature Numerique ===\n");
        System.out.println("1. Texte en clair : " + args[0]);

        // 2. Generer le condensat SHA-512
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        System.out.println("\n2. Provider du condensat : " + messageDigest.getProvider().getInfo());
        messageDigest.update(plainText);
        byte[] digest = messageDigest.digest();
        System.out.println("   Condensat genere (SHA-512), taille : " + digest.length + " octets");
        System.out.println("   Condensat (hex) : " + bytesToHex(digest));

        // 3. Creer paire de cles RSA emetteur (2048 bits)
        KeyPairGenerator keyGenEmetteur = KeyPairGenerator.getInstance("RSA");
        keyGenEmetteur.initialize(2048);
        KeyPair keyEmetteur = keyGenEmetteur.generateKeyPair();
        System.out.println("\n3. Paire de cles RSA emetteur generee (2048 bits)");

        // 4. Creer paire de cles RSA destinataire (1024 bits)
        KeyPairGenerator keyGenDestinataire = KeyPairGenerator.getInstance("RSA");
        keyGenDestinataire.initialize(1024);
        KeyPair keyDestinataire = keyGenDestinataire.generateKeyPair();
        System.out.println("4. Paire de cles RSA destinataire generee (1024 bits)");

        // 5. Recuperer le Cipher RSA/ECB/PKCS1Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        System.out.println("\n5. Provider du Cipher : " + cipher.getProvider().getInfo());

        // 6. Chiffrer le condensat avec la cle privee de l'emetteur
        cipher.init(Cipher.ENCRYPT_MODE, keyEmetteur.getPrivate());
        byte[] digestChiffre = cipher.doFinal(digest);
        System.out.println("\n6. Condensat chiffre avec cle privee emetteur (taille : " + digestChiffre.length + " octets)");
        System.out.println("   Condensat chiffre (hex) : " + bytesToHex(digestChiffre).substring(0, 80) + "...");

        // 7. Chiffrer le texte en clair avec la cle publique du destinataire
        cipher.init(Cipher.ENCRYPT_MODE, keyDestinataire.getPublic());
        byte[] messageChiffre = cipher.doFinal(plainText);
        System.out.println("\n7. Message chiffre avec cle publique destinataire (taille : " + messageChiffre.length + " octets)");
        System.out.println("   Message chiffre (hex) : " + bytesToHex(messageChiffre).substring(0, 80) + "...");

        // === Cote destinataire ===
        System.out.println("\n=== Reception par le destinataire ===");

        // 8. Dechiffrer le condensat avec la cle publique de l'emetteur
        cipher.init(Cipher.DECRYPT_MODE, keyEmetteur.getPublic());
        byte[] digestDechiffre = cipher.doFinal(digestChiffre);
        System.out.println("\n8. Condensat dechiffre avec cle publique emetteur");
        System.out.println("   Condensat dechiffre (hex) : " + bytesToHex(digestDechiffre));

        // 9. Dechiffrer le message avec la cle privee du destinataire
        cipher.init(Cipher.DECRYPT_MODE, keyDestinataire.getPrivate());
        byte[] messageDechiffre = cipher.doFinal(messageChiffre);
        System.out.println("\n9. Message dechiffre : " + new String(messageDechiffre, "UTF8"));

        // 10. Generer condensat sur le message dechiffre
        messageDigest.reset();
        messageDigest.update(messageDechiffre);
        byte[] digestVerification = messageDigest.digest();
        System.out.println("\n10. Condensat du message dechiffre genere");
        System.out.println("    Condensat verification (hex) : " + bytesToHex(digestVerification));

        // 11. Comparer les deux condensats octet par octet
        System.out.println("\n11. Comparaison des condensats octet par octet :");
        boolean identiques = true;
        for (int i = 0; i < digestDechiffre.length; i++) {
            if (digestDechiffre[i] != digestVerification[i]) {
                System.out.println("    ERREUR a l'octet " + i + " : " + digestDechiffre[i] + " != " + digestVerification[i]);
                identiques = false;
            }
        }
        if (identiques) {
            System.out.println("    => Les deux condensats sont IDENTIQUES. Integrite verifiee !");
        } else {
            System.out.println("    => Les condensats sont DIFFERENTS. Integrite compromise !");
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
