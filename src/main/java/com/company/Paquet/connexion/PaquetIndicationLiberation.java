package main.java.com.company.Paquet.connexion;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.java.com.company.Enum.Primitiv;
import main.java.com.company.Paquet.Paquet;
@Getter
@Setter
public class PaquetIndicationLiberation extends Paquet {
    private Primitiv type;
    private int adresseSource;
    private int adresseDestination;
    private String raison;
@Builder
    public PaquetIndicationLiberation(int adresseSource, int adresseDestination, String raison) {
        super(adresseSource,adresseDestination);
        this.type = Primitiv.N_DISCONNECT_req;
        this.raison = raison;
    }

}
