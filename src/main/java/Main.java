
import java.util.ArrayList;
import java.util.List;


/**
 * Created by victor on 3/15/18.
 */
public class Main {

    public static void main(String [] args){

        // Setup
        DES des = new DES();
        String message = "Un super message";
        String key = "1000000000";
        List<Boolean> keyBoolean = Util.binaryStringToBooleanList(key);
        List<List<Boolean>> booleanListMessageChiffe = new ArrayList<List<Boolean>>();
        String messageToSend = "";

        char[] charArrayMessage = message.toCharArray();

        // On encrypte les charactère one by one
        for (char currentChar : charArrayMessage){

            // Conversion du charactère vers sa version binaryString (calculé à partir de son code ASCII decimal)
            String charToBinaryString = Util.charToBinaryString(currentChar);
            //System.out.println(charToBinaryString);

            // Conversion de la chaîne de charactères binaire vers une liste de booleens
            List<Boolean> messageBoolean = Util.binaryStringToBooleanList(charToBinaryString);
            //Util.printBooleanListFormat(messageBoolean);

            // Version encryptée de la liste de booléens
            List<Boolean> booleanListEncrypted =  des.encrypt(messageBoolean, keyBoolean);
            //Util.printBooleanListFormat(booleanListEncrypted);

            // Conversion de la liste de booléens encrypté vers sa chaîne de charactères binaire
            String binaryStringEncrypted = Util.booleanListToBinaryString(booleanListEncrypted);

            // Récupération du charactère ASCII au format encrypté
            String charToAddOnMessage = Util.binaryStringToChar(binaryStringEncrypted);

            // Ajout du characère encrypté au message à envoyer
            messageToSend = messageToSend + charToAddOnMessage;
        }
        System.out.println("Message encrypté : " + messageToSend);

        char[] charArrayMessageToDecrypt = messageToSend.toCharArray();
        String messageDecrypted = "";
        // On decrypte les charactère one by one
        for (char currentChar : charArrayMessageToDecrypt){

            // Conversion du charactère vers sa version binaryString (calculé à partir de son code ASCII decimal)
            String charToBinaryString = Util.charToBinaryString(currentChar);

            // Conversion de la chaîne de charactères binaire vers une liste de booleens
            List<Boolean> messageBoolean = Util.binaryStringToBooleanList(charToBinaryString);

            // Version encryptée de la liste de booléens
            List<Boolean> booleanListDecrypted =  des.decrypt(messageBoolean, keyBoolean);

            // Conversion de la liste de booléens encrypté vers sa chaîne de charactères binaire
            String binaryStringDecrypted = Util.booleanListToBinaryString(booleanListDecrypted);

            // Récupération du charactère ASCII au format encrypté
            String charToAddOnMessage = Util.binaryStringToChar(binaryStringDecrypted);

            // Ajout du characère encrypté au message à envoyer
            messageDecrypted = messageDecrypted + charToAddOnMessage;
        }
        System.out.println("Message decrypté : " + messageDecrypted);
    }

    public static void testDES(String message, String key){
    }

    public static void testRSA(){
    }

}
