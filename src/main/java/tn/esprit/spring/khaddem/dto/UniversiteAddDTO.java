package tn.esprit.spring.khaddem.dto;

import lombok.Getter;

@Getter
public class UniversiteAddDTO {
    private String nomUniv;
    public String getnomUniv() {
        return nomUniv;
    }

    public void setnomUniv(String nomUniv) {
        this.nomUniv = nomUniv;
    }
}
