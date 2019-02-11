package uk.gov.ons.collection.PersistenceLayer.controller;

import uk.gov.ons.collection.PersistenceLayer.entity.FormDefinitionEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.FormEntity;
import uk.gov.ons.collection.PersistenceLayer.entity.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IterableCombiner {

    public Map<String,  Map<String, String>> ConvertToMap(Iterable<ResponseEntity> questionResponseEntities, Iterable<FormDefinitionEntity> formDefinitionEntities){
        List<String> responses = new ArrayList<>();
        List<String> questionCodes = new ArrayList<>();
        Map<String, Map<String, String>> questionDetailMap = new HashMap<>();
        List<String> definitionQcodes = new ArrayList<>();
        List<String> createdBy = new ArrayList<>();
        List<String> createdDate = new ArrayList<>();
        List<String> instance = new ArrayList<>();

        questionResponseEntities.forEach(questionResponseEntity -> responses.add(questionResponseEntity.getResponse()));
        questionResponseEntities.forEach(questionResponseEntity -> questionCodes.add(questionResponseEntity.getQuestionCode()));
        formDefinitionEntities.forEach(formDefinitionEntity -> definitionQcodes.add(formDefinitionEntity.getquestionCode()));
        questionResponseEntities.forEach(questionResponseEntity -> createdBy.add(questionResponseEntity.getCreatedBy()));
        questionResponseEntities.forEach(questionResponseEntity -> createdDate.add(questionResponseEntity.getCreatedDate().toString()));
        questionResponseEntities.forEach(questionResponseEntity -> instance.add(Integer.toString(questionResponseEntity.getInstance())));


        for(int i = 0; i < definitionQcodes.size(); i++){
            int questionIndex = questionCodes.indexOf(definitionQcodes.get(i));
            // List<String> responseContainer = new ArrayList<>();
            Map<String, String> responseContainer = new HashMap<>();
            if( questionIndex >= 0) {
                responseContainer.put("response", responses.get(questionIndex));
                responseContainer.put("createdBy", createdBy.get(questionIndex));
                responseContainer.put("createdDate", createdDate.get(questionIndex));
                responseContainer.put("instance", instance.get(questionIndex));
            }

            questionDetailMap.put(definitionQcodes.get(i), responseContainer);
        }
        System.out.println(questionDetailMap.toString());
        return questionDetailMap;
    }
}
