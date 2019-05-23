package uk.gov.ons.collection.PersistenceLayer.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uk.gov.ons.collection.PersistenceLayer.entity.*;
import uk.gov.ons.collection.PersistenceLayer.repository.ContributorRepo;
import uk.gov.ons.collection.PersistenceLayer.repository.ResponseRepo;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;


@RestController
@RequestMapping(value = "/Upsert", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UpdateResponseController {
    private static final Logger logger = LogManager.getLogger(UpdateResponseController.class);
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    ResponseRepo responseRepo;

    @Autowired
    ContributorRepo contributorRepo;

    @PutMapping(value = "/UpdateResponses/{args}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateResponse(@RequestBody String updatedResponseDetails,
                               @MatrixVariable Map<String, String> matrixVars) {
        String reference = matrixVars.get("reference");
        logger.debug("The value of reference={}",reference);
        String period = matrixVars.get("period");
        logger.debug("The value of period={}",period);
        String survey = matrixVars.get("survey");
        logger.debug("The value of survey={}",survey);

        List<ResponseEntity> responseEntities = new ArrayList<>();

        // We want a counter to make sure at least one record is updated
        int counter = 0;

        JSONObject jsonOfUpdatedResponses = new JSONObject(updatedResponseDetails);
        // Get the username
        String user = jsonOfUpdatedResponses.getString("user");
        Timestamp time = new Timestamp(new Date().getTime());

        // Get the updated responses
        JSONArray jsonObjectOfUpdatedResponses = jsonOfUpdatedResponses.getJSONArray("Updated Responses");

        for (int index = 0; index < jsonObjectOfUpdatedResponses.length(); index++) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                ResponseEntity responseEntity = objectMapper.readValue(jsonObjectOfUpdatedResponses.get(index).toString(), ResponseEntity.class);
                responseEntities.add(responseEntity);
            } catch (Exception error) {
                error.printStackTrace();
            }
        }

        // Create an EntityManger
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // Begin a transaction
        entityManager.getTransaction().begin();

        for (ResponseEntity responseEntity : responseEntities) {
            try {
                responseEntity.setPeriod(period);
                responseEntity.setSurvey(survey);
                responseEntity.setReference(reference);
                System.out.println(responseEntity.toString());
                entityManager.merge(responseEntity);
                counter++;
            } catch (Exception error) {
                entityManager.getTransaction().rollback();
                entityManager.close();
            }
        }

        if (counter > 0) {
            ContributorKey contributorKey = new ContributorKey(reference, period, survey);
            ContributorEntity contributorEntityToUpdate = contributorRepo.getOne(contributorKey);
            contributorEntityToUpdate.setLastUpdatedBy(user);
            contributorEntityToUpdate.setLastUpdatedDate(time);
            entityManager.merge(contributorEntityToUpdate);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}