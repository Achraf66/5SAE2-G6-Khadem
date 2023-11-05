package tn.esprit.spring.khaddem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.entities.Equipe;
import tn.esprit.spring.khaddem.entities.Niveau;
import tn.esprit.spring.khaddem.repositories.EquipeRepository;
import tn.esprit.spring.khaddem.services.EquipeServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
 class EquipeServiceImplTest {

    @InjectMocks
    private EquipeServiceImpl equipeService;

    @Mock
    private EquipeRepository equipeRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testRetrieveAllEquipes() {
        // Créez une liste fictive d'équipes
        List<Equipe> equipes = new ArrayList<>();
        equipes.add(new Equipe(1, "Equipe 1"));
        equipes.add(new Equipe(2, "Equipe 2"));
        when(equipeRepository.findAll()).thenReturn(equipes);

        List<Equipe> result = equipeService.retrieveAllEquipes();

        assertIterableEquals(equipes, result);
    }

    @Test
     void testAddEquipe() {
        Equipe equipeToAdd = new Equipe(1, "Nouvelle équipe");
        when(equipeRepository.save(equipeToAdd)).thenReturn(equipeToAdd);

        Equipe addedEquipe = equipeService.addEquipe(equipeToAdd);

        assertSame(equipeToAdd, addedEquipe);
    }
    void testUpdateEquipe() {
        // Create an updated Equipe
        Equipe updatedEquipe = new Equipe();
        updatedEquipe.setIdEquipe(1); // Set the ID of an existing Equipe
        updatedEquipe.setNomEquipe("Nouveau nom d'équipe");
        updatedEquipe.setNiveau(Niveau.SENIOR);

        // Mock the save method of equipeRepository
        Mockito.when(equipeRepository.save(Mockito.any(Equipe.class))).thenReturn(updatedEquipe);

        // Call the method to test
        Equipe result = equipeService.updateEquipe(updatedEquipe);

        // Assertions to verify that the result matches the updatedEquipe
        Assertions.assertEquals(updatedEquipe.getIdEquipe(), result.getIdEquipe());
        Assertions.assertEquals(updatedEquipe.getNomEquipe(), result.getNomEquipe());
        Assertions.assertEquals(updatedEquipe.getNiveau(), result.getNiveau());
    }








@Test
    void testRetrieveEquipe() {
        Equipe equipe = new Equipe(1, "Équipe existante");
        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe));

        Equipe retrievedEquipe = equipeService.retrieveEquipe(1);

        assertNotNull(retrievedEquipe);
    }

    @Test
     void testAddAndRetrieveEquipe() {
        Equipe equipeToAdd = new Equipe(1, "Nouvelle équipe");
        when(equipeRepository.save(equipeToAdd)).thenReturn(equipeToAdd);

        Equipe addedEquipe = equipeService.addEquipe(equipeToAdd);

        assertNotNull(addedEquipe);

        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipeToAdd));
        Equipe retrievedEquipe = equipeService.retrieveEquipe(1);

        assertEquals(equipeToAdd, retrievedEquipe);
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

}
