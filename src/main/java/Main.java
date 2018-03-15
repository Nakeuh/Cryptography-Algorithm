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

        String[] temp =key.split("");

        List<Boolean> list_key = new ArrayList<Boolean>(10);

        for (int i=0; i<key.length(); i++){

            System.out.println(temp[i]);
            if( Integer.parseInt(temp[i]) == 1){
                list_key.add(true);
            }else{
                list_key.add(false);
            }
        }

        System.out.println(list_key);
    }

    public static void testDES(String message, String key){
    }

    public static void testRSA(){
    }
}
