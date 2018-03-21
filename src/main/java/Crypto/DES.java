package Crypto;

import java.util.ArrayList;
import java.util.List;

abstract class DES {

    abstract String encrypt(String message, String key);

    abstract String decrypt(String message, String key);

    abstract List<List<Boolean>> keyGeneration(List<Boolean> key);

    abstract List<List<Boolean>> fk(List<Boolean> data, List<Boolean> key);

    public List<Boolean> etapesEncrypt(List<Boolean> data, List<List<Boolean>> keys, int nombreEtape) {

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

    public List<List<Boolean>> etapesKeyGeneration(List<List<Boolean>> halfs, int nombreEtape, int[] permutations) {

        List<List<Boolean>> retour = new ArrayList<List<Boolean>>();

        for(int i = 1 ; i <= nombreEtape ; i++) {
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

    public List<Boolean> permute(List<Boolean> bits, int[] permutations){
        List<Boolean> retour = new ArrayList<Boolean>();

        for(int i = 0 ; i < permutations.length ; i++){
            retour.add(bits.get(permutations[i] - 1));
        }

        return retour;
    }

}
