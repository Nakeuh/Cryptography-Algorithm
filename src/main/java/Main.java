
import Crypto.DES64;
import Crypto.DESSimple;
import Crypto.RSA;

import java.security.KeyPair;


/**
 * Created by victor on 3/15/18.
 */
public class Main {

    public static void main(String [] args){
        testDES();
        //testRSA();
    }

    private static void testDES(){

        // Message que l'on souhaite chiffrer
        String message = "Un super message !";

        // Setup DES Simple
        DESSimple desSimple = new DESSimple();
        String key = "1000110000";

        // Setup 64
        DES64 des64 = new DES64();
        String key64 = "0011100110000101010000011101011001001110110001011100000001100010";

        System.out.println("Message de départ : " + message);
        System.out.println("Clé simple: " + key);
        System.out.println("Clé DES 64: " + key64);

        // On encrypte
        String messageToSend = desSimple.encrypt(message, key);
        System.out.println("Message encrypté via DES Simple : " + messageToSend);

        // On décrypte
        String messageDecrypted = desSimple.decrypt(messageToSend, key);
        System.out.println("Message decrypté via DES Simple : " + messageDecrypted);

        // On encrypte
        messageToSend = des64.encrypt(message, key64);
        System.out.println("Message encrypté via DES 64 : " + messageToSend);

        // On décrypte
        messageDecrypted = des64.decrypt(messageToSend, key64);
        System.out.println("Message decrypté via DES 64 : " + messageDecrypted);
    }

    private static void testRSA(){
        RSA rsa = new RSA();
        String message = "Un super message";

        KeyPair keys = rsa.generateKey(2048);

        byte[] crypted = rsa.encrypt(message, keys.getPublic());

        String output = rsa.decrypt(crypted, keys.getPrivate());

        System.out.println("Inpput message RSA : " + message);
        System.out.println("Output message RSA : " + output);

    }
}
