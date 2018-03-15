import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by victor on 3/15/18.
 */
public class DES implements Constantes{

    public BitSet encrypt(BitSet message, BitSet key){
        BitSet retour = new BitSet();

        // Première permutation sur le message avec IP
        permute(message, Constantes.IP);

        // Récupération des clés d'étapes
        List<BitSet> keys = keyGeneration(key);

        // Première étape


        // Deuxième étape

        // Permutation avec IP-1
        permute(message, Constantes.INVERT_IP);

        return retour;
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

    public boolean[] permute(boolean[] bits, int[] permutations){
        boolean[] resBitSet = new boolean[bits.length];

        for(int i=0; i <bits.length; i++){
            resBitSet[i] = bits[permutations[i]];
        }
        return resBitSet;
    }

    public BitSet sbox(){
        return null;
    }

    public BitSet[] fk(BitSet data, BitSet key) {
        return null;
    }
}
