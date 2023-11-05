package tn.esprit.spring.khaddem.dto;

import lombok.Getter;
import tn.esprit.spring.khaddem.entities.Niveau;

@Getter
public class EquipeUpdateDTO {
    // Getter method for the nomEquipe field
    private String nomEquipe;
    private Niveau niveau;

    // Setter methods for the fields
    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    // Other getter and setter methods as needed
}


