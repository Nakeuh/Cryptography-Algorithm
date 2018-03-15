import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by victor on 3/15/18.
 */
public class DES implements Constantes{

    public BitSet encrypt(BitSet message, BitSet key){
        return null;
    }

    public BitSet decrypt(BitSet crypted, BitSet key){
        return null;
    }

    public List<BitSet> keyGeneration(BitSet key){

        List<BitSet> retour = new ArrayList<BitSet>();

        // Initialisation : Permutation avec P10
        BitSet keyPermuteP10 = permute(key, Constantes.P10);

        // PREMIERE ETAPE
        // Split
        BitSet[] halfs = Util.split(keyPermuteP10);

        // On shift de 1 à gauche
        halfs[0] = Util.shiftBit(halfs[0], 1, true);
        halfs[1] = Util.shiftBit(halfs[1], 1, true);

        // On concatène
        BitSet concatenation = Util.concat(halfs[0], halfs[1]);

        // On permute avec P8
        BitSet keyPermuteP8 = permute(concatenation, Constantes.P8);

        retour.add(keyPermuteP8);

        // DEUXIEME ETAPE
        halfs[0] = Util.shiftBit(halfs[0], 2, true);
        halfs[1] = Util.shiftBit(halfs[1], 2, true);

        // On concatène
        concatenation = Util.concat(halfs[0], halfs[1]);
        
        // On permute avec P8
        keyPermuteP8 = permute(concatenation, Constantes.P8);

        retour.add(keyPermuteP8);

        return retour;
    }

    public BitSet substitute(){
        return null;
    }

    public BitSet permute(BitSet bits, int[] permutations){
        return null;
    }

    public BitSet sbox(){
        return null;
    }
}
