package uk.gov.ons.collection.PersistenceLayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorKey;
import uk.gov.ons.collection.PersistenceLayer.entity.FormDefinitionEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.FormEntity;
import uk.gov.ons.collection.PersistenceLayer.repository.ContributorRepo;
import uk.gov.ons.collection.PersistenceLayer.repository.FormRepo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/FormDefinition")
public class FormDefinitionController {

    @Autowired
    private ContributorRepo contributorRepo;

    @Autowired
    private FormRepo formRepo;

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private EntityManager em;
    @GetMapping(value = "/GetFormDefinition/{args}")
    public Iterable<FormDefinitionEntity> formDefinition (@MatrixVariable Map<String, String> matrixVars){

        String v1 = matrixVars.get("reference");
        String v2 = matrixVars.get("period");
        String v3 = matrixVars.get("survey");

        ContributorKey contributorKey = new ContributorKey(v1,v2,v3);

        ContributorEntity contributorEntity = contributorRepo.getOne(contributorKey);

      return  contributorEntity.getFormEntity().getFormDefinitionEntityList();
    }
    @GetMapping(value = "/GetAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<ContributorEntity> getAll () {
        return formRepo.findById(4).get().getContributorEntityList();
    }
}
