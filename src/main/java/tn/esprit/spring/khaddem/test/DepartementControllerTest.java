package tn.esprit.spring.khaddem.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.spring.khaddem.controllers.DepartementRestController;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.services.DepartementServiceImpl;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DepartementControllerTest {
    @InjectMocks
    private DepartementRestController departementController;

    @Mock
    private DepartementServiceImpl departementService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(departementController).build();
    }

    @Test
    public void testRetrieveDepartement() throws Exception {
        Integer departementId = 1;
        Departement departement = new Departement();
        departement.setIdDepartement(departementId);
        departement.setNomDepart("Some Departement Name");

        when(departementService.retrieveDepartement(departementId)).thenReturn(departement);

        mockMvc.perform(get("/departement/retrieve-departement/{departement-id}", departementId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.idDepartement").value(departementId))
                .andExpect((ResultMatcher) jsonPath("$.nomDepart").value("Some Departement Name"));
    }

    @Test
    public void testAddDepartement() throws Exception {
        Departement newDepartement = new Departement();
        newDepartement.setIdDepartement(2); // Replace with the desired ID
        newDepartement.setNomDepart("New Departement Name");

        when(departementService.addDepartement(newDepartement)).thenReturn(newDepartement);

        mockMvc.perform(post("/departement/add-departement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newDepartement)))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.idDepartement").value(newDepartement.getIdDepartement()))
                .andExpect((ResultMatcher) jsonPath("$.nomDepart").value("New Departement Name"));
    }
}
