package tn.esprit.spring.khaddem.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.entities.Equipe;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Niveau;
import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.EquipeRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EquipeServiceImpl implements IEquipeService {

    EquipeRepository equipeRepository;
    ContratRepository contratRepository;

    @Override
    public List<Equipe> retrieveAllEquipes() {
        return equipeRepository.findAll();
    }

    @Transactional
    public Equipe addEquipe(Equipe e) {
        equipeRepository.save(e);
        return e;
    }


    public Equipe updateEquipe(Equipe updatedEquipe) {
        Optional<Equipe> existingEquipeOptional = equipeRepository.findById(updatedEquipe.getIdEquipe());

        if (existingEquipeOptional.isPresent()) {
            Equipe existingEquipe = existingEquipeOptional.get();

            // Merge changes from updatedEquipe into existingEquipe
            BeanUtils.copyProperties(updatedEquipe, existingEquipe, "idEquipe"); // Exclude copying the "idEquipe" property

            equipeRepository.save(existingEquipe);
            return existingEquipe;
        } else {
            throw new EntityNotFoundException("Equipe not found");
        }
    }


    @Override
    public Equipe retrieveEquipe(Integer idEquipe) {
        Optional<Equipe> equipeOptional = equipeRepository.findById(idEquipe);

        if (equipeOptional.isPresent()) {
            return equipeOptional.get();
        } else {
            throw new EntityNotFoundException("Equipe not found");
        }
    }


    public void evoluerEquipes() {
        log.info("debut methode evoluerEquipes");

        List<Equipe> equipes = equipeRepository.findAll();
        log.debug("nombre equipes: " + equipes.size());

        for (Equipe equipe : equipes) {
            if (equipeHasActiveStudentsAndEligibleForUpgrade(equipe)) {
                upgradeEquipeNiveau(equipe);
            }
        }

        log.info("fin methode evoluerEquipes");
    }

    private boolean equipeHasActiveStudentsAndEligibleForUpgrade(Equipe equipe) {
        return equipe.getEtudiants() != null && !equipe.getEtudiants().isEmpty()
                && (equipe.getNiveau().equals(Niveau.JUNIOR) || equipe.getNiveau().equals(Niveau.SENIOR))
                && shouldUpgradeEquipe(equipe);
    }

    private boolean shouldUpgradeEquipe(Equipe equipe) {
        List<Etudiant> etudiants = equipe.getEtudiants();
        int countActiveContracts = 0;

        for (Etudiant etudiant : etudiants) {
            List<Contrat> contrats = contratRepository.findByEtudiantIdEtudiant(etudiant.getIdEtudiant());

            for (Contrat contrat : contrats) {
                if (isContractActive(contrat)) {
                    countActiveContracts++;
                    if (countActiveContracts >= 3) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean isContractActive(Contrat contrat) {
        if (contrat.getArchived() == null || !contrat.getArchived()) {
            long timeDifferenceInMillis = contrat.getDateFinContrat().getTime() - contrat.getDateDebutContrat().getTime();
            long yearsDifference = timeDifferenceInMillis / (1000L * 60 * 60 * 24 * 365);
            return yearsDifference > 1;
        }
        return false;
    }

    private void upgradeEquipeNiveau(Equipe equipe) {
        Niveau currentNiveau = equipe.getNiveau();
        Niveau newNiveau = (currentNiveau == Niveau.JUNIOR) ? Niveau.SENIOR : Niveau.EXPERT;

        log.info("mise a jour du niveau de l'équipe " + equipe.getNomEquipe() +
                " du niveau " + currentNiveau + " vers le niveau supérieur " + newNiveau);

        equipe.setNiveau(newNiveau);
        equipeRepository.save(equipe);
    }

}
