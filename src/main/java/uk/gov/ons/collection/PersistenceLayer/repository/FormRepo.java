package uk.gov.ons.collection.PersistenceLayer.repository;

/*
    Name: ContributorRepo.java

    Description: ContributorRepo.java gives the methods that will be used by the controller

*/
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorKey;
import uk.gov.ons.collection.PersistenceLayer.entity.FormEntity;
//import uk.gov.ons.collection.PersistenceLayer.entity.QContributorEntity;

@Repository
public interface FormRepo extends CrudRepository<FormEntity, Integer>, JpaRepository<FormEntity, Integer>,
        JpaSpecificationExecutor<FormEntity> {

    //List<uk.gov.ons.collection.entity.ContributorEntity> findByPeriodAndSurvey(String Survey, String Period);


}
