import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.math.BigInteger;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStore.SecretKeyEntry;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStore.TrustedCertificateEntry;



public class StoringAndLoadingFromKeyStoreExample_01{
    public static void main(String args[]) throws Exception{
   
   	final String PASSWORD = "MY_PASSWORD";
   	final String SECRET_KEY_ALGO = "AES";
   	final String SECRET_KEY_ALIAS = "secretKeyAlias";
   	final String KEYSTORE_FILENAME = "myKeystore";
	final String PRIVATE_KEY_ALGO = "RSA";
	final int PRIVATE_KEY_SIZE = 2048;
	final String CERTIFICATE_CHAIN_FILENAME = "ca-chain.cert.pem";
	final String CERTIFICATE_TYPE = "X.509";
	final String PRIVATE_KEY_ALIAS = "privateKeyAlias";
	final String TRUSTED_CERTIFICATE_ALIAS = "trustedCertAlias";

   	KeyStore keyStoreToStore = KeyStore.getInstance(KeyStore.getDefaultType());   
   	
   	char[] password = PASSWORD.toCharArray();
   	keyStoreToStore.load(null, password);
	
	SecretKey secretKeyToStore = new SecretKeySpec(PASSWORD.getBytes(), SECRET_KEY_ALGO);	
	System.out.println("Algorithm to generate secret key : "+secretKeyToStore.getAlgorithm());   
	System.out.println("Format used for the key: "+secretKeyToStore.getFormat());
   
   	KeyStore.SecretKeyEntry secretKeyEntryToStore = new KeyStore.SecretKeyEntry(secretKeyToStore);
   	KeyStore.ProtectionParameter protParamSecretKeyToStore = new KeyStore.PasswordProtection(password);
   	keyStoreToStore.setEntry(SECRET_KEY_ALIAS, secretKeyEntryToStore, protParamSecretKeyToStore); 
   	
	KeyPairGenerator asymmetricKeysGenerator = KeyPairGenerator.getInstance(PRIVATE_KEY_ALGO);
	asymmetricKeysGenerator.initialize(PRIVATE_KEY_SIZE);
	KeyPair asymmetricKeysToStore = asymmetricKeysGenerator.generateKeyPair();
	PrivateKey privateKeyToStore = asymmetricKeysToStore.getPrivate();	
	System.out.println("Algorithm to generate private key : " + privateKeyToStore.getAlgorithm());   
	System.out.println("Format used for the key: " + privateKeyToStore.getFormat());
	
	FileInputStream fisCert = new FileInputStream(CERTIFICATE_CHAIN_FILENAME); 
	BufferedInputStream bisCert = new BufferedInputStream(fisCert);
	CertificateFactory cfCert = CertificateFactory.getInstance(CERTIFICATE_TYPE);
	Certificate[] x509CertificateChain = new Certificate[2];

	int i = 0;
	while (bisCert.available() > 0 && i < x509CertificateChain.length ) {
	    Certificate cert = cfCert.generateCertificate(bisCert);	    
	    System.out.println("Cert type = " + cfCert.getType());
	    x509CertificateChain[i] = cert;
	    i++;
	    System.out.println(cert.toString());
	}
	keyStoreToStore.setKeyEntry(PRIVATE_KEY_ALIAS, privateKeyToStore, password, x509CertificateChain);
	
	keyStoreToStore.setCertificateEntry(TRUSTED_CERTIFICATE_ALIAS, x509CertificateChain[0]);
	
   	java.io.FileOutputStream fos = null;
   	try {
	    fos = new FileOutputStream(KEYSTORE_FILENAME);
	    keyStoreToStore.store(fos, password);
	    System.out.println("KeyStore stored\n\n");
	} catch(IOException e) {
	    e.printStackTrace();
	}  
   
   	KeyStore keyStoreToLoad = KeyStore.getInstance(KeyStore.getDefaultType());
   	java.io.FileInputStream fis = null;
   	try {
	    fis = new FileInputStream(KEYSTORE_FILENAME);
	    keyStoreToLoad.load(fis, password);
	    System.out.println("KeyStore loaded");
	} catch(IOException e) {
	    e.printStackTrace();
	}  
    
	KeyStore.ProtectionParameter protParamToLoad = new KeyStore.PasswordProtection(password);
	SecretKeyEntry secretKeyEntryToLoad = (SecretKeyEntry)keyStoreToLoad.getEntry(SECRET_KEY_ALIAS, protParamToLoad);
	SecretKey secretKeyToLoad = secretKeyEntryToLoad.getSecretKey();      
	System.out.println("Algorithm to generate secret key : "+secretKeyToLoad.getAlgorithm());   
	System.out.println("Format used for the key: "+secretKeyToLoad.getFormat());

	PrivateKeyEntry privateKeyEntryToLoad = (PrivateKeyEntry) keyStoreToLoad.getEntry(PRIVATE_KEY_ALIAS, protParamToLoad);
	PrivateKey privateKeyToLoad = privateKeyEntryToLoad.getPrivateKey();
	System.out.println("Algorithm to generate private key : " + privateKeyToLoad.getAlgorithm());   
	System.out.println("Format used for the key: " + privateKeyToLoad.getFormat());

	TrustedCertificateEntry trustedCertEntryToLoad = (TrustedCertificateEntry) keyStoreToLoad.getEntry(TRUSTED_CERTIFICATE_ALIAS, null); 
	Certificate trustedCertToLoad = trustedCertEntryToLoad.getTrustedCertificate();
	System.out.println("trustedCertToLoad : " + trustedCertToLoad);
    } 
}   	 

