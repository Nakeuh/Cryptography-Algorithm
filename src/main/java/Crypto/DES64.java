package Crypto;

import java.util.ArrayList;
import java.util.List;

public class DES64 extends DES {

    /* Longueur de la clé attendu : 64 bits */
    public String encrypt(String message, String key) {

        String retour = "";

        List<Boolean> keyBoolean = Util.binaryStringToBooleanList(key);

        // Récupération des clés d'étapes
        List<List<Boolean>> keys = keyGeneration(keyBoolean);

        int i = 0;

        // On découpe notre message en bloc de 64 bits pour encrypter chacun des blocs avec DES64
        while (i < message.length()) {

            // On convertit notre bloc en List<Boolean>
            List<Boolean> currentBooleanList;
            if(i + 8 > message.length()) {
                currentBooleanList = Util.getCurrentBooleanList(message.substring(i, message.length()));
                int size = 64 - currentBooleanList.size();
                for(int j = 0 ; j < size ; j++ ){
                    currentBooleanList.add(false);
                }
            } else {
                currentBooleanList = Util.getCurrentBooleanList(message.substring(i, i + 8));
            }

            // Première permutation sur le bloc avec IP
            List<Boolean> initialPermutResult = permute(currentBooleanList, Constantes64.IP);

            // Déroulement des étapes (16 étapes pour DES 64)
            List<Boolean> etapeResult = etapesEncrypt(initialPermutResult, keys, 16);

            // Permutation finale
            List<Boolean> blocFinalResult = permute(etapeResult, Constantes64.INVERT_IP);

            // Conversion de la liste de booléens encrypté vers sa chaîne de charactères binaire
            String binaryStringEncrypted = Util.booleanListToBinaryString(blocFinalResult);

            // Récupération du charactère ASCII au format encrypté
            retour += Util.binaryStringToChar(binaryStringEncrypted);

            i = i + 8;

        }

        return retour;
    }

    public String decrypt(String crypted, String key) {

        String retour = "";

        List<Boolean> keyBoolean = Util.binaryStringToBooleanList(key);

        // Récupération des clés d'étapes
        List<List<Boolean>> keys = keyGeneration(keyBoolean);

        int i = 0;
        while (i < crypted.length()) {

            // On convertit notre bloc en List<Boolean>
            List<Boolean> currentBooleanList;
            if(i + 8 > crypted.length()) {
                currentBooleanList = Util.getCurrentBooleanList(crypted.substring(i, crypted.length()));
                int size = 64 - currentBooleanList.size();
                for(int j = 0 ; j < size ; j++ ){
                    currentBooleanList.add(false);
                }
            } else {
                currentBooleanList = Util.getCurrentBooleanList(crypted.substring(i, i + 8));
            }

            // Première permutation sur le bloc avec IP
            List<Boolean> initialPermutResult = permute(currentBooleanList, Constantes64.IP);

            // Déroulement des étapes
            List<Boolean> etapeResult = etapesDecrypt(initialPermutResult, keys, 16);

            // Permutation finale
            List<Boolean> blocFinalResult = permute(etapeResult, Constantes64.INVERT_IP);

            // Conversion de la liste de booléens encrypté vers sa chaîne de charactères binaire
            String binaryStringEncrypted = Util.booleanListToBinaryString(blocFinalResult);

            // Récupération du charactère ASCII au format encrypté
            retour += Util.binaryStringToChar(binaryStringEncrypted);

            i = i + 8;

        }

        return retour;
    }

    public List<List<Boolean>> keyGeneration(List<Boolean> key) {

        // Initialisation : Permutation
        List<Boolean> keyPermute = permute(key, Constantes64.KPI);

        // Split
        List<List<Boolean>> halfs = Util.split(keyPermute);

        return etapesKeyGeneration(halfs, 16, Constantes64.KP2);

    }

    public List<List<Boolean>> fk(List<Boolean> data, List<Boolean> key) {

        List<List<Boolean>> retour = new ArrayList<List<Boolean>>();

        // On splitte (deux listes de taille 32)
        List<List<Boolean>> arraySplit = Util.split(data);

        // On permute la deuxième liste (liste de droite) avec EP
        List<Boolean> extendedPermuted = permute(arraySplit.get(1), Constantes64.E);

        // Ou Logique entre le résultat de la permutation et la clé
        List<Boolean> orResult = Util.or(extendedPermuted, key);

        // On scinde en 8 blocs de 6 bits
        List<List<Boolean>> orResultSplit = Util.splitEnHuit(orResult);

        // On fait appel 8 fois a sbox avec nos 8 blocs
        List<Boolean> sboxResult1 = sbox(orResultSplit.get(0), Constantes64.S0);
        List<Boolean> sboxResult2 = sbox(orResultSplit.get(1), Constantes64.S1);
        List<Boolean> sboxResult3 = sbox(orResultSplit.get(2), Constantes64.S2);
        List<Boolean> sboxResult4 = sbox(orResultSplit.get(3), Constantes64.S3);
        List<Boolean> sboxResult5 = sbox(orResultSplit.get(4), Constantes64.S4);
        List<Boolean> sboxResult6 = sbox(orResultSplit.get(5), Constantes64.S5);
        List<Boolean> sboxResult7 = sbox(orResultSplit.get(6), Constantes64.S6);
        List<Boolean> sboxResult8 = sbox(orResultSplit.get(7), Constantes64.S7);

        // On concatène les résultats
        List<Boolean> sboxConcat = new ArrayList<Boolean>();
        sboxConcat.addAll(sboxResult1);
        sboxConcat.addAll(sboxResult2);
        sboxConcat.addAll(sboxResult3);
        sboxConcat.addAll(sboxResult4);
        sboxConcat.addAll(sboxResult5);
        sboxConcat.addAll(sboxResult6);
        sboxConcat.addAll(sboxResult7);
        sboxConcat.addAll(sboxResult8);

        // On permute avec P32
        List<Boolean> permuted = permute(sboxConcat, Constantes64.P32);

        // On fait un ou exclusif entre permuted et le premier morceau qu'on a jamais utilisé pour l'instant
        retour.add(Util.or(permuted, arraySplit.get(0)));

        // On ajoute à retour le deuxième morceau
        retour.add(arraySplit.get(1));

        return retour;
    }

    private List<Boolean> sbox(List<Boolean> data, int[] S) {

        List<Boolean> retour;

        Boolean firstValue = data.get(0);
        Boolean secondValue = data.get(1);
        Boolean thirdValue = data.get(2);
        Boolean fourthValue = data.get(3);
        Boolean fifthValue = data.get(4);
        Boolean lastValue = data.get(5);

        // On récupère le numéro de ligne avec la première et la dernière valeur
        int ligne = Util.booleanToInt(firstValue, lastValue);

        // On récupère le numéro de colonne avec les 4 valeurs du milieu
        int colonne = Util.booleanToInt(secondValue, thirdValue, fourthValue, fifthValue);

        int value = S[ligne * 16 + colonne];

        // On aura alors un résultat sur 4 bits
        retour = Util.intToBoolean(value, 4);

        return retour;
    }
}