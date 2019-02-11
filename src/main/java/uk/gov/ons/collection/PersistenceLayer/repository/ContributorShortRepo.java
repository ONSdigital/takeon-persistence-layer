package uk.gov.ons.collection.PersistenceLayer.repository;

/*
    Name: ContributorRepo.java

    Description: ContributorRepo.java gives the methods that will be used by the controller

*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorEntityShort;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorKey;

@Repository
public interface ContributorShortRepo extends CrudRepository<ContributorEntityShort, ContributorKey>, JpaRepository<ContributorEntityShort, ContributorKey>,
        JpaSpecificationExecutor<ContributorEntityShort>, PagingAndSortingRepository<ContributorEntityShort, ContributorKey> {

    ContributorEntityShort findByReference(String reference);


}
