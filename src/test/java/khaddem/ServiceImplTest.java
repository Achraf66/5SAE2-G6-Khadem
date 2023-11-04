package khaddem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.entities.Equipe;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.EquipeRepository;
import tn.esprit.spring.khaddem.repositories.EtudiantRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;
import tn.esprit.spring.khaddem.services.ContratServiceImpl;
import tn.esprit.spring.khaddem.services.EquipeServiceImpl;
import tn.esprit.spring.khaddem.services.UniversiteServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceImplTest {
    @Mock
    private UniversiteRepository universiteRepository;
    @InjectMocks
    private UniversiteServiceImpl universiteService;
    @InjectMocks
    private EquipeServiceImpl equipeService;
    @Mock
    private EquipeRepository equipeRepository;

    private Universite universite;
    private Etudiant etudiant;
    private Contrat contrat;
    @Mock
    private EtudiantRepository etudiantRepository;
    @Mock
    private ContratRepository contratRepository;
    @InjectMocks
    private ContratServiceImpl contratService;

    @Test
    void retrieveAllEquipesTest() {
        when(equipeRepository.findAll()).thenReturn(Arrays.asList(new Equipe(), new Equipe()));

        List<Equipe> equipes = equipeService.retrieveAllEquipes();

        assertEquals(2, equipes.size());
        verify(equipeRepository, times(1)).findAll();
    }

    @Test
    void addEquipeTest() {
        Equipe equipe = new Equipe();
        when(equipeRepository.save(equipe)).thenReturn(equipe);

        Equipe returnedEquipe = equipeService.addEquipe(equipe);

        assertNotNull(returnedEquipe);
        verify(equipeRepository, times(1)).save(equipe);
    }

    @Test
    void updateEquipeTest() {
        Equipe equipe = new Equipe();
        when(equipeRepository.save(equipe)).thenReturn(equipe);

        Equipe returnedEquipe = equipeService.updateEquipe(equipe);

        assertNotNull(returnedEquipe);
        verify(equipeRepository, times(1)).save(equipe);
    }

    @Test
    void retrieveEquipeTest() {
        Integer id = 1;
        Equipe equipe = new Equipe();
        when(equipeRepository.findById(id)).thenReturn(Optional.of(equipe));

        Equipe returnedEquipe = equipeService.retrieveEquipe(id);

        assertNotNull(returnedEquipe);
        verify(equipeRepository, times(1)).findById(id);
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        universite = new Universite();
        universite.setIdUniversite(1);
        universite.setNomUniv("Test University");
        universite.setDepartements(new ArrayList<>());
    }

    @Test
    public void whenUpdateUniversite_thenUniversiteShouldBeUpdated() {
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);
        Universite updatedUniversite = universiteService.updateUniversite(universite);
        verify(universiteRepository).save(universite);
        assertEquals("Test University", updatedUniversite.getNomUniv());
        assertEquals(universite.getIdUniversite(), updatedUniversite.getIdUniversite());
    }

    @BeforeEach
    public void setUpE() {
        MockitoAnnotations.initMocks(this);
        etudiant = new Etudiant();
        etudiant.setNomE("EtudiantNom");
        etudiant.setPrenomE("EtudiantPrenom");
        etudiant.setContrats(new ArrayList<>());
        contrat = new Contrat();
        for (int i = 0; i < 3; i++) {
            Contrat existingContrat = new Contrat();
            etudiant.getContrats().add(existingContrat);
        }
    }

    @Test
    public void testAddContractToStudent() {
        when(etudiantRepository.findByNomEAndPrenomE(any(String.class), any(String.class))).thenReturn(etudiant);
        when(contratRepository.save(any(Contrat.class))).thenReturn(contrat);
        Contrat newContrat = contratService.addAndAffectContratToEtudiant(new Contrat(), etudiant.getNomE(), etudiant.getPrenomE());

        verify(contratRepository).save(any(Contrat.class));
        assertEquals(etudiant, newContrat.getEtudiant());
    }

}
