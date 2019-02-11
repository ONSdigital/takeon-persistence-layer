package uk.gov.ons.collection.PersistenceLayer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.ons.collection.PersistenceLayer.entity.ResponseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/responses")
public class GetContributorResponses {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @GetMapping(value = "/CurrentResponses/{args}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponseEntity> getCurrentResponses(@MatrixVariable Map<String, String> urlArguments){
        System.out.println("reached response method");

        String reference = urlArguments.get("reference");
        String period = urlArguments.get("period");
        String survey = urlArguments.get("survey");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<ResponseEntity> ResponseEntities =
                entityManager.createNamedQuery("Response.findAllByContributorKey", ResponseEntity.class).setParameter(1,reference)
                        .setParameter(2, period).setParameter(3, survey).getResultList();
        entityManager.close();
        return ResponseEntities;
    }
}
