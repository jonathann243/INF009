/** -----------------------------------------AUTEUR--------------------
 * *JONATHAN KANYINDA
 * CHEIKH AHMED TIDJANE NIANG
 * ------------------------------------------------------------------
 * */
package main.java.com.company;
import main.java.com.company.Couche.CoucheReseau;
import main.java.com.company.Couche.CoucheTransport;

import java.util.LinkedList;
import java.util.Queue;


public class Main {

    private static Thread lireDeTransport, ecrireDeTransport, lireDeReseau;
    private static Queue<PaquetInterCouche> canalTransportToReseau = new LinkedList<>();

    private static CoucheTransport transport = new CoucheTransport(canalTransportToReseau);
    private static CoucheReseau reseau = new CoucheReseau(canalTransportToReseau);


    public static void main(String[] args){

        lireDeTransport = new Thread(transport::lireDepuisTrs);
        lireDeTransport.start();
        try {
            lireDeTransport.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ecrireDeTransport = new Thread(transport::ecrireDepuisTrs);
        ecrireDeTransport.start();

        try {
            ecrireDeTransport.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lireDeReseau = new Thread(reseau::ReadPaquetFromTransport);
        lireDeReseau.start();


    }



}




