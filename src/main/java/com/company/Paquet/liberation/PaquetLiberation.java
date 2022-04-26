package main.java.com.company.Paquet.liberation;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.java.com.company.Enum.Primitiv;
import main.java.com.company.MyUtils.Util_Binary;
import main.java.com.company.Paquet.Paquet;

@Getter
@Setter
public class PaquetLiberation extends Paquet {
    private byte type;
@Builder

    public PaquetLiberation(int adresseSource, int adresseDestination, int numeroConnexion, byte type) {
        super(adresseSource, adresseDestination, numeroConnexion);
        this.type = 0b0001_0011;
    }

    @Override
    public String toString() {
        return "PaquetLiberation{" +
                "adresseSource=" + adresseSource +
                ", adresseDestination=" + adresseDestination +
                ", numeroConnexion=" + numeroConnexion +
                ", type=" + Util_Binary.inToString(type) +
                '}';
    }
}
