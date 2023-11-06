package tn.esprit.spring.khaddem.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Universite;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.UniversiteRepository;
import tn.esprit.spring.khaddem.services.DepartementServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class DepartementServiceImplTest {

    @InjectMocks
    private DepartementServiceImpl departementService;

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddDepartement() {
        Departement d = new Departement();
        d.setIdDepartement(1);
        d.setNomDepart("Departement 1");

        Mockito.when(departementRepository.save(Mockito.any(Departement.class))).thenReturn(d);

        Departement savedDepartement = departementService.addDepartement(d);

        assertEquals(1, savedDepartement.getIdDepartement());
        assertEquals("Departement 1", savedDepartement.getNomDepart());
    }

    @Test
    public void testUpdateDepartement() {
        Departement d = new Departement();
        d.setIdDepartement(1);
        d.setNomDepart("Departement 1");

        Mockito.when(departementRepository.save(Mockito.any(Departement.class))).thenReturn(d);

        Departement updatedDepartement = departementService.updateDepartement(d);

        assertEquals(1, updatedDepartement.getIdDepartement());
        assertEquals("Departement 1", updatedDepartement.getNomDepart());
    }

    @Test
    public void testRetrieveDepartementsByUniversite() {
        int universiteId = 1;
        Universite universite = new Universite();
        universite.setIdUniversite(universiteId);

        Departement departement1 = new Departement();
        departement1.setIdDepartement(1);
        departement1.setNomDepart("Departement 1");

        Departement departement2 = new Departement();
        departement2.setIdDepartement(2);
        departement2.setNomDepart("Departement 2");

        universite.setDepartements(Arrays.asList(departement1, departement2));

        Mockito.when(universiteRepository.findById(universiteId)).thenReturn(Optional.of(universite));

        List<Departement> retrievedDepartements = departementService.retrieveDepartementsByUniversite(universiteId);

        assertEquals(2, retrievedDepartements.size());
        assertEquals(1, retrievedDepartements.get(0).getIdDepartement());
        assertEquals("Departement 1", retrievedDepartements.get(0).getNomDepart());
        assertEquals(2, retrievedDepartements.get(1).getIdDepartement());
        assertEquals("Departement 2", retrievedDepartements.get(1).getNomDepart());
    }
}
