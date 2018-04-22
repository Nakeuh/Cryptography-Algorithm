package Crypto;

import java.util.ArrayList;
import java.util.List;

abstract class DES {

    abstract String encrypt(String message, String key);

    abstract String decrypt(String message, String key);

    abstract List<List<Boolean>> keyGeneration(List<Boolean> key);

    abstract List<List<Boolean>> fk(List<Boolean> data, List<Boolean> key);

    /**************
     *   Fonction permettant de gérer un ensemble de transformation itératives appelées rondes
     *   On y passe nos datas, nos clés ainsi que le nombre d'étapes souhaitées
     *   Pour DES 64, liste de 16 clés et 16 étapes à effectuer
     *
     *   Dans chaque étape, on fait appel à notre fonction fk, puis on switch nos deux listes et on les concatène
     *************/
    public List<Boolean> etapesEncrypt(List<Boolean> data, List<List<Boolean>> keys, int nombreEtape) {

        // Dans cette fonction, on va faire n étapes
        // Dans chaque étape, on fait appel à notre fonction fk, puis on switch nos deux listes et on les concatène
        List<List<Boolean>> fkResult = fk(data, keys.get(0));
        List<List<Boolean>> fkResultSwitched = Util.switchList(fkResult);
        List<Boolean> concatenation = Util.concat(fkResultSwitched.get(0), fkResultSwitched.get(1));

        for(int i = 1 ; i < nombreEtape ; i++) {
            fkResult = fk(concatenation, keys.get(i));

            if(i != nombreEtape - 1){
                fkResult = Util.switchList(fkResult);
            }

            concatenation = Util.concat(fkResult.get(0), fkResult.get(1));
        }

        return concatenation;
    }

    public List<Boolean> etapesDecrypt(List<Boolean> data, List<List<Boolean>> keys, int nombreEtape) {

        // Dans cette fonction, on va faire n étapes
        // Dans chaque étape, on fait appel à notre fonction fk, puis on switch nos deux listes et on les concatène
        List<List<Boolean>> fkResult = fk(data, keys.get(nombreEtape - 1));
        List<List<Boolean>> fkResultSwitched = Util.switchList(fkResult);
        List<Boolean> concatenation = Util.concat(fkResultSwitched.get(0), fkResultSwitched.get(1));

        for(int i = nombreEtape - 2 ; i >= 0 ; i--) {
            fkResult = fk(concatenation, keys.get(i));

            if(i != 0){
                fkResult = Util.switchList(fkResult);
            }

            concatenation = Util.concat(fkResult.get(0), fkResult.get(1));
        }

        return concatenation;
    }

    /**************
     *   Fonction gérant les différentes étapes de génération de notre liste de clé
     *   On y passe en paramètre notre clé de départ permuté puis splittée, ainsi que le nombre d'étapes que l'on souhaite
     *   (16 étapes pour DES64 par exemple), ainsi que la permutation que l'on souhaite utiliser
     *
     *   Pour chaque étape, les deux blocs subissent ensuite une rotation à gauche, de telles façons que les bits
     *   en seconde position prennent la première position, ceux en troisième position la seconde, ...
     *   Les bits en première position passent en dernière position.
     *
     *   Dans le cas du DES64, les 2 blocs de 28 bits sont ensuite regroupés en un bloc de 56 bits.
     *   Celui-ci passe par une permutation, fournissant en sortie un bloc de 48 bits, représentant une clé d'étape.
     *************/
    public List<List<Boolean>> etapesKeyGeneration(List<List<Boolean>> halfs, int nombreEtape, int[] permutations) {

        List<List<Boolean>> retour = new ArrayList<List<Boolean>>();

        for(int i = 1 ; i <= nombreEtape ; i++) {

            // On effectue un shift de nos bits de i bits vers la gauche (i augmentant à chaque étape)
            halfs.set(0, Util.shiftBit(halfs.get(0), i, true));
            halfs.set(1, Util.shiftBit(halfs.get(1), i, true));

            // On concatène
            List<Boolean> concatenation = Util.concat(halfs.get(0), halfs.get(1));

            // On permute
            List<Boolean> keyPermute = permute(concatenation, permutations);
            retour.add(keyPermute);
        }

        return retour;
    }

    /**************
     *   Fonction gérant les permutations
     *   On y passe en paramètre notre liste de bits de départ ainsi que le tableau de permutation
     *   Nous renvoit la liste permutée
     *************/
    public List<Boolean> permute(List<Boolean> bits, int[] permutations){
        List<Boolean> retour = new ArrayList<Boolean>();

        for(int i = 0 ; i < permutations.length ; i++){
            retour.add(bits.get(permutations[i] - 1));
        }

        return retour;
    }

}
