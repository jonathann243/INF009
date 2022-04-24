package main.java.com.company.Paquet.connexion;


import lombok.Getter;
import main.java.com.company.Enum.StatusConnection;
import main.java.com.company.Paquet.Paquet;
@Getter
public class PaquetCommunicationEtablie extends Paquet {
    private StatusConnection type;
    private int adresseSource;
    private int adresseDestination;

    public PaquetCommunicationEtablie(int adresseSource, int adresseDestination) {
        super(adresseSource,adresseDestination);
        this.type = StatusConnection.ConnectionEtablie;
    }

}
