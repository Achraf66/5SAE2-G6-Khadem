package tn.esprit.spring.khaddem;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Option;
import tn.esprit.spring.khaddem.services.IEtudiantService;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
 class EtudiantServiceImplTest {


    @Autowired
    IEtudiantService EtudiantService;



    @Test
    void testAddEtudiant()
    {
        Etudiant e = Etudiant.builder().nomE("Achraf").prenomE("Abbes").op(Option.SAE).build();
        log.info(e.toString());
        Etudiant etudiant = EtudiantService.addEtudiant(e);

        assertNotNull(etudiant);
        assertNotNull(e.getIdEtudiant());
        assertTrue(e.getNomE().contains("Achraf"));
        assertTrue(e.getPrenomE().contains("Abbes"));
        assertTrue(e.getOp().equals(Option.SAE) || e.getOp().equals(Option.GAMIX) ||
                        e.getOp().equals(Option.INFINI)  || e.getOp().equals(Option.SE)
        );


        EtudiantService.removeEtudiant(e.getIdEtudiant());
    }


    @Test
    void testUpdateEtudiant() {
        Etudiant e = Etudiant.builder().nomE("Achraf").prenomE("Abbes").op(Option.SAE).build();

        Etudiant addedEtudiant = EtudiantService.addEtudiant(e);

        log.info(addedEtudiant.toString());

        addedEtudiant.setNomE("Nom");
        addedEtudiant.setPrenomE("Prenom");
        addedEtudiant.setOp(Option.SAE);

        Etudiant updatedEtudiant = EtudiantService.updateEtudiant(addedEtudiant);


        Etudiant retrievedEtudiant = EtudiantService.retrieveEtudiant(updatedEtudiant.getIdEtudiant());


        assertNotNull(retrievedEtudiant);

        assertEquals(retrievedEtudiant.getIdEtudiant(),addedEtudiant.getIdEtudiant());

        assertEquals("Nom", retrievedEtudiant.getNomE());
        assertEquals("Prenom", retrievedEtudiant.getPrenomE());
        assertEquals(Option.SAE, retrievedEtudiant.getOp());

        EtudiantService.removeEtudiant(addedEtudiant.getIdEtudiant());


    }







    }
