package Crypto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 3/15/18.
 */
public class DES implements Constantes {

    public List<Boolean> encrypt(List<Boolean> message, List<Boolean> key){

        List<Boolean> retour;

        // Première permutation sur le message avec IP
        List<Boolean> initialPermutResult = permute(message, Constantes.IP);

        // Récupération des clés d'étapes
        List<List<Boolean>> keys = keyGeneration(key);

        // Première étape
        List<List<Boolean>> fkResult = fk(initialPermutResult, keys.get(0));

        List<List<Boolean>> fkResultSwitched = Util.switchList(fkResult);
        List<Boolean> concatFkResultatSwitched = Util.concat(fkResultSwitched.get(0), fkResultSwitched.get(1));

        // Deuxième étape
        List<List<Boolean>> fkResult2 = fk(concatFkResultatSwitched, keys.get(1));
        concatFkResultatSwitched = Util.concat(fkResult2.get(0), fkResult2.get(1));

        // Permutation avec IP-1
        retour = permute(concatFkResultatSwitched, Constantes.INVERT_IP);

        return retour;
    }

    public List<Boolean> decrypt(List<Boolean> crypted, List<Boolean> key){
        List<Boolean> retour;

        // Première permutation sur le message avec IP
        List<Boolean> initialPermutResult = permute(crypted, Constantes.IP);

        // Récupération des clés d'étapes
        List<List<Boolean>> keys = keyGeneration(key);

        // Première étape
        List<List<Boolean>> fkResult = fk(initialPermutResult, keys.get(1));

        List<List<Boolean>> fkResultSwitched = Util.switchList(fkResult);
        List<Boolean> concatFkResultatSwitched = Util.concat(fkResultSwitched.get(0), fkResultSwitched.get(1));

        // Deuxième étape
        List<List<Boolean>> fkResult2 = fk(concatFkResultatSwitched, keys.get(0));
        concatFkResultatSwitched = Util.concat(fkResult2.get(0), fkResult2.get(1));

        // Permutation avec IP-1
        retour = permute(concatFkResultatSwitched, Constantes.INVERT_IP);

        return retour;
    }

    private List<List<Boolean>> keyGeneration(List<Boolean> key){

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

    private List<Boolean> permute(List<Boolean> bits, int[] permutations){
        List<Boolean> retour = new ArrayList<Boolean>();

        for(int i = 0 ; i < permutations.length ; i++){
            retour.add(bits.get(permutations[i] - 1));
        }

        return retour;
    }

    private List<List<Boolean>> fk(List<Boolean> data, List<Boolean> key) {

        List<List<Boolean>> retour = new ArrayList<List<Boolean>>();

        // On splitte (deux listes de taille 8)
        List<List<Boolean>> arraySplit = Util.split(data);

        // On permute la deuxième liste (liste de droite) avec EP
        List<Boolean> extendedPermuted = permute(arraySplit.get(1), Constantes.E_P);

        // Ou Logique entre le résultat de la permutation et la clé
        List<Boolean> orResult = Util.or(extendedPermuted, key);

        // On splitte (deux listes de booleans de taille 4)
        List<List<Boolean>> orResultSplit = Util.split(orResult);

        // On fait appel deux fois à sbox avec nos deux listes)
        List<Boolean> sboxResult1 = sbox(orResultSplit.get(0), Constantes.S0);
        List<Boolean> sboxResult2 = sbox(orResultSplit.get(0), Constantes.S1);

        // On concatène les résultats
        List<Boolean> sboxConcat = Util.concat(sboxResult1, sboxResult2);

        // On permute avec P4
        List<Boolean> permuted = permute(sboxConcat, P4);

        // On fait un ou exclusif entre permuted et le premier morceau qu'on a jamais utilisé pour l'instant
        retour.add(Util.or(permuted, arraySplit.get(0)));

        // On ajoute à retour le deuxième morceau
        retour.add(arraySplit.get(1));

        return retour;

    }

    private List<Boolean> sbox(List<Boolean> data, int[] S) {

        List<Boolean> retour;

        Boolean firstValue = data.get(0);
        Boolean lastValue = data.get(3);
        Boolean secondValue = data.get(1);
        Boolean thirdValue = data.get(2);

        int ligne = Util.booleanToInt(firstValue, lastValue);
        int colonne = Util.booleanToInt(secondValue, thirdValue);

        int value = S[ligne * 4 + colonne];

        retour = Util.intToBoolean(value);

        return retour;

    }
}