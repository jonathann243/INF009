package main.java.com.company;


import main.java.com.company.Couche.Reseau;
import main.java.com.company.Couche.Transport;

import java.util.LinkedList;
import java.util.Queue;


public class Main {
    private static Queue<Npdu> canalTransportToReseau = new LinkedList<>();
    private static Queue<Npdu> canalReseauToTransport = new LinkedList<>();
    private static Queue<Npdu> canalReseauToProcessing = new LinkedList<>();
    private static Queue<Npdu> canalProcessingToReseau = new LinkedList<>();
    private static Transport transport = new Transport(canalTransportToReseau, canalReseauToTransport);
    private static Reseau reseau = new Reseau(canalTransportToReseau, canalReseauToTransport, canalReseauToProcessing, canalProcessingToReseau);
    //private static Transport transport = new Transport();
    private static String message;
    private static int nbTest = 0;
    private static Thread lireDeTransport, ecrireDeTransport, lireDeReseau, ecrireDeReseau;


    public static void main(String[] args){
        demarreThreads();
        int nbreThreadCurrent = Thread.activeCount();
        System.out.println(nbreThreadCurrent);
    }

    /**
     * Methode qui permet de générer les addresses sources et destinations
     * @code {destination} correspond également au numero de connexion
     * @return void
     */
    private static void genererSourceDestination(){

        int destination =  transport.setAdresseDestination();
        int source = transport.setAdresseSource(destination);

        message = "N_CONNECT " + source + " " + destination + "\n" + 
                "N_DATA " + destination + " Donnée-" +  ++nbTest + "\n" +
                "N_DISCONNECT " + source + " " + destination + "\n";
 
        transport.writeTo_S_ECR(message);
    }

    /**
     * Methode qui permet de demarrer les Threads 
     * @return void
     */
    private static void demarreThreads(){
        lireDeTransport = new Thread(transport::readFromTransport);
        lireDeTransport.setName("Thread-lireDeTransport");
        lireDeTransport.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ecrireDeTransport = new Thread(transport::writeFromTransport);
        ecrireDeTransport.setName("Thread-ecrireDeTransport");
        ecrireDeTransport.start(); 
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lireDeReseau = new Thread(reseau::ReadPaquetFromTransport);
        lireDeReseau.setName("Thread-lireDeReseau");
        lireDeReseau.start();


    }


}




