package main.java.com.company.Paquet.connexion;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.java.com.company.Enum.Primitiv;
import main.java.com.company.Paquet.Paquet;
@Getter
@Setter
public class PaquetAppel extends Paquet {
    private Primitiv type;
@Builder
    public PaquetAppel(int adresseSource, int adresseDestination) {
        super(adresseSource,adresseDestination);
        this.type = Primitiv.N_CONNECT_req;
    }



    
}
