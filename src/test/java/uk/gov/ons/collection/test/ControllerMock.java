package uk.gov.ons.collection.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.ons.collection.PersistenceLayer.WebMvcConfiguration;
import uk.gov.ons.collection.PersistenceLayer.controller.ContributorController;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorEntityShort;
import uk.gov.ons.collection.PersistenceLayer.repository.ContributorRepo;
import uk.gov.ons.collection.PersistenceLayer.repository.ContributorShortRepo;

import javax.persistence.EntityManagerFactory;
import java.util.*;

@ActiveProfiles("mocking")
@RunWith(SpringRunner.class)
@WebMvcTest(ContributorController.class)
@ContextConfiguration(classes = {WebMvcConfiguration.class, uk.gov.ons.collection.PersistenceLayer.PersistenceLayerApp.class})
public class ControllerMock {

    @Autowired
    private MockMvc mockMvc;

    // Dependencies
    @MockBean
    private ContributorRepo conRepo;

    @MockBean
    private ContributorShortRepo conShortRepo;

    @MockBean
    private ContributorController contributorController;

    @MockBean
    private EntityManagerFactory emf;

    @MockBean
    PagedResourcesAssembler assembler;


    @Test
    public void givenReference_whenSearchByReference_thenReturnJsonArray() throws Exception {

        ContributorEntity conEntity = new ContributorEntity.Builder("49900000001", "201809", "009").build();

        Iterable<ContributorEntity> returnedArray = new ArrayList<>(Arrays.asList(conEntity));

        // Create Map to insert variables for call to function
        Map<String, String> matrixVars = new HashMap<String, String>();
        matrixVars.put("reference", conEntity.getReference());
        matrixVars.put("period", conEntity.getPeriod());
        matrixVars.put("survey", conEntity.getSurvey());

        when(contributorController.searchBy(matrixVars)).thenReturn(returnedArray);

        this.mockMvc.perform(get("/contributor/searchBy/reference=49900000001;period=201809;survey=009"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'reference':'49900000001'}]"))
                .andExpect(content().json("[{'period':'201809'}]"))
                .andExpect(content().json("[{'survey':'009'}]"));

    }

    @Test
    public void givenLikeCondition_whenSearchByLike_thenReturnJsonArray() throws Exception {

        ContributorEntityShort conEntity = new ContributorEntityShort.Builder("499", "2017", "023").build();

        Iterable<ContributorEntityShort> returnedArray = new ArrayList<>(Arrays.asList(conEntity));

        // Create Map to insert variables for call to function
        Map<String, String> matrixVars = new HashMap<String, String>();
        matrixVars.put("reference", conEntity.getReference());
        matrixVars.put("period", conEntity.getPeriod());
        matrixVars.put("survey", conEntity.getSurvey());

        when(contributorController.searchByLike(matrixVars)).thenReturn(returnedArray);

        this.mockMvc.perform(get("/contributor/searchByLike/reference=499;period=2017;survey=023"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'reference':'499'}]"))
                .andExpect(content().json("[{'period':'2017'}]"))
                .andExpect(content().json("[{'survey':'023'}]"));

    }

    // Expect to pass as we want a HTTP response status of 404
    @Test
    public void givenIncorrectURL_thenReturnPageNotFound() throws Exception {

        this.mockMvc.perform(get("contributor/searchBy"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void givenUrl_whenSuccess_thenStatusIsOk() throws Exception {

        this.mockMvc.perform(get("/contributor/searchByLikePageable/reference=499;period=2017?page=1&size=10&sort=period,asc"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void whenFirstPageIsRetrieved_thenPageContainsResources() throws Exception {
        Pageable pageable = PageRequest.of(1,10, Sort.Direction.ASC, "period");

        Page<ContributorEntityShort> pageData = conShortRepo.findAll(pageable);
//
//        List<ContributorEntityShort> conData = pageData.getContent();
        Map<String, String> matrixVars = new HashMap<String, String>();
        matrixVars.put("reference", "499");
        matrixVars.put("period", "2017");

        ResponseEntity response = new ResponseEntity<> (pageData, HttpStatus.OK);

        when(contributorController.searchByLikePageable(assembler, matrixVars, 1, 10, "period,asc")).thenReturn(response);

        this.mockMvc.perform(get("/contributor/searchByLikePageable/reference=499;period=2017?page=1&size=10&sort=period,asc"))
                .andDo(print())
                .andExpect(status().isOk());


//        MvcResult result = mockMvc.perform(get("/contributor/searchByLikePageable/reference=499;period=2017?page=8&size=10&sort=period,asc"))
//                .andExpect(status().isOk()).andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        System.out.println("Content = " + content);
//
//        assertThat(content, CoreMatchers.containsString("reference"));



    }


}
