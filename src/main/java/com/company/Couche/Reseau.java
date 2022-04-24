package main.java.com.company.Couche;


import main.java.com.company.Enum.Primitiv;
import main.java.com.company.Npdu;

import java.io.*;
import java.util.Queue;
import java.util.Timer;

import static main.java.com.company.MyUtils.Constante.L_LEC;

public class Reseau {

    private Queue<Npdu> canalTransportToReseau;
    private Queue<Npdu> canalReseauToTransport;
    private Queue<Npdu> canalReseauToProcessing;
    private Queue<Npdu> canalProcessingToReseau;
    private Timer timer;
    private boolean disconnected;
    private boolean connect;

    public Reseau(Queue<Npdu> canalTransportToReseau, Queue<Npdu> canalReseauToTransport, Queue<Npdu> canalReseauToProcessing, Queue<Npdu> canalProcessingToReseau) {
        this.canalTransportToReseau = canalTransportToReseau;
        this.canalReseauToTransport = canalReseauToTransport;
        this.canalReseauToProcessing = canalReseauToProcessing;
        this.canalProcessingToReseau = canalProcessingToReseau;

        Start();
    }


    private void Start() {
        disconnected = false;
        connect = false;

        // TODO : Initialiser le Timer

        // timer = new Timer();
        //timer.schedule(ReadPaquetFromTransport(), 1000);
    }

    
    /**
     * Methode qui permet à la couche Réseau de lire les contenus de @code{canalTransportToReseau} contenant
     * les paquets qui lui sont destinés
     * 
     * @return void
     */
    public synchronized void ReadPaquetFromTransport(){
        while(true){
            if(!disconnected && canalTransportToReseau.size() > 0){
                if(canalTransportToReseau.peek() instanceof Npdu){
                    // Recorver infos of NPDU
                    Npdu npduDuTransport = canalTransportToReseau.poll();
                    String messagePour_L_lec;

                    // Donner un numero de route pour la couche réseau
                    // Le numero de la route correspond au numero de l'adresse Source
                    // Vérification des adresses sources et de destinations
                    if(npduDuTransport.type == Primitiv.N_CONNECT_req){
                        int adressesource = npduDuTransport.adresseSource;
                        int adressedestination = npduDuTransport.adressedestination;

                        if(!isValidAdresse(adressesource, adressedestination))
                            npduDuTransport.routeAddr = "Les adresses ne sont pas valides pour avoir une route";
                        else
                            npduDuTransport.routeAddr = String.valueOf(adressesource);

                        messagePour_L_lec = npduDuTransport.type + " " + npduDuTransport.adresseSource + " "
                            + npduDuTransport.adressedestination + " " + npduDuTransport.routeAddr;
                        writeTo_L_LEC(messagePour_L_lec);
                    }
                   

                }
            }
        }
    }

    /**
     * Methode qui permet de vérifier si les adresses sont bonnes ou erronées
     * @param adresseSource : l'adresse source à vérifier
     * @param adresseDestination : l'adresse de destination à vérifier
     * @return boolean
     * */
    private boolean isValidAdresse(int adresseSource, int adresseDestination){
        if(adresseSource > 255 || adresseDestination > 255)
            return false;
        else return adresseSource >= 0 && adresseDestination >= 0;
    }

    /**
     * Methode qui permet d'écrire dans le fichier L_LEC
     * @param message : le message à écrire dans le fichier
     * @throws FileNotFoundException : Si le fichier n'a pas été trouvé
     * @throws IOException : erreur au niveau du system I/O
     * @return void
     */
    public static void writeTo_L_LEC(String message){
        File file = new File(L_LEC);
        try(
                FileWriter fileWriter = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                PrintWriter out = new PrintWriter(bw)){
            out.println(message);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



}
