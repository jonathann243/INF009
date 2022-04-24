package main.java.com.company.Couche;
import main.java.com.company.Enum.Primitiv;
import main.java.com.company.MyUtils.Util_File_RW;
import main.java.com.company.Npdu;
import main.java.com.company.Paquet.Paquet;
import main.java.com.company.Paquet.connexion.PaquetAppel;
import main.java.com.company.Paquet.liberation.PaquetLiberation;
import main.java.com.company.Paquet.transfert.PaquetDonnees;


import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static main.java.com.company.MyUtils.Util_File_path.S_ECR;
import static main.java.com.company.MyUtils.Util_File_path.S_LEC;


public class Transport {
   
    Thread threadTransport, lireDeTransport, ecrireDeTransport;
    private Queue<Npdu> canalTransportToReseau;
    private Queue<Npdu> canalReseauToTransport;
    private static List<Npdu> listControlConnection = new ArrayList<>();

    public Transport(Queue<Npdu> canalTransportToReseau, Queue<Npdu> canalReseauToTransport) {
        this.canalTransportToReseau = canalTransportToReseau;
        this.canalReseauToTransport = canalReseauToTransport;
    }

    /**
     * Methode qui permet de lire le fichier S_lec.txt
     */
    public synchronized void readFromTransport(){

       // File myFile = new File(S_LEC);//chemin fichier
        ArrayList<String> list = Util_File_RW.ReadToFile(S_LEC);
        Npdu transportToReseau;
        Paquet paquetAppel;
        Paquet paquetDonnee;
        Paquet paquetLiberation;
        // essaie lecture
        //try(Scanner myReader = new Scanner(myFile)){
          //  while(myReader.hasNextLine()){
        for (String lineRead : list) {

            transportToReseau = new Npdu();
            //    String lineRead = myReader.nextLine().toString();//prend ligne
                String[] dataFile = lineRead.split(" ");//**decoupe la ligne  en mot
                boolean validNpdu = false;
                
                // Si c'est la fin du fichier
                if(lineRead.equals(""))
                    break;

                else{

                    String directive = dataFile[0];
                    int numeroSource = Integer.parseInt(dataFile[1]);
                    int numeroDestination = 0;

                    if(dataFile.length> 2)
                        numeroDestination = Integer.parseInt(dataFile[2]);

                    switch (directive) {
                        case "N_CONNECT":{
                            paquetAppel = new PaquetAppel(numeroSource,numeroDestination);
                            transportToReseau.type = Primitiv.N_CONNECT_req;
                            transportToReseau.adresseSource = numeroSource;
                            transportToReseau.adressedestination = numeroDestination;
                            transportToReseau.paquet = paquetAppel;
                            transportToReseau.connection = genererNumeroConnection();
                          //  transportToReseau.data = transportToReseau.toString();
                            validNpdu = true;
                            break;
                        }
                        case "N_DATA":{
                            paquetDonnee = new PaquetDonnees(numeroSource, numeroDestination, dataFile[3]);
                            transportToReseau.type = Primitiv.N_DATA_req;
                            transportToReseau.adresseSource = numeroSource;
                            transportToReseau.adressedestination = numeroDestination;
                            transportToReseau.paquet = paquetDonnee;
                            transportToReseau.data = dataFile[3];
                            validNpdu = true;

                            break;
                        }
                        case "N_DISCONNECT":{
                            paquetLiberation = new PaquetLiberation(numeroSource, numeroDestination);
                            transportToReseau.type = Primitiv.N_DISCONNECT_req;
                            transportToReseau.adresseSource = numeroSource;
                            transportToReseau.adressedestination = numeroDestination;
                            transportToReseau.paquet = paquetLiberation;
                            //transportToReseau.data = transportToReseau.toString();
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
            
//        }catch(FileNotFoundException e){
//            System.out.println("An error occurred.");
//			e.printStackTrace();
//        }
    }


     /**
     * Methode qui d'ecrire dans le fichier S_ECR
     * @throws FileNotFoundException : Si le fichier n'a pas été trouvé
     * @throws IOException : erreur au niveau du system I/O
     * 
     * @return String
     */
	public void writeFromTransport(){
        File file = new File(S_ECR);
        
        try(
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            PrintWriter out = new PrintWriter(bw)
            ){

            /*canalTransportToReseau.forEach(e ->{
                    out.println(e.type + " " + e.adresseSource + " " + e.adressedestination + " " 
                    + e.data==null?"":e.data);
            });*/
            canalTransportToReseau.forEach(e ->{
                out.println(e.toString());
            });
                
        } catch (FileNotFoundException e) {
            e.printStackTrace();  
        }
        catch (IOException e) {
            e.printStackTrace();  
        }
    }

    /**
     * Methode qui permet d'écrire dans le fichier S_ECR
     * @param message : le message à écrire dans le fichier
     * 
     * @throws FileNotFoundException : Si le fichier n'a pas été trouvé
     * @throws IOException : erreur au niveau du system I/O
     * @return void
     */
    public void writeTo_S_ECR(String message){
        File file = new File(S_ECR);
        try(
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        PrintWriter out = new PrintWriter(bw)){
            out.println(message.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();  
        }
        catch (IOException e) {
            e.printStackTrace();  
        }
    }


    /**
     * Methode qui permet de générer un nombre correspondant au numero de Connexion
     * @return int
     */
    private int genererNumeroConnection() {
        return getRandomNumber(255);
    }

    /**
     * Methode qui permet d'envoyer la nouvelle connexion vers le réseau
     * Utilisation de Synchronized vu le partage de ressource entre plusieurs Threads
     * @throws IllegalStateException : La methode a été invoquer de manière illégal ou un moment inapproprié !
     * @return void
     */
    private void envoyerVersReseau(Npdu connectPaquet) throws IllegalStateException{
        canalTransportToReseau.offer(connectPaquet);
    }

    /**
     * Methode qui permet d'ajouter la connection dans la liste control du transport
     * @param connectPaquet : Le paquet dont on veut ajouter dans la liste de control de connection de la couche Transport
     * et envoyer vers la couche Reséau
     * @return void
     */
    private void ajouterConnection(Npdu connectPaquet){
        if(!isConnectionExist(connectPaquet.adresseSource, connectPaquet.adressedestination)){
            // Mettre le status à non-connecté
            connectPaquet.status = false;
            listControlConnection.add(connectPaquet);
        }


        //if(connectPaquet.status)
        envoyerVersReseau(connectPaquet);
    }


     /**
      * Methode qui permet de vérifier si la connection existe déjà dans la liste de connexion du transportToReseau
      * @return boolean
      */
    private boolean isConnectionExist(int adresseSource, int adresseDestination){
        for (Npdu npdu : listControlConnection) {
            if(npdu.adresseSource == adresseSource &&
                npdu.adressedestination == adresseDestination ){
                return true;
            }
        }

        return false;
    }


    /**
     * Methode qui permet de selectionner un chiffre aleatoirement
     * @return int
     */
    public int getRandomNumber(int max){
        
        Random random = new Random();
        return random.nextInt(max) + 1;
         
    }

    /**
     * Methode qui permet de set l'adresse d'une source
     * @return String
     */

     public int setAdresseSource(int destination){

        int source;

        do {
            source = getRandomNumber(255);
        }while(destination == source);

        return source;
     }

     
     /**
     * Methode qui permet de set l'adresse d'une source
     * @return String
     */
    public int setAdresseDestination(){
        return getRandomNumber(255);
    }

    /* ***************************************************************************** */
    /* ***************************  GETTER & SETTER  ******************************* */
    /* ***************************************************************************** */
    public Queue<Npdu> getCanalTransportToReseau() {
        return canalTransportToReseau;
    }

    public void setCanalTransportToReseau(Queue<Npdu> canalTransportToReseau) {
        this.canalTransportToReseau = canalTransportToReseau;
    }

    public Queue<Npdu> getCanalReseauToTransport() {
        return canalReseauToTransport;
    }

    public void setCanalReseauToTransport(Queue<Npdu> canalReseauToTransport) {
        this.canalReseauToTransport = canalReseauToTransport;
    }

    

	 
}


