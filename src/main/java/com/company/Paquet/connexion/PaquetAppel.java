package main.java.com.company.Paquet.connexion;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.java.com.company.MyUtils.Util_Binary;
import main.java.com.company.Paquet.Paquet;
@Getter
@Setter
public class PaquetAppel extends Paquet {
    private byte type;
@Builder
    public PaquetAppel(int adresseSource, int adresseDestination,int numeroConnexion) {
        super(adresseSource,adresseDestination, numeroConnexion);
        this.type = 0b0000_1011;
    }

    @Override
    public String toString() {
        return "PaquetAppel{" +
                "adresseSource=" + adresseSource +
                ", adresseDestination=" + adresseDestination +
                ", numeroConnexion=" + numeroConnexion +
                ", type=" + Util_Binary.inToString(type) +
                '}';
    }
}
