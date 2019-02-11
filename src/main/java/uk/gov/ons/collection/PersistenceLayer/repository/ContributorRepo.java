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
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorKey;

@Repository
public interface ContributorRepo extends CrudRepository<ContributorEntity, ContributorKey>, JpaRepository<ContributorEntity, ContributorKey>,
        JpaSpecificationExecutor<ContributorEntity>, PagingAndSortingRepository<ContributorEntity, ContributorKey> {

    ContributorEntity findByReference(String reference);


}
