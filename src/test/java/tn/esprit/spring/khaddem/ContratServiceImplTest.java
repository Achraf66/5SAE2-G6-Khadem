package tn.esprit.spring.khaddem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.khaddem.entities.Contrat;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.entities.Option;
import tn.esprit.spring.khaddem.entities.Specialite;
import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.EtudiantRepository;
import tn.esprit.spring.khaddem.services.ContratServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
public class ContratServiceImplTest {

    @InjectMocks
    private ContratServiceImpl contratService;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testRetrieveAllContrats() {
        Contrat mockContrat1 = Contrat.builder().build();
        Contrat mockContrat2 = Contrat.builder().build();

        List<Contrat> mockContrats = Arrays.asList(mockContrat1, mockContrat2);

        when(contratRepository.findAll()).thenReturn(mockContrats);

        List<Contrat> contrats = contratService.retrieveAllContrats();

        assertNotNull(contrats);
        assertEquals(2, contrats.size());
    }

    @Test
    void testAddContrat() {

        Contrat c = Contrat.builder()
                .idContrat(5)
                .montantContrat(555)
                .specialite(Specialite.IA)
                .dateDebutContrat(new Date())
                .dateFinContrat(new Date())
                .archived(false)
                .build();

        log.info(c.toString());

        Contrat c1 = contratService.addContrat(c);
        if ( c1 != null ) {
            assertNotNull(c1);
            assertNotNull(c.getIdContrat());
            assertTrue(c.getMontantContrat().equals(555));
            assertTrue(c.getSpecialite().equals(Specialite.IA));
            assertNotNull(c.getDateDebutContrat());
            assertNotNull(c.getDateFinContrat());
            assertFalse(c.getArchived());

        contratService.removeContrat(c.getIdContrat());
        }
    }

    @Test
    public void testUpdateContrat() {
        Contrat mockContrat = Contrat.builder().build();

        when(contratRepository.save(any(Contrat.class))).thenReturn(mockContrat);

        Contrat updatedContrat = contratService.updateContrat(Contrat.builder().build());

        assertNotNull(updatedContrat);
    }

    @Test
    public void testRetrieveContrat() {
        Contrat mockContrat = Contrat.builder().build();

        when(contratRepository.findById(anyInt())).thenReturn(java.util.Optional.of(mockContrat));

        Contrat retrievedContrat = contratService.retrieveContrat(1);
        assertNotNull(retrievedContrat);
    }

    @Test
    public void testRemoveContrat() {
        doNothing().when(contratRepository).deleteById(anyInt());

        assertDoesNotThrow(() -> contratService.removeContrat(1));
    }

    @Test
    public void testAddContrat1() {
        Contrat mockContrat = Contrat.builder().build();

        when(contratRepository.save(any(Contrat.class))).thenReturn(mockContrat);

        Contrat addedContrat = contratService.addContrat(Contrat.builder().build());

        assertNotNull(addedContrat);
    }

    @Test
    public void testRetrieveAndUpdateStatusContrat() {
        Contrat mockContrat1 = Contrat.builder()
                .dateFinContrat(new Date(System.currentTimeMillis() + 1000))
                .build();

        Contrat mockContrat2 = Contrat.builder()
                .dateFinContrat(new Date(System.currentTimeMillis() - 1000))
                .build();

        List<Contrat> mockContrats = Arrays.asList(mockContrat1, mockContrat2);

        when(contratRepository.findAll()).thenReturn(mockContrats);
        when(contratRepository.save(any(Contrat.class))).thenReturn(mockContrat1);

        assertDoesNotThrow(() -> contratService.retrieveAndUpdateStatusContrat());
    }


    @Test
    public void testGetChiffreAffaireEntreDeuxDates() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date customStartDate = dateFormat.parse("2023-01-01");
        Date customStartDate2 = dateFormat.parse("2023-03-02");
        Contrat mockContrat1 = Contrat.builder()
                .specialite(Specialite.IA)
                .montantContrat(1000)
                .dateDebutContrat(customStartDate)
                .dateFinContrat(customStartDate2)
                .build();


        List<Contrat> mockContrats = Arrays.asList(mockContrat1);

        when(contratRepository.findAll()).thenReturn(mockContrats);

        float expectedChiffreAffaire = (2 * 1000) ;

        float chiffreAffaire = contratService.getChiffreAffaireEntreDeuxDates(customStartDate, customStartDate2);

        assertEquals(expectedChiffreAffaire, chiffreAffaire);
    }


}
