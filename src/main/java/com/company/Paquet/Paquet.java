package main.java.com.company.Paquet;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Paquet {
    protected int adresseSource;
    protected int adresseDestination;
    protected int numeroConnexion;

    public Paquet(int adresseSource, int adresseDestination) {
        this.adresseSource = adresseSource;
        this.adresseDestination = adresseDestination;
    }

    @Override
    public String toString() {
        return "Paquet [adresseDestination=" + adresseDestination + ", adresseSource=" + adresseSource + "]";
            
    }
    
}
