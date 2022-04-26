package main.java.com.company.Paquet.connexion;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.java.com.company.MyUtils.Util_Binary;
import main.java.com.company.Paquet.Paquet;
@Getter
@Setter
public class PaquetIndicationLiberation extends Paquet {
    private byte type;
    private String raison;
@Builder
    public PaquetIndicationLiberation(int adresseSource, int adresseDestination, String raison,int numeroConnexion) {
        super(adresseSource,adresseDestination,numeroConnexion);
        this.raison = raison;
        this.type = 0b0001_0011;
    }

    @Override
    public String toString() {
        return "PaquetIndicationLiberation{" +
                "adresseSource=" + adresseSource +
                ", adresseDestination=" + adresseDestination +
                ", numeroConnexion=" + numeroConnexion +
                ", type=" + Util_Binary.inToString(type) +
                ", raison='" + raison + '\'' +
                '}';
    }
}
