import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 3/15/18.
 */
public class Util {

    public static List<Boolean> shiftBit(List<Boolean> data, int shift, boolean isLeft){

        if(!isLeft){
            shift += data.size()-1;
        }

        List<Boolean> shifted = data;

        for(int i = 0; i < data.size() - 1 ; i++){
            shifted.set(i,data.get((i+shift)%(data.size()-1)));

        }

        return shifted;
    }

    public static List<Boolean> concat(List<Boolean> data1, List<Boolean> data2) {

        List<Boolean> retour = new ArrayList<Boolean>();

        for(int i = 0 ; i < data1.size() ; i++) {
            retour.set(i, data1.get(i));
            retour.set(i + 5, data2.get(i + 5));
        }

        return retour;
    }

    public static List<List<Boolean>> split(List<Boolean> data) {

        List<List<Boolean>> retour = new ArrayList<List<Boolean>>();

        List<Boolean> half1 = data.subList(0, (data.size()-1)/2);
        List<Boolean> half2 = data.subList((data.size()-1)/2, data.size()-1);

        retour.add(half1);
        retour.add(half2);

        return retour;
    }

    public static String booleanToBinaryString(List<Boolean> data){
        StringBuilder string = new StringBuilder();

        for(int i = 0 ; i < data.size() - 1 ; i++){
            if(data.get(i)){
                string.append(1);
            } else{
                string.append(0);
            }
        }
        return string.toString();
    }
}