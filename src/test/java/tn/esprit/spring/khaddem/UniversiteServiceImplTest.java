package tn.esprit.spring.khaddem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;
import tn.esprit.spring.khaddem.services.UniversiteServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UniversiteServiceImplTest {
    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private DepartementRepository departementRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllUniversites() {
        List<Universite> universites = new ArrayList<>();
        universites.add(new Universite());
        universites.add(new Universite());
        when(universiteRepository.findAll()).thenReturn(universites);

        List<Universite> retrievedUniversites = universiteService.retrieveAllUniversites();

        assertEquals(2, retrievedUniversites.size());
    }

    @Test
    public void testAddUniversite() {
        Universite u = new Universite();
        when(universiteRepository.save(u)).thenReturn(u);

        Universite addedUniversite = universiteService.addUniversite(u);

        verify(universiteRepository, times(1)).save(u);
        assertEquals(u, addedUniversite);
    }
    @Test
    public void testAssignUniversiteToDepartement() {
        // Créez un objet Universite factice pour les tests
        Universite universite = new Universite();
        universite.setDepartements(new ArrayList<>()); // Assurez-vous que la liste de départements n'est pas null

        // Simulez le comportement de findById dans les repositories
        when(universiteRepository.findById(any(Integer.class))).thenReturn(Optional.of(universite));
        when(departementRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Departement()));

        // Appelez la méthode à tester
        universiteService.assignUniversiteToDepartement(1, 2);

        // Vérifiez que la méthode a été appelée avec les bons arguments
        verify(universiteRepository, times(1)).findById(1);
        verify(departementRepository, times(1)).findById(2);

        // Vérifiez que le département a été ajouté à l'université
        assertEquals(1, universite.getDepartements().size());
    }

}
