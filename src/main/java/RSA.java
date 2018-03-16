import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.BitSet;
import java.util.List;

/**
 * Created by victor on 3/15/18.
 */
public class RSA implements Constantes{

    public KeyPair generateKey(int size){
        KeyPair kp = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(size);

            kp = kpg.generateKeyPair();
        }catch(Exception e){
            e.printStackTrace();
        }

        return kp;
    }

    public byte[] encrypt(String message, Key publicKey){
        byte[] output=null;

        try {
            Cipher rsa = Cipher.getInstance("RSA");

            rsa.init(Cipher.ENCRYPT_MODE, publicKey);

            output = rsa.doFinal(message.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    public String decrypt(byte[] crypted, Key privateKey){
        String output = "";

        try {
            Cipher rsa = Cipher.getInstance("RSA");

            rsa.init(Cipher.DECRYPT_MODE, privateKey);

            output = new String(rsa.doFinal(crypted), "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
