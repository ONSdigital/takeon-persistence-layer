package uk.gov.ons.collection.PersistenceLayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ons.collection.PersistenceLayer.entity.FormDefinitionEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.FormDefinitionKey;

@Repository
public interface FormDefinitionRepo extends CrudRepository<FormDefinitionEntity, FormDefinitionKey>, JpaRepository<FormDefinitionEntity, FormDefinitionKey>,
        JpaSpecificationExecutor<FormDefinitionEntity> {
}
