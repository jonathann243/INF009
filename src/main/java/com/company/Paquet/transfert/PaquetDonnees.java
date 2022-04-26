package main.java.com.company.Paquet.transfert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.java.com.company.MyUtils.Util_Binary;
import main.java.com.company.Paquet.Paquet;

@Getter
@Setter
public class PaquetDonnees  extends Paquet {
    private byte type;
    private String donnees;
@Builder
    public PaquetDonnees(int adresseSource, int adresseDestination ,int numeroConnection, byte type, String donnees) {
        super(adresseSource, adresseDestination, numeroConnection);
        this.type = type;
        this.donnees = donnees;
    }

    @Override
    public String toString() {
        return "PaquetDonnees{" +
                "adresseSource=" + adresseSource +
                ", adresseDestination=" + adresseDestination +
                ", numeroConnexion=" + numeroConnexion +
                ", type=" + Util_Binary.inToString(type) +
                ", donnees='" + donnees + '\'' +
                '}';
    }
}
