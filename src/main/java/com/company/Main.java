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

    private static Thread threadLDTrs, thredEDTrs, lireDeReseau;
    private static Queue<PaquetInterCouche> canalTransportToReseau = new LinkedList<>();

    private static CoucheTransport transport = new CoucheTransport(canalTransportToReseau);
    private static CoucheReseau reseau = new CoucheReseau(canalTransportToReseau);


    public static void main(String[] args){

        threadLDTrs = new Thread(transport::lireDepuisTrs);
        threadLDTrs.start();
        try {
            threadLDTrs.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thredEDTrs = new Thread(transport::ecrireDepuisTrs);
        thredEDTrs.start();

        try {
            thredEDTrs.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lireDeReseau = new Thread(reseau::ReadPaquetFromTransport);
        lireDeReseau.start();


    }



}




