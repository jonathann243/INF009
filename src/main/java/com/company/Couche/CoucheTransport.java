/** -----------------------------------------AUTEUR--------------------
 * *JONATHAN KANYINDA
 * CHEIKH AHMED TIDJANE NIANG
 * ------------------------------------------------------------------
 * */
package main.java.com.company.Couche;
import main.java.com.company.MyUtils.Util_File_RW;
import main.java.com.company.PaquetInterCouche;
import main.java.com.company.Paquet.Paquet;
import main.java.com.company.Paquet.connexion.PaquetAppel;
import main.java.com.company.Paquet.liberation.PaquetLiberation;
import main.java.com.company.Paquet.transfert.PaquetDonnees;


import java.util.*;

import static main.java.com.company.MyUtils.Util_File_path.S_ECR;
import static main.java.com.company.MyUtils.Util_File_path.S_LEC;


public class CoucheTransport {

    private Queue<PaquetInterCouche> canalTransportToReseau;
    private static List<PaquetInterCouche> listControlConnection = new ArrayList<>();

    public CoucheTransport(Queue<PaquetInterCouche> canalTransportToReseau) {
        this.canalTransportToReseau = canalTransportToReseau;
    }

    //Methode qui permet de lire dans le fichier S_LEC  et de le stocker dans la queue canalReseauToTransport
    public synchronized void lireDepuisTrs(){

        ArrayList<String> list = Util_File_RW.ReadToFile(S_LEC);//On lit le fichier S_LEC et on retourent une liste de String
        PaquetInterCouche transportToReseau;
        //on definit un paquet de type pour chaque type(3)
        Paquet paquetAppel;
        Paquet paquetLiberation;
        int numconnexion= generateConnecxionNumber();
        int numeroDestination = CoucheLiaison.generateAdresseDestination(Integer.parseInt(list.get(list.size() - 1).split(" ")[1]));
        //on lit chaque operation sur le fichier S_LEC
        //et on traite selon chaque cas
        for (String lineRead : list) {
            transportToReseau = new PaquetInterCouche();
            String[] dataFile = lineRead.split(" ");//**decoupe la ligne  en mot

                boolean validNpdu = false;
                // Si c'est la fin du fichier
                if(lineRead.equals(""))
                    break;

                else{

                    String directive = dataFile[0];
                    int numeroSource = Integer.parseInt(dataFile[1]);

                    switch (directive) {
                        case "N_CONNECT":{
                             numconnexion= generateConnecxionNumber();
                            numeroDestination = CoucheLiaison.generateAdresseDestination(Integer.parseInt(list.get(list.size() - 1).split(" ")[1]));
                            paquetAppel = new PaquetAppel(numeroSource,numeroDestination,numconnexion);
                            transportToReseau.type = 0b0000_1011;
                            transportToReseau.adresseSource = numeroSource;
                            transportToReseau.adressedestination = numeroDestination;
                            transportToReseau.paquet = paquetAppel;
                            transportToReseau.connection = numconnexion;
                           // canalReseauToTransport.add(transportToReseau);

                            validNpdu = true;
                            break;
                        }
                        case "N_DATA":{
                            PaquetDonnees paquetDonnee = new PaquetDonnees(numeroSource,numeroDestination,numconnexion, (byte) 0b0000_0000, dataFile[3]);
                            transportToReseau.type = 0b0000_0000;
                            transportToReseau.adresseSource = numeroSource;
                            transportToReseau.adressedestination = numeroDestination;
                            transportToReseau.connection = numconnexion;
                            transportToReseau.paquet = paquetDonnee;
                            transportToReseau.data = dataFile[3];
                            validNpdu = true;

                            break;
                        }
                        case "N_DISCONNECT":{
                            paquetLiberation = new PaquetLiberation(numeroSource,numeroDestination, numconnexion, (byte) 0b0001_0011);
                            transportToReseau.type = 0b0001_0011;
                            transportToReseau.adresseSource = numeroSource;
                            transportToReseau.connection = numconnexion;
                            transportToReseau.adressedestination = numeroDestination;
                            transportToReseau.paquet = paquetLiberation;
                            validNpdu = true;
                            break;
                        }
                        default:{
                            System.out.println("La structure du paquet n'est pas bon");
                            break;
                        }
                    }

                    if(validNpdu){
                        ajouterConnection(transportToReseau);
                    }
                }
            }
    }


     //Methode qui d'ecrire dans le fichier S_ECR
    public void ecrireDepuisTrs(){
            canalTransportToReseau.forEach(e ->{
                Util_File_RW.writeToFile(e.toString(),S_ECR);});
    }



    /**
     * Methode qui permet de générer un nombre correspondant au numero de Connexion
     * @return int
     */
    private int generateConnecxionNumber() {
        return getRandomNumber(255);
    }

    private void envoyerVersReseau(PaquetInterCouche connectPaquet) throws IllegalStateException{
        canalTransportToReseau.offer(connectPaquet);
    }

    private void ajouterConnection(PaquetInterCouche connectPaquet){
        if(!isConnectionExist(connectPaquet.adresseSource, connectPaquet.adressedestination)){
            connectPaquet.status = false;
            listControlConnection.add(connectPaquet);
        }
        envoyerVersReseau(connectPaquet);
    }



    private boolean isConnectionExist(int adresseSource, int adresseDestination){
        for (PaquetInterCouche npdu : listControlConnection) {
            if(npdu.adresseSource == adresseSource &&
                npdu.adressedestination == adresseDestination ){
                return true;
            }
        }
        return false;
    }


    //Methode qui permet de selectionner un chiffre aleatoirement

    public int getRandomNumber(int max){

        Random random = new Random();
        return random.nextInt(max) + 1;

    }

}


