package uk.gov.ons.collection.test;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import cucumber.api.java.Before;
import net.minidev.json.JSONArray;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;


import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static com.jayway.jsonassert.JsonAssert.with;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;
import uk.gov.ons.collection.PersistenceLayer.WebMvcConfiguration;
import uk.gov.ons.collection.PersistenceLayer.controller.ContributorController;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.FormEntity;
import uk.gov.ons.collection.PersistenceLayer.repository.ContributorRepo;
import uk.gov.ons.collection.PersistenceLayer.repository.FormRepo;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.sql.Timestamp;
import java.util.*;

@ActiveProfiles("mocking")
@SpringBootTest(classes = uk.gov.ons.collection.PersistenceLayer.PersistenceLayerApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {uk.gov.ons.collection.PersistenceLayer.PersistenceLayerApp.class, TestingDataSourceConfig.class})
public class ContributorIntegrationTest {

    private static final String HTTP_METHODS = "GET|POST|PUT|HEAD|DELETE|OPTIONS|PATCH|TRACE";
    private static final String COUNT_COMPARISON = "(?: (less than|more than|at least|at most))?";

    private static final String LOCAL_HOST = "http://localhost:";

    @LocalServerPort
    private int port;

    private MockMvc mockMvc;

    @Autowired
    private ContributorController contributorController;

    //@Autowired
    //private TestEntityManager entityManager;

    @Autowired
    private FormRepo formRepo;

    @Autowired
    private ContributorRepo conRepo;


    ResultActions resultActions;

    @Before
    public void setup() throws Exception {
        //MockitoAnnotations.initMocks(this);
        //this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(contributorController).setRemoveSemicolonContent(false).build();

        FormEntity formEntity1 = new FormEntity();
        formEntity1.setFormid(9);
        formEntity1.setCreatedBy("");
        formEntity1.setDescription("Form 9");
        formEntity1.setCreatedDate("");
        //formEntity1.setLastUpdatedBy("");
        formEntity1.setPeriodEnd("");
        formEntity1.setPeriodStart("");
        formEntity1.setSurvey("");

        formRepo.save(formEntity1);


        List<ContributorEntity> contributorEntities = new ArrayList<>();
        ContributorEntity contributor0 = new ContributorEntity.Builder("49900000001", "201803", "009")
                .status("Dead")
                .formId(9)
                .createdDate(new Timestamp(new Date().getTime()))
                .lastUpdatedDate(new Timestamp(new Date().getTime()))
                .lockedDate(new Timestamp(new Date().getTime()))
                .receiptDate(new Timestamp(new Date().getTime()))
                .build();
        ContributorEntity contributor1 = new ContributorEntity.Builder("49900000001", "201803", "099")
                .status("Dead")
                .formId(9)
                .createdDate(new Timestamp(new Date().getTime()))
                .lastUpdatedDate(new Timestamp(new Date().getTime()))
                .lockedDate(new Timestamp(new Date().getTime()))
                .receiptDate(new Timestamp(new Date().getTime()))
                .build();
        ContributorEntity contributor2 = new ContributorEntity.Builder("49900000001", "201809", "009")
                .status("Dead")
                .formId(9)
                .createdDate(new Timestamp(new Date().getTime()))
                .lastUpdatedDate(new Timestamp(new Date().getTime()))
                .lockedDate(new Timestamp(new Date().getTime()))
                .receiptDate(new Timestamp(new Date().getTime()))
                .build();
        ContributorEntity contributor3 = new ContributorEntity.Builder("49900000002", "201803", "009")
                .status("Dead")
                .formId(9)
                .createdDate(new Timestamp(new Date().getTime()))
                .lastUpdatedDate(new Timestamp(new Date().getTime()))
                .lockedDate(new Timestamp(new Date().getTime()))
                .receiptDate(new Timestamp(new Date().getTime()))
                .build();

        contributorEntities.add(contributor0);
        contributorEntities.add(contributor1);
        contributorEntities.add(contributor2);
        contributorEntities.add(contributor3);

        conRepo.saveAll(contributorEntities);

    }

    @When("^the client makes a (.+) call to (.+)$")
    public void the_client_makes_a_GET_call_to_contributor(String typeOfCall, String call) throws Throwable {
        try {
            //UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(call);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(LOCAL_HOST + this.port);
            if (typeOfCall.equals("GET")) {

                resultActions = mockMvc.perform(get(builder.build().encode().toUri()+call)).andDo(print()).andExpect(status().isOk()).andDo(print());
                //resultActions = mockMvc.perform(get("/contributor/searchBy/reference=22222222;period=201803;survey=099")).andDo(print()).andExpect(status().isOk()).andDo(print());
            }
        } catch (ResourceAccessException ex) {
            ex.printStackTrace();
        }

    }

    @Then("^the client receives a valid JSON$")
    public void the_client_receives_a_valid_JSON() throws Throwable {
        assertThat(resultActions.andReturn().getResponse().getContentAsString(), isJson());
    }

    @Then("^the response should contain(?: (less than|more than|at least|at most|exactly))? (\\d+) entit(?:ies|y)$")
    public void the_response_should_n_entity(String compareOp, int noOfEntities) throws Throwable {
        assertThat(resultActions.andReturn().getResponse().getContentAsString(), isJson());
        System.out.println("the response :" + resultActions.andReturn().getResponse().getContentAsString());
        JSONArray jsonArray = JsonPath.compile("$").read(new String(resultActions.andReturn().getResponse().getContentAsString()));
        if (compareOp.equals("at least"))
            assertThat(jsonArray.size() , greaterThanOrEqualTo(noOfEntities) );
        else if (compareOp.equals("at most"))
            assertThat(jsonArray.size() , lessThanOrEqualTo(noOfEntities) );
        else if  (compareOp.equals("less than"))
            assertThat(jsonArray.size() , lessThan(noOfEntities) );
        else if  (compareOp.equals("more than"))
            assertThat(jsonArray.size() , greaterThan(noOfEntities) );
        else if  (compareOp.equals("exactly"))
            assertThat(jsonArray.size() , equalTo(noOfEntities) );
    }


    @Then("^the response should contain \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void the_response_should_contain_with_value(String key, String value) throws Throwable {
        assertThat(resultActions.andReturn().getResponse().getContentAsString(), hasJsonPath("$."+key, equalTo(value)));
        with(resultActions.andReturn().getResponse().getContentAsString()).assertThat("$."+key,equalTo(value));
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code(int statusCode) throws Throwable {
        final int currentStatusCode = resultActions.andReturn().getResponse().getStatus();

        assertThat("status code is incorrect : " + resultActions.andReturn().getResponse().getContentAsString()
                , currentStatusCode, is(statusCode));
    }

    @Then("^the response entity at (\\d+) should contain \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void the_response_entity_at_should_contain_with_string_value(int location, String key, String value) throws Throwable {
        JsonNode json= new ObjectMapper().readTree(resultActions.andReturn().getResponse().getContentAsString());
        assertThat(json.get(location), notNullValue());
        assertThat(json.get(location).get(key).textValue(), equalTo(value));
    }

    @Then("^the response entity at (\\d+) should contain \"([^\"]*)\" with value ([^\"]*)$")
    public void the_response_entity_at_should_contain_with__integer_value(int location, String key, Integer value) throws Throwable {
        JsonNode json= new ObjectMapper().readTree(resultActions.andReturn().getResponse().getContentAsString());
        assertThat(json.get(location), notNullValue());
        assertThat(json.get(location).get(key).intValue(), equalTo(value));
    }


}