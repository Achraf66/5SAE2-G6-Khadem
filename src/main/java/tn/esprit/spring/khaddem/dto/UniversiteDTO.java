package tn.esprit.spring.khaddem.dto;

import lombok.Getter;

@Getter
public class UniversiteDTO {
    private String nomUniv;
    public String getnomUniv() {
        return nomUniv;
    }

    public void setnomUniv(String nomUniv) {
        this.nomUniv = nomUniv;
    }

}
