package uk.gov.ons.collection.PersistenceLayer.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.ons.collection.PersistenceLayer.entity.*;
import uk.gov.ons.collection.PersistenceLayer.repository.ContributorRepo;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping(value = "/Response")
public class CombinedDefinitionResponceController {
    @Autowired
    private ContributorRepo contributorRepo;

    @GetMapping("/QuestionResponse/{args}")
    public Map combinedDefintionResponse(@MatrixVariable Map<String, String> matrixVars){

        String reference = matrixVars.get("reference");
        log.debug("The value of reference = {}",reference);
        String period = matrixVars.get("period");
        log.debug("The value of period = {}",period);
        String survey = matrixVars.get("survey");
        log.debug("The value of survey = {}",survey);

        ContributorKey contributorKey = new ContributorKey(reference, period, survey);
        ContributorEntity contributorEntity = contributorRepo.getOne(contributorKey);


        Iterable<ResponseEntity> questionResponseEntity = contributorEntity.getResponseEntityList();
        Iterable<FormDefinitionEntity> formDefinitionEntities = contributorEntity.getFormEntity().getFormDefinitionEntityList();

        IterableCombiner convertedIterable = new IterableCombiner();
        convertedIterable.ConvertToMap(questionResponseEntity, formDefinitionEntities);

        Map mapOfIterable = convertedIterable.ConvertToMap(questionResponseEntity, formDefinitionEntities);

        return mapOfIterable;
    }
}
