package main.java.com.company;


import main.java.com.company.Enum.Primitiv;
import main.java.com.company.Paquet.Paquet;

public class Npdu {
    public Primitiv type;
    public Paquet paquet;
    public int adresseSource;
    public int adressedestination;
    public String routeAddr;
    public String data;
    public String target;
    public int ps, pr;
    public boolean flag;
    public int connection;
    public boolean status;
    
    @Override
    public String toString() {
        //return type + " " + adresseSource + " " + adressedestination + " " + data==null ? "" : data;
        return paquet.toString();
    }

    

}