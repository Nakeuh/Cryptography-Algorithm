
import java.util.ArrayList;
import java.util.List;


/**
 * Created by victor on 3/15/18.
 */
public class Main {

    public static void main(String [] args){
        testDES();
    }

    public static void testDES(){
        // Setup
        DES des = new DES();
        String message = "Un super message";
        String key = "a";

        System.out.println("Message à envoyer : " + message);
        System.out.println("Clé : " + key);

        String messageToSend = des.encryptAsciiMessage8Bits(message, key);
        System.out.println(messageToSend);

        String messageDecrypt = des.decryptAsciiMessage8Bits(messageToSend, key);
        System.out.println(messageDecrypt);
    }

    public static void testRSA(){
        RSA rsa = new RSA();
        String message = "Un super message";
        String publicKey;
        String privateKey;
    }
}
