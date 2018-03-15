import java.util.ArrayList;

import java.util.List;

/**
 * Created by victor on 3/15/18.
 */
public class DES implements Constantes{

    public List<Boolean> encrypt(List<Boolean> message, List<Boolean> key){
        List<Boolean> retour = new ArrayList<Boolean>();

        // Première permutation sur le message avec IP
        permute(message, Constantes.IP);

        // Récupération des clés d'étapes
        List<List<Boolean>> keys = keyGeneration(key);

        // Première étape


        // Deuxième étape

        // Permutation avec IP-1
        permute(message, Constantes.INVERT_IP);

        return retour;
    }

    public List<Boolean> decrypt(List<Boolean> crypted, List<Boolean> key){
        return null;
    }

    public List<List<Boolean>> keyGeneration(List<Boolean> key){

        List<List<Boolean>> retour = new ArrayList<List<Boolean>>();

        // Initialisation : Permutation avec P10
        List<Boolean> keyPermuteP10 = permute(key, Constantes.P10);

        // PREMIERE ETAPE
        // Split
        List<List<Boolean>> halfs = Util.split(keyPermuteP10);

        // On shift de 1 à gauche
        halfs.set(0, Util.shiftBit(halfs.get(0), 1, true));
        halfs.set(1, Util.shiftBit(halfs.get(1), 1, true));

        // On concatène
        List<Boolean> concatenation = Util.concat(halfs.get(0), halfs.get(1));

        // On permute avec P8
        List<Boolean> keyPermuteP8 = permute(concatenation, Constantes.P8);

        retour.add(keyPermuteP8);

        // DEUXIEME ETAPE
        // On shift de 1 à gauche
        halfs.set(0, Util.shiftBit(halfs.get(0), 1, true));
        halfs.set(1, Util.shiftBit(halfs.get(1), 1, true));

        // On concatène
        concatenation = Util.concat(halfs.get(0), halfs.get(1));

        // On permute avec P8
        keyPermuteP8 = permute(concatenation, Constantes.P8);

        retour.add(keyPermuteP8);

        return retour;
    }

    public List<Boolean> substitute(){
        return null;
    }


    public List<Boolean> permute(List<Boolean> bits, int[] permutations){
        List<Boolean> retour = new ArrayList<Boolean>();

        for(int i=0; i < bits.size() ; i++){
            retour.add(bits.get(permutations[i]));
        }

        return retour;
    }

    public List<Boolean> sbox(){
        return null;
    }

    public List<Boolean>[] fk(List<Boolean> data, List<Boolean> key) {
        return null;
    }
}