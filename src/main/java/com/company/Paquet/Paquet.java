package main.java.com.company.Paquet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public abstract class Paquet {
    protected int adresseSource;
    protected int adresseDestination;
    protected int numeroConnexion;

    @Override
    public String toString() {
        return "Paquet [adresseDestination=" + adresseDestination + ", adresseSource=" + adresseSource + "]";
    }
    
}
