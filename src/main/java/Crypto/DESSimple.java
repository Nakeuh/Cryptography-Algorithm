package Crypto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 3/15/18.
 */
public class DESSimple extends DES {

    /* Longueur de la clé attendu : 10 bits */
    public String encrypt(String message, String key){

        String retour = "";

        List<Boolean> keyBoolean = Util.binaryStringToBooleanList(key);

        // Récupération des clés d'étapes
        List<List<Boolean>> keys = keyGeneration(keyBoolean);

        int i = 0;
        while(i < message.length()) {

            // On convertit notre bloc en List<Boolean>
            List<Boolean> currentBooleanList = Util.getCurrentBooleanList(message.substring(i, i+1));

            // Première permutation sur le bloc avec IP
            List<Boolean> initialPermutResult = permute(currentBooleanList, Constantes.IP);

            // Déroulement des étapes
            List<Boolean> etapeResult = etapesEncrypt(initialPermutResult, keys, 2);

            // Permutation finale
            List<Boolean> blocFinalResult = permute(etapeResult, Constantes.INVERT_IP);

            // Conversion de la liste de booléens encrypté vers sa chaîne de charactères binaire
            String binaryStringEncrypted = Util.booleanListToBinaryString(blocFinalResult);

            // Récupération du charactère ASCII au format encrypté
            retour += Util.binaryStringToChar(binaryStringEncrypted);

            i++;
        }

        return retour;
    }

    public String decrypt(String crypted, String key){

        String retour = "";

        List<Boolean> keyBoolean = Util.binaryStringToBooleanList(key);

        // Récupération des clés d'étapes
        List<List<Boolean>> keys = keyGeneration(keyBoolean);

        int i = 0;
        while(i < crypted.length()) {

            // On convertit notre bloc en List<Boolean>
            List<Boolean> currentBooleanList = Util.getCurrentBooleanList(crypted.substring(i, i+1));

            // Première permutation sur le bloc avec IP
            List<Boolean> initialPermutResult = permute(currentBooleanList, Constantes.IP);

            // Déroulement des étapes
            List<Boolean> etapeResult = etapesDecrypt(initialPermutResult, keys, 2);

            // Permutation finale
            List<Boolean> blocFinalResult = permute(etapeResult, Constantes.INVERT_IP);

            // Conversion de la liste de booléens encrypté vers sa chaîne de charactères binaire
            String binaryStringEncrypted = Util.booleanListToBinaryString(blocFinalResult);

            // Récupération du charactère ASCII au format encrypté
            retour += Util.binaryStringToChar(binaryStringEncrypted);

            i++;
        }

        return retour;
    }

    public List<List<Boolean>> keyGeneration(List<Boolean> key){

        // Initialisation : Permutation avec P10
        List<Boolean> keyPermuteP10 = permute(key, Constantes.P10);

        // Split
        List<List<Boolean>> halfs = Util.split(keyPermuteP10);

        return etapesKeyGeneration(halfs, 2, Constantes.P8);

    }

    public List<List<Boolean>> fk(List<Boolean> data, List<Boolean> key) {

        List<List<Boolean>> retour = new ArrayList<List<Boolean>>();

        // On splitte (deux listes de taille 8)
        List<List<Boolean>> arraySplit = Util.split(data);

        // On permute la deuxième liste (liste de droite) avec EP (expension permutation)
        List<Boolean> extendedPermuted = permute(arraySplit.get(1), Constantes.E_P);

        // Ou Logique entre le résultat de la permutation et la clé
        List<Boolean> orResult = Util.or(extendedPermuted, key);

        // On splitte (deux listes de booleans de taille 4)
        List<List<Boolean>> orResultSplit = Util.split(orResult);

        // On fait appel deux fois à sbox avec nos deux listes)
        List<Boolean> sboxResult1 = sbox(orResultSplit.get(0), Constantes.S0);
        List<Boolean> sboxResult2 = sbox(orResultSplit.get(1), Constantes.S1);

        // On concatène les résultats
        List<Boolean> sboxConcat = Util.concat(sboxResult1, sboxResult2);

        // On permute avec P4
        List<Boolean> permuted = permute(sboxConcat, Constantes.P4);

        // On fait un ou exclusif entre permuted et le premier morceau qu'on a jamais utilisé pour l'instant
        retour.add(Util.or(permuted, arraySplit.get(0)));

        // On ajoute à retour le deuxième morceau
        retour.add(arraySplit.get(1));

        return retour;
    }

    private List<Boolean> sbox(List<Boolean> data, int[] S) {

        List<Boolean> retour;

        Boolean firstValue = data.get(0);
        Boolean lastValue = data.get(data.size() - 1);
        Boolean secondValue = data.get(1);
        Boolean thirdValue = data.get(data.size() - 2);

        int ligne = Util.booleanToInt(firstValue, lastValue);
        int colonne = Util.booleanToInt(secondValue, thirdValue);

        int value = S[ligne * 4 + colonne];
        retour = Util.intToBoolean(value, 2);

        return retour;
    }

}