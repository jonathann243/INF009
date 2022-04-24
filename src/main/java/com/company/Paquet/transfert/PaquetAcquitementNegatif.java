package main.java.com.company.Paquet.transfert;

import lombok.Getter;
import lombok.Setter;
import main.java.com.company.MyUtils.Util_Binary;

import java.util.Arrays;
@Getter
@Setter
public class PaquetAcquitementNegatif {
    private int numeroConnexion;
    private byte typePaquet=0b0000_1001;

    public PaquetAcquitementNegatif(int numeroConnexion, byte typePaquet) {
        this.numeroConnexion = numeroConnexion;
        this.typePaquet = typePaquet;
    }




    public byte getPr(){
        return Util_Binary.getPartOf("pr",typePaquet);
    }

    public byte getPs(){
        return Util_Binary.getPartOf("ps",typePaquet);
    }

    public byte getM(){
        return Util_Binary.getPartOf("m",typePaquet);
    }

}
