package main.java.com.company.Paquet.connexion;


import lombok.Getter;
import main.java.com.company.Paquet.Paquet;
@Getter
public class PaquetCommunicationEtablie extends Paquet {
    private byte type;


    public PaquetCommunicationEtablie(int adresseSource, int adresseDestination, int numeroConnexion) {
        super(adresseSource, adresseDestination, numeroConnexion);
        this.type=0b00001111;
    }
}
