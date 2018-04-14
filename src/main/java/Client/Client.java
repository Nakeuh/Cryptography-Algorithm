package Client;

import Crypto.DES64;
import Crypto.MessageType;
import Crypto.RSA;
import Crypto.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class Client {
    public static String keyDES64 = null;

    public static void main(String[] args) {
        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc = new Scanner(System.in);//pour lire à partir du clavier
        RSA rsa = new RSA();
        KeyPair keyPairRSA = null;
        DES64 des64 = new DES64();
        String msg =null;
        try {
            /*
             * les informations du serveur ( port et adresse IP ou nom d'hote
             * 127.0.0.1 est l'adresse local de la machine
             */
            clientSocket = new Socket("127.0.0.1",5000);

            //flux pour envoyer
            out = new PrintWriter(clientSocket.getOutputStream());
            //flux pour recevoir
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("Client démarré. Connecté au serveur (127.0.0.1:5000)");


            /// Initialisation of keys ///

            // Génération de la clé RSA et envoi au serveur de la clé publique
            keyPairRSA = rsa.generateKey(2048);
            System.out.println("Génération d'une clé RSA");

            PublicKey publicKey = keyPairRSA.getPublic();
            String serializedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());

            String messageAEnvoyer = Util.construireMessageEnvoi(MessageType.KEY_RSA, String.valueOf(serializedPublicKey));
            out.println(messageAEnvoyer);
            out.flush();
            System.out.println("Clé publique envoyée au serveur : "+serializedPublicKey);

            while(keyDES64==null){
                msg = in.readLine();

                if(Util.getType(msg).equals(MessageType.KEY_DES)){
                    byte [] crypted = Base64.getDecoder().decode(Util.getMessage(msg));
                    System.out.println("Clé DES64 reçue: "+crypted);
                    keyDES64=rsa.decrypt(crypted,keyPairRSA.getPrivate());
                    System.out.println("Décodage de la clé DES64 grâce à la clé privée RSA");
                }else{
                    System.out.println("En attente d'une clé DES64. Message refusé");
                }
            }

            System.out.println("La conversation peut désormais débuter. Tous les messages seront cryptées à l'aide d'une clé DES64");


            Thread envoyer = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    while(true){
                        System.out.print("Client : ");
                        msg = sc.nextLine();
                        out.println(des64.encrypt(msg,keyDES64));
                        out.flush();
                    }
                }
            });
            envoyer.start();

            Thread recevoir = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    try {
                        msg = in.readLine();
                        while(msg!=null){
                            System.out.println("    (Message crypté : "+msg+")");
                            msg = des64.decrypt(msg,keyDES64);

//                            System.out.println("Serveur : "+Util.getMessage(msg));
                            System.out.println("Serveur : "+msg);
                            System.out.print("Client : ");

                            msg = in.readLine();
                        }
                        System.out.println("Serveur déconecté");
                        out.close();
                        clientSocket.close();
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
