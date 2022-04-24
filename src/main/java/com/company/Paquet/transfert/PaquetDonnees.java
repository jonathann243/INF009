package main.java.com.company.Paquet.transfert;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.java.com.company.Enum.Primitiv;
import main.java.com.company.Paquet.Paquet;
@Getter
@Setter
public class PaquetDonnees extends Paquet {
    private int numeroConnection;
    private Primitiv type;
    private String donnees;
@Builder
    public PaquetDonnees(int adresseSource, int adresseDestination, String donnees) {
        super(adresseSource,adresseDestination);
        this.type = Primitiv.N_DATA_req;
        this.donnees = donnees;
    }

    @Override
    public String toString() {
        return type + " " + adresseSource + " " +  adresseDestination + " " + donnees;
    }
 
}
