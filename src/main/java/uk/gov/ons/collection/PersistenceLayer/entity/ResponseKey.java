package uk.gov.ons.collection.PersistenceLayer.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ResponseKey implements Serializable {

    @Column(name = "reference", columnDefinition = "char(11)", nullable = false)
    private String reference;

    @Column(name = "period", columnDefinition = "char(6)", nullable = false)
    private String period;

    @Column(name = "survey", columnDefinition = "char(3)", nullable = false)
    private String survey;

    @Column(name = "questioncode", columnDefinition = "char(4)", nullable = false)
    private String questionCode;

    @Column(name = "instance",columnDefinition = "int", nullable = false)
    private int instance;

    public ResponseKey(){}

    public ResponseKey(String reference, String period, String survey, String questionCode, Integer instance) {
        this.reference = reference;
        this.period = period;
        this.survey = survey;
        this.questionCode = questionCode;
        this.instance = instance;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public Integer getInstance() {
        return instance;
    }

    public void setInstance(Integer instance) {
        this.instance = instance;
    }
}
