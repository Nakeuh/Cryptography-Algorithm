package Serveur;

import Crypto.DES64;
import Crypto.MessageType;
import Crypto.RSA;
import Crypto.Util;
import sun.misc.BASE64Decoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class Serveur {

    public static void main(String[] test) {

        final ServerSocket serveurSocket  ;
        final Socket clientSocket ;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc=new Scanner(System.in);
        RSA rsa = new RSA();
        DES64 des = new DES64();
        String keyDES64 = "0011100110000101010000011101011001001110110001011100000001100010";

        try {
            serveurSocket = new ServerSocket(5000);
            clientSocket = serveurSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("Serveur démarré");

            Thread envoi= new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    while(true){
                        System.out.print("Serveur : ");
                        msg = des.encrypt(sc.nextLine(),keyDES64);
                        out.println(msg);
                        out.flush();
                    }
                }
            });
            envoi.start();

            Thread recevoir = new Thread(new Runnable() {
                String msg ;

                @Override
                public void run() {
                    try {
                        msg = in.readLine();

                        //tant que le client est connecté
                        while(msg!=null){

                            if(Util.getType(msg).equals(MessageType.KEY_RSA)){
                                try {
                                    // On recoit la clé publique RSA du client
                                    System.out.println("Clé publique RSA reçue : " + Util.getMessage(msg));

                                    byte[] encodedKey = Base64.getDecoder().decode(Util.getMessage(msg));
                                    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
                                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                                    PublicKey pubKey = keyFactory.generatePublic(keySpec);

                                    // On utilise la clé publique pour crypter la clé du DES64
                                    String crypted = Base64.getEncoder().encodeToString(rsa.encrypt(keyDES64, pubKey));

                                    System.out.println("Encryptage de la clé du DES64 grâce à la clé publique du client");
                                    System.out.println("Clé encryptée : "+crypted);
                                    out.println(Util.construireMessageEnvoi(MessageType.KEY_DES, crypted));

                                    out.flush();
                                    System.out.println("Envoie de la clé cryptée au client");
                                    System.out.println("La conversation peut désormais débuter. Tous les messages seront cryptées à l'aide d'une clé DES64");

                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }else {
                                msg = des.decrypt(msg,keyDES64);

//                                System.out.println("Client : " + Util.getMessage(msg));
                                System.out.println("Client : " + msg);
                                System.out.print("Serveur : ");

                            }
                            msg = in.readLine();
                        }

                        //sortir de la boucle si le client a déconecté
                        System.out.println("Client déconnecté");
                        //fermer le flux et la session socket
                        out.close();
                        clientSocket.close();
                        serveurSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            recevoir.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}