package tn.esprit.spring.khaddem;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.entities.*;
import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.EquipeRepository;
import tn.esprit.spring.khaddem.repositories.EtudiantRepository;
import tn.esprit.spring.khaddem.services.ContratServiceImpl;
import tn.esprit.spring.khaddem.services.EquipeServiceImpl;
import tn.esprit.spring.khaddem.services.EtudiantServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EtudiantServiceImplTestMock {


    @Mock
    EtudiantRepository etudiantRepository;


    @Mock
    ContratRepository contratRepository;

    @Mock
    EquipeRepository equipeRepository;



    @InjectMocks
    EtudiantServiceImpl etudiantService;
    @InjectMocks
    EquipeServiceImpl equipeService;

    @InjectMocks
    ContratServiceImpl contratService;



    Etudiant e = Etudiant.builder().nomE("Abbes").prenomE("Achraf").op(Option.SAE).build();
    List<Etudiant> list = new ArrayList<Etudiant>(){
    {
    add(Etudiant.builder().nomE("Abbes").prenomE("Achraf").op(Option.SAE).build());
    add(Etudiant.builder().nomE("Shili").prenomE("Neyrouz").op(Option.GAMIX).build());
    add(Etudiant.builder().nomE("Ghassen").prenomE("Alamia").op(Option.INFINI).build());

    }
    };


    @Test
    void retreiveEtudiantTest() {
        Integer id = 1;

        when(etudiantRepository.findById(anyInt())).thenReturn(Optional.of(e));

        Etudiant etudiant = etudiantService.retrieveEtudiant(id);

        assertNotNull(etudiant);
        verify(etudiantRepository, times(1)).findById(id);
    }



    @Test
    void addAndAssignEtudiantToEquipeAndContractTest() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.OCTOBER, 10);
        Date date = calendar.getTime();

        Etudiant e1 = Etudiant.builder().nomE("Abbes").prenomE("Achraf").op(Option.SAE).build();

        Contrat c1 = Contrat.builder().dateDebutContrat(new Date()).dateFinContrat(date)
                .specialite(Specialite.CLOUD).montantContrat(200).archived(true).build();

        Equipe eq1 = Equipe.builder().nomEquipe("Equipe 1 ").niveau(Niveau.JUNIOR).build();


        when(contratRepository.findById(1)).thenReturn(Optional.of(c1));
        when(equipeRepository.findById(2)).thenReturn(Optional.of(eq1));
        when(etudiantRepository.save(e1)).thenReturn(e1);


        Etudiant result = etudiantService.addAndAssignEtudiantToEquipeAndContract(e1, 1, 2);


        verify(contratRepository, times(1)).findById(1);
        verify(equipeRepository, times(1)).findById(2);
        verify(etudiantRepository, times(1)).save(e1);


        assertEquals(e1, result);
        assertEquals(e1, c1.getEtudiant());
        assertEquals(1, e1.getEquipes().size());



    }













    }
