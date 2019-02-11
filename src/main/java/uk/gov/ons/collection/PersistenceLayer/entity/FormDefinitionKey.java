package uk.gov.ons.collection.PersistenceLayer.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FormDefinitionKey implements Serializable {

    @Column(name = "formid", nullable = false)
    private Integer formid;

    @Column(name = "questioncode", nullable = false)
    private String questionCode;

    public FormDefinitionKey(){

    }

    public FormDefinitionKey(Integer formid, String questionCode){
        this.formid = formid;
        this.questionCode = questionCode;
    }

    public Integer getFormId() {
        return formid;
    }

    public void setFormId(Integer formid) {
        this.formid = formid;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }
}
