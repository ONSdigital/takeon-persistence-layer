package uk.gov.ons.collection.PersistenceLayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorKey;
import uk.gov.ons.collection.PersistenceLayer.entity.ResponseEntity;
import uk.gov.ons.collection.PersistenceLayer.repository.ContributorRepo;
import uk.gov.ons.collection.PersistenceLayer.repository.ResponseRepo;

import java.util.Map;

@RestController
@RequestMapping(value = "/QuestionResponse")
public class QuestionResponseController {

    @Autowired
    private ContributorRepo contributorRepo;

    @Autowired
    private ResponseRepo responseRepo;

    @GetMapping(value = "/Responses/{args}")
    public Iterable<ResponseEntity> getResponses (@MatrixVariable Map<String, String> matrixVars){
        String v1 = matrixVars.get("reference");
        String v2 = matrixVars.get("period");
        String v3= matrixVars.get("survey");

        ContributorKey contributorKey = new ContributorKey(v1,v2,v3);

        ContributorEntity contributorEntity = contributorRepo.getOne(contributorKey);

        return  contributorEntity.getResponseEntityList();
    }
}