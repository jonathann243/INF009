
/** -----------------------------------------AUTEUR--------------------
 * *JONATHAN KANYINDA
 * CHEIKH AHMED TIDJANE NIANG
 * ------------------------------------------------------------------
 * */package main.java.com.company;


import lombok.Getter;
import lombok.Setter;
import main.java.com.company.Paquet.Paquet;
@Getter
@Setter
public class PaquetInterCouche {
    public byte type;
    public Paquet paquet;
    public int adresseSource;
    public int adressedestination;
    public String data;
    public String target;
    public int ps, pr;
    public int connection;
    public boolean status;

    @Override
    public String toString() {
        return paquet.toString();
    }

    

}