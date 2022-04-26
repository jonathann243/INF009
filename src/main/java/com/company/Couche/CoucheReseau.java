package main.java.com.company.Couche;

import main.java.com.company.MyUtils.Util_File_RW;
import main.java.com.company.PaquetInterCouche;
import main.java.com.company.Paquet.connexion.PaquetCommunicationEtablie;
import main.java.com.company.Paquet.connexion.PaquetIndicationLiberation;
import main.java.com.company.Paquet.transfert.PaquetDonnees;


import java.io.*;
import java.util.ArrayList;
import java.util.Queue;

import static main.java.com.company.MyUtils.Util_File_path.*;

public class CoucheReseau {

    private Queue<PaquetInterCouche> canalTransportToReseau;

    public CoucheReseau(Queue<PaquetInterCouche> canalTransportToReseau) {
        this.canalTransportToReseau = canalTransportToReseau;


    }

    //Reseau qui lit les paquets qui lui sont destinés
    public synchronized void ReadPaquetFromTransport(){
        while(true){

            if( canalTransportToReseau.size() > 0){
                if(canalTransportToReseau.peek() instanceof PaquetInterCouche){

                    PaquetInterCouche npduDuTransport = canalTransportToReseau.poll();
                    String messagePour_L_lec=" ";
                    String messagePour_L_ecr=" ";

                //**** CONNECTION <Liaion>
                    if(npduDuTransport.type == 0b0000_1011){
                        int adressesource = npduDuTransport.adresseSource;
                        int resultatGenCon = CoucheLiaison.genererReponseConnexion(adressesource);
                        int adressedestination=npduDuTransport.getAdressedestination();
                       switch(resultatGenCon){

                           case 0:{
                               // Reponse <ok>
                               PaquetCommunicationEtablie paquetCom=new PaquetCommunicationEtablie(adressesource,adressedestination,npduDuTransport.getConnection());
                               messagePour_L_lec="N_CONNECT.resp";
                               messagePour_L_ecr="\nCONNECTION_OK "+adressedestination;
                               npduDuTransport.setPaquet(paquetCom);
                               break;
                           }
                           case 1:{

                               // Refus connexion;
                             PaquetIndicationLiberation paquetIndicationLiberation=new PaquetIndicationLiberation(adressesource,adressedestination,"00000001",npduDuTransport.getConnection());
                               messagePour_L_lec="N_DISCONNECT.req"+paquetIndicationLiberation.toString();
                               messagePour_L_ecr=paquetIndicationLiberation.toString();
                               break;}
                           case 2:
                               //pas reponse
                               PaquetIndicationLiberation paquetIndicationLiberation=new PaquetIndicationLiberation(adressesource,adressedestination,"00000010",npduDuTransport.getConnection());
                               messagePour_L_lec="Pas de reponse";
                               messagePour_L_ecr=paquetIndicationLiberation.toString();
                               break;
                       }
                    }
                    //****SEND  <Liaion>
                    if(npduDuTransport.type == 0b0000_0000){
                        int adressesource = npduDuTransport.adresseSource;
                        int ps=npduDuTransport.ps;
                        ArrayList<Integer> resultatGenTrans = CoucheLiaison.genererReponsePaquetAcquitement(adressesource,ps);

                        if (resultatGenTrans.size()>0){//negatif  //pas de reponse
                            if (resultatGenTrans.contains(1)){//<NO>
                                PaquetDonnees paquetData=new PaquetDonnees(adressesource,npduDuTransport.getAdressedestination(),npduDuTransport.getConnection(), (byte) 0b0000_0000,npduDuTransport.getData());
                                messagePour_L_lec="SEND = <NEGATIF> NumCon = "+npduDuTransport.getConnection();
                                messagePour_L_ecr="";
                                npduDuTransport.setPaquet(paquetData);
                            }
                            if (resultatGenTrans.contains(2)){
                                //pas de reponse<??>
                              messagePour_L_lec="SEND = <ER.TIME = "+npduDuTransport.getConnection();
                                messagePour_L_ecr="SEND = <ER.TIME> NumCon = "+npduDuTransport.getConnection();
                            }

                        }else{ // positif<ok>
                            PaquetDonnees paquetData=new PaquetDonnees(adressesource,npduDuTransport.getAdressedestination(),npduDuTransport.getConnection(), (byte) 0b0000_0000,npduDuTransport.getData());
                            messagePour_L_lec="SEND = <OK> NumCon = "+npduDuTransport.getConnection();
                            messagePour_L_ecr=""+paquetData;
                            npduDuTransport.setPaquet(paquetData);
                        }
                    }

                    //****END
                    if(npduDuTransport.type == 0b0001_0011){

                        PaquetIndicationLiberation paquetIndicationLiberation=new PaquetIndicationLiberation(npduDuTransport.getAdresseSource(),npduDuTransport.adressedestination,"00000010",npduDuTransport.getConnection());
                        messagePour_L_ecr=paquetIndicationLiberation.toString();
                        npduDuTransport.setPaquet(paquetIndicationLiberation);

                    }


                    Util_File_RW.writeToFile(messagePour_L_ecr,L_ECR);
                    writeFileL_lec(messagePour_L_lec);
                }
            }

        }
    }




    //Methode qui permet d'écrire dans le fichier L_LEC

    public static void writeFileL_lec(String message){

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
