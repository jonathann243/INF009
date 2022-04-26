package main.java.com.company.Couche;

import java.util.ArrayList;
import java.util.Random;

public class Liaison {

    /**
     * Methode qui permet de generer  l'adresse de destination
     * @return String
     */

    public static int generateAdresseDestination(int destination){

        int source;

        do {
            Random random = new Random();
            source = random.nextInt(255) + 1;;
        }while(destination == source);

        return source;
    }
    /***
     *
     * @param adresse adresse source network
     * @return 0 ok 1 pas de reponce 2 refus
     */
    public static int genererReponseConnexion(int adresse ){
        int x=0;//reponse ok
        if (adresse %13 == 0){// refus de la connexion
            x=2;
        } else if (adresse %19 == 0){//ne recois pas de reponse
            x=1;
        }
        return x ;
    }

    /***
     *
     * @param adresse adresse source network test
     * @param numPS   Type.ps egal radom
     * @return liste de int
     */
    public static ArrayList<Integer> genererReponsePaquetAcquitement(int adresse, int numPS ){
        ArrayList<Integer> x=new ArrayList<>();
        int aleatoire=0 + (int)(Math.random() * ((7 - 0) + 0));
        if ( aleatoire== numPS ){// refus de la connexion
            x.add(1);
        }
        if (adresse %15 == 0){//ne recois pas de paquet d'acquitement
            x.add(2);
        }
        return x ;
    }


}
