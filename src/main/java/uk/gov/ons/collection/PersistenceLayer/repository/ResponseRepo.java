package uk.gov.ons.collection.PersistenceLayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ons.collection.PersistenceLayer.entity.ResponseEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.ResponseKey;

import java.util.List;

@Repository
public interface ResponseRepo extends CrudRepository <ResponseEntity, ResponseKey>, JpaRepository<ResponseEntity, ResponseKey>,
        JpaSpecificationExecutor<ResponseEntity>{

}

