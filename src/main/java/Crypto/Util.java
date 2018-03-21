package Crypto;

import java.util.ArrayList;
import java.util.Arrays;
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

        Boolean[] array = new Boolean[data1.size()*2];
        List<Boolean> retour = Arrays.asList(array);

        for(int i = 0 ; i < data1.size(); i++) {
            retour.set(i, data1.get(i));
            retour.set(i + data1.size(), data2.get(i));
        }

        return retour;
    }

    public static List<List<Boolean>> split(List<Boolean> data) {

        List<List<Boolean>> retour = new ArrayList<List<Boolean>>();

        List<Boolean> half1 = data.subList(0, data.size()/2);
        List<Boolean> half2 = data.subList(data.size()/2, data.size());

        retour.add(half1);
        retour.add(half2);

        return retour;
    }

    public static List<List<Boolean>> splitEnHuit(List<Boolean> data) {

        List<List<Boolean>> retour = new ArrayList<List<Boolean>>();

        List<Boolean> half1 = new ArrayList<Boolean>();
        List<Boolean> half2 = new ArrayList<Boolean>();
        List<Boolean> half3 = new ArrayList<Boolean>();
        List<Boolean> half4 = new ArrayList<Boolean>();
        List<Boolean> half5 = new ArrayList<Boolean>();
        List<Boolean> half6 = new ArrayList<Boolean>();
        List<Boolean> half7 = new ArrayList<Boolean>();
        List<Boolean> half8 = new ArrayList<Boolean>();

        int i = 0;
        while(i < 65) {

            if(i >= 0 && i < 9){
                if(i < data.size()) {
                    half1.add(data.get(i));
                } else {
                    half1.add(false);
                }
            }

            if(i >= 9 && i < 17){
                if(i < data.size()) {
                    half2.add(data.get(i));
                } else {
                    half2.add(false);
                }
            }

            if(i >= 17 && i < 25){
                if(i < data.size()) {
                    half3.add(data.get(i));
                } else {
                    half3.add(false);
                }
            }

            if(i >= 25 && i < 33){
                if(i < data.size()) {
                    half4.add(data.get(i));
                } else {
                    half4.add(false);
                }
            }

            if(i >= 33 && i < 41){
                if(i < data.size()) {
                    half5.add(data.get(i));
                } else {
                    half5.add(false);
                }
            }

            if(i >= 41 && i < 49){
                if(i < data.size()) {
                    half6.add(data.get(i));
                } else {
                    half6.add(false);
                }
            }

            if(i >= 49 && i < 57){
                if(i < data.size()) {
                    half7.add(data.get(i));
                } else {
                    half7.add(false);
                }
            }

            if(i >= 57){
                if(i < data.size()) {
                    half8.add(data.get(i));
                } else {
                    half8.add(false);
                }
            }

            i++;
        }

        retour.add(half1);
        retour.add(half2);
        retour.add(half3);
        retour.add(half4);
        retour.add(half5);
        retour.add(half6);
        retour.add(half7);
        retour.add(half8);

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

    public static List<Boolean> or(List<Boolean> data, List<Boolean> key) {

        List<Boolean> retour = new ArrayList<Boolean>();

        for(int i = 0 ; i < data.size() ; i++){
            retour.add(data.get(i) != key.get(i));
        }

        return retour;
    }

    public static int booleanToInt(Boolean bool1, Boolean bool2) {

        int result = 0;

        if(bool1){
            result += 2;
        }

        if(bool2){
            result += 1;
        }

        return result;
    }

    public static List<Boolean> intToBoolean(int data, int nombreBitsSouhaites) {

        List<Boolean> retour = new ArrayList<Boolean>();

        for (int i = nombreBitsSouhaites - 1; i >= 0; i--) {
            Boolean result = ((1 << i) & data) != 0;
            retour.add(result);
        }
        
        return retour;
    }

    public static List<List<Boolean>> switchList(List<List<Boolean>> data) {

        List<List<Boolean>> retour = new ArrayList<List<Boolean>>();

        retour.add(data.get(1));
        retour.add(data.get(0));

        return retour;
    }

    public static String charToBinaryString(char myChar){

        String zeroString = "";

        String binaryStringChar = Integer.toBinaryString((int)myChar);

        int nbrZeroToAdd = 8 - binaryStringChar.length();

        for (int i=0; i < nbrZeroToAdd; i++){
            zeroString = zeroString + '0';
        }

        return zeroString + binaryStringChar;
    }

    public static List<Boolean> binaryStringToBooleanList(String myString){
        String[] temp = myString.split("");
        List<Boolean> booleanList = new ArrayList<Boolean>(myString.length());
        for (int i=0; i<myString.length(); i++){

            if( Integer.parseInt(temp[i]) == 1){
                booleanList.add(true);
            }else{
                booleanList.add(false);
            }
        }
        return booleanList;
    }

    public static void printBooleanListFormat(List<Boolean> booleanList){
        System.out.print("[");

        for (int i=0; i < booleanList.size(); i++){
            if(booleanList.get(i)){
                if(i == booleanList.size()-1){
                    System.out.print("1");
                }else{
                    System.out.print("1,");
                }
            }else{
                if(i == booleanList.size()-1){
                    System.out.print("0");
                }else{
                    System.out.print("0,");
                }
            }
        }
        System.out.println("]");
    }

    public static String booleanListToBinaryString(List<Boolean> booleanList){
        String binaryString = "";

        for (Boolean bool : booleanList){
            if(bool){
                binaryString = binaryString + "1";
            }else{
                binaryString = binaryString + "0";
            }
        }
        return binaryString;
    }

    public static String binaryStringToChar(String binaryString){
        String retour = "";
        for (int i = 0; i < binaryString.length()/8; i++) {

            int a = Integer.parseInt(binaryString.substring(8*i,(i+1)*8),2);
            retour += (char)(a);
        }
        return retour;
    }

    public static String construireMessageEnvoi(MessageType messageType, String message) {
        String retour;
        retour = messageType + Constantes.SPLITTER + message;

        return retour;
    }

//    public static MessageType getType(String message) {
//        String msg = message.split(Constantes.SPLITTER)[0];
//        return MessageType.stringToMessageType(msg);
//    }

    public static String getMessage(String message) {
        return message.split(Constantes.SPLITTER)[1];
    }

    public static List<Boolean> getCurrentBooleanList(String message){

        List<Boolean> retour = new ArrayList<Boolean>();

        char[] arrayMessage = message.toCharArray();

        for(char currentChar : arrayMessage){
            // Conversion du charactère vers sa version binaryString (calculé à partir de son code ASCII decimal)
            String charToBinaryString = Util.charToBinaryString(currentChar);

            // Conversion de la chaîne de charactères binaire vers une liste de booleens
            retour.addAll(binaryStringToBooleanList(charToBinaryString));
        }

        return retour;
    }
}