import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;


/**
 * Created by victor on 3/15/18.
 */
public class Main {

    public static void main(String [] args){
        String message = "Je suis un message";
        String key = "1001101010";

        String charToBinaryString = charToBinaryString('z');
        System.out.println("test charToBinaryString for z :" + charToBinaryString);

        List<Boolean> listBoolean = binaryStringToBooleanList(charToBinaryString);
        System.out.println("test binaryStringToBooleanList for z :" + listBoolean);

        printBooleanListFormat(listBoolean);
    }

    public static void testDES(String message, String key){
    }

    public static void testRSA(){
    }

    public static String charToBinaryString(char myChar){
        String binaryStringChar = Integer.toBinaryString((int)myChar);
        int nbrZeroToAdd = 8 - binaryStringChar.length();
        for(int i=0; i < nbrZeroToAdd; i++){
            binaryStringChar = binaryStringChar + "0";
        }
        return binaryStringChar;
    }

    public static List<Boolean> binaryStringToBooleanList(String myString){
        String[] temp = myString.split("");
        List<Boolean> booleanList = new ArrayList<Boolean>(myString.length());
        for (int i=0; i<myString.length(); i++){

            System.out.println(temp[i]);
            if( Integer.parseInt(temp[i]) == 1){
                booleanList.add(true);
            }else{
                booleanList.add(false);
            }
        }
        return booleanList;
    }

    public static void printBooleanListFormat(List<Boolean> booleanList){
        System.out.print("[");
        for ( Boolean bool : booleanList){
            if(bool){
                System.out.print("1,");
            }else{
                System.out.print("0,");
            }
        }
        System.out.print("]");
    }
}
