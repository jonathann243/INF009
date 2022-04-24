package main.java.com.company.Paquet.liberation;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.java.com.company.Enum.Primitiv;
import main.java.com.company.Paquet.Paquet;

@Getter
@Setter
public class PaquetLiberation extends Paquet {
    private Primitiv type;
@Builder
    public PaquetLiberation(int adresseSource, int adresseDestination) {
        super(adresseSource,adresseDestination);
        this.type = Primitiv.N_DISCONNECT_req;
    }



    
}
