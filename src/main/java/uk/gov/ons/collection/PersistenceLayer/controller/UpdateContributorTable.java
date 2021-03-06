package uk.gov.ons.collection.PersistenceLayer.controller;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.ContributorKey;
import uk.gov.ons.collection.PersistenceLayer.repository.ContributorRepo;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.sql.Timestamp;

@Log4j2
@RestController
@RequestMapping(value = "/Update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UpdateContributorTable {
    @Autowired
    ContributorRepo contributorRepo;

    @PutMapping(value = "/LockedStatus/{args}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateLockedStatus (@RequestBody String lockedByStatus,
                                    @MatrixVariable Map<String, String> matrixVars){
        // get the contributor key
        String reference = matrixVars.get("reference");
        log.debug("The value of reference={}",reference);
        String period = matrixVars.get("period");
        log.debug("The value of period={}",period);
        String survey = matrixVars.get("survey");
        log.debug("The value of survey={}",survey);
        // build the JSONObject from the lockedByStatus
        JSONObject jsonOfUpdatedLock = new JSONObject(lockedByStatus);
        // Get the lockedBy string
        String lockedBy = jsonOfUpdatedLock.get("lockedBy").toString();
        log.debug("The value of lockedBy={}",lockedBy);


        // Date date = new Date();

        ContributorKey contributorKey = new ContributorKey(reference, period, survey);
        // Get the contributor
        ContributorEntity contributorEntity = contributorRepo.getOne(contributorKey);
        // set the locked by field
        contributorEntity.setLockedBy(jsonOfUpdatedLock.getString("lockedBy"));
        if(Objects.equals(lockedBy, "")){
            // If lockedBy has an empty string, then the document has been checked in and we need to set the lockedDate to NULL
            contributorEntity.setLockedDate(null);
        }
        else {
            //Otherwise set the lockedDate to the current time
          contributorEntity.setLockedDate(new Timestamp(new Date().getTime()));
        }
        //Save the entity
        contributorRepo.save(contributorEntity);
    }

    @PutMapping("/Status/{args}")
    public void updateStatus(@RequestBody String status, @MatrixVariable Map<String, String> matrixVars){

        String reference = matrixVars.get("reference");
        log.debug("The value of reference={}",reference);
        String period = matrixVars.get("period");
        log.debug("The value of period={}",period);
        String survey = matrixVars.get("survey");
        log.debug("The value of survey={}", survey);

        JSONObject jsonObject = new JSONObject(status);

        ContributorKey contributorKey = new ContributorKey(reference, period, survey);
        // Get the contributor
        ContributorEntity contributorEntity = contributorRepo.getOne(contributorKey);

        contributorEntity.setStatus(jsonObject.getString("status"));
        contributorRepo.save(contributorEntity);
    }

}
