import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by victor on 3/15/18.
 */
public class Util {

    public static String bitsetToString(BitSet data){
        return data.toString();
    }

    public static BitSet shiftBit(BitSet data, int shift, boolean isLeft){

        if(!isLeft){
            shift += data.size()-1;
        }

        BitSet shifted = data;

        for(int i=0; i<data.size()-1;i++){
            shifted.set(i,data.get((i+shift)%(data.size()-1)));

        }

        return shifted;
    }

    public static BitSet concat(BitSet data1, BitSet data2) {

        BitSet retour = new BitSet();

        for(int i = 0 ; i < data1.size() ; i++) {
            retour.set(i, data1.get(i));
            retour.set(i + 5, data1.get(i + 5));
        }

        return retour;
    }

    public static BitSet[] split(BitSet data) {

        BitSet[] retour = new BitSet[2];

        BitSet half1 = data.get(0,(data.size()-1)/2);
        BitSet half2 = data.get((data.size()-1)/2, data.size()-1);

        retour[0] = half1;
        retour[1] = half2;

        return retour;
    }

    public static String bitsetToBinaryString(BitSet data){
        StringBuilder string = new StringBuilder();

        for(int i=0;i<data.size()-1;i++){
            if(data.get(i)){
                string.append(1);
            }else{
                string.append(0);
            }
        }
        return string.toString();
    }
}
