import java.util.BitSet;

/**
 * Created by victor on 3/15/18.
 */
public class Main {

    public static void main(String [] args){
        String message = "Je suis un message";
        String key = "Wolololo";

        BitSet keyB = BitSet.valueOf(key.getBytes());
        System.out.println("keyB : "+Util.bitsetToBinaryString(keyB));

        keyB = Util.shiftBit(keyB,1,true);


        System.out.println("keyB : "+Util.bitsetToBinaryString(keyB));



       // testDES(message,key);

    }

    public static void testDES(String message, String key){
        DES des = new DES();

        BitSet messageB = BitSet.valueOf(message.getBytes());
        BitSet keyB = BitSet.valueOf(key.getBytes());


        des.encrypt(messageB,keyB);

        des.decrypt(messageB,keyB);

    }

    public static void testRSA(){

    }
}
