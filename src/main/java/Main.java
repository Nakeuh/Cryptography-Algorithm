
import java.util.List;


/**
 * Created by victor on 3/15/18.
 */
public class Main {

    public static void main(String [] args){
        String message = "00000000";
        String key = "0000000000";

        //String charToBinaryString = Util.charToBinaryString('z');
        //System.out.println("test charToBinaryString for z :" + charToBinaryString);

        //List<Boolean> listBoolean = Util.binaryStringToBooleanList(charToBinaryString);
        //System.out.println("test binaryStringToBooleanList for z :" + listBoolean);

        List<Boolean> messageBoolean = Util.binaryStringToBooleanList(message);
        List<Boolean> keyBoolean = Util.binaryStringToBooleanList(key);

        DES des = new DES();
        List<Boolean> msgChiffre = des.encrypt(messageBoolean, keyBoolean);

        Util.printBooleanListFormat(messageBoolean);
        System.out.println();

        Util.printBooleanListFormat(msgChiffre);
        System.out.println();

        List<Boolean> msgDecrypte = des.decrypt(msgChiffre, keyBoolean);
        Util.printBooleanListFormat(msgDecrypte);
    }

    public static void testDES(String message, String key){



    }

    public static void testRSA(){
    }

}
