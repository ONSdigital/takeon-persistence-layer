package uk.gov.ons.collection.test;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.FormEntity;
import uk.gov.ons.collection.PersistenceLayer.repository.ContributorRepo;
import uk.gov.ons.collection.PersistenceLayer.repository.ContributorShortRepo;
import uk.gov.ons.collection.PersistenceLayer.repository.FormDefinitionRepo;
import uk.gov.ons.collection.PersistenceLayer.repository.FormRepo;

import java.sql.Timestamp;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("mocking")
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {uk.gov.ons.collection.PersistenceLayer.PersistenceLayerApp.class})
public class RepositoryMock {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FormRepo formRepo;

    @Autowired
    private ContributorRepo conRepo;

    @Autowired
    private FormDefinitionRepo formDefinitionRepo;

    @Before
    public void setup(){
        FormEntity formEntity1 = new FormEntity();
        formEntity1.setFormid(9);
        formEntity1.setCreatedBy("");
        formEntity1.setDescription("Form 9");
        formEntity1.setCreatedDate("");
        //formEntity1.setLastUpdatedBy("");
        formEntity1.setPeriodEnd("");
        formEntity1.setPeriodStart("");
        formEntity1.setSurvey("");

        // Insert row into H2 database
        entityManager.persist(formEntity1);
        entityManager.flush();

        //Given
        ContributorEntity contributor = new ContributorEntity.Builder("49900000001", "201809", "009")
                .formId(9)
                .createdDate(new Timestamp(new Date().getTime()))
                .lastUpdatedDate(new Timestamp(new Date().getTime()))
                .lockedDate(new Timestamp(new Date().getTime()))
                .receiptDate(new Timestamp(new Date().getTime()))
                .build();

        // Insert row into H2 database
        entityManager.persist(contributor);
        entityManager.flush();

    }

    @Test
    public void whenFindByReference_thenReturnContributor() {

        String reference = "49900000001";

        //When
        ContributorEntity conSearch = conRepo.findByReference(reference);


        //Then
        assertThat(conSearch.getReference())
                .isEqualTo(reference);
    }

}
