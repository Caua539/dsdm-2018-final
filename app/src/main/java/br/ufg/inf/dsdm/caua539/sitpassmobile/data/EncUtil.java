package br.ufg.inf.dsdm.caua539.sitpassmobile.data;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.util.Base64;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.List;

import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;

public class EncUtil {

    static List<String> keyAliases;
    static KeyStore keyStore;
    private static final String alias = "MASTER";
    static Context app;

    public static void createKeyStore(Context context){
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
        }
        catch(Exception e) {}
        app = context;
        createNewKeys(app);
    }

    public static void createNewKeys(Context context) {
        try {
            // Create new key if needed
            if (!keyStore.containsAlias(alias)) {
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 5);
                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                        .setAlias(alias)
                        .setSubject(new X500Principal("CN="+alias+", O=Android Authority"))
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build();
                KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
                generator.initialize(spec);

                KeyPair keyPair = generator.generateKeyPair();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Exception " + e.getMessage() + " occured", Toast.LENGTH_LONG).show();
        }
    }



    public static String encryptString(String s) {
        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, null);
            RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();

            Cipher inCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            inCipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] cipherText = inCipher.doFinal(s.getBytes("UTF-8"));
            String b = Base64.encodeToString(cipherText, Base64.DEFAULT);

            return b;
        } catch (Exception e) {
            Toast.makeText(app, "Exception " + e.getMessage() + " occured", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public static String decryptString(String s) {
        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(alias, null);
            RSAPrivateKey privateKey = (RSAPrivateKey) privateKeyEntry.getPrivateKey();

            Cipher output = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            output.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] cypherText = Base64.decode(s, Base64.DEFAULT);

            String decryptString = new String(output.doFinal(cypherText), "UTF-8");

            return decryptString;

        } catch (Exception e) {
            Toast.makeText(app, "Exception " + e.getMessage() + " occured", Toast.LENGTH_LONG).show();
        }
        return null;
    }
}
