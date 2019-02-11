package uk.gov.ons.collection.PersistenceLayer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@IdClass(FormDefinitionKey.class)
@Table(name = "formdefinition", schema = "dev01")
public class FormDefinitionEntity {

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="formid", insertable = false, updatable = false)
    private FormEntity formEntity;

    @Id
    @Column(name = "formid", nullable = false)
    private Integer formid;

    @Id
    @Column(name = "questioncode", length = 4, nullable = false)
    private String questionCode;

    @Column(name = "displayquestionnumber", length = 16, nullable = false)
    private String displayQuestionNumber;

    @Column(name = "displaytext", length = 128, nullable = false)
    private String displayText;

    @Column(name = "type", length = 16, nullable = false)
    private String type;

    @Column(name = "createdby", length = 16, nullable = false)
    private String createdBy;

    @Column(name = "createddate", length = 7, nullable = false)
    private String createdDate;

    @Column(name = "lastupdatedby", length = 16)
    private String lastUpadatedBy;

    @Column(name = "lastupdateddate", length = 7)
    private String lastUpdatedDate;

    public FormEntity getFormEntity() {
        return formEntity;
    }

    public void setFormEntity(FormEntity formEntity) {
        this.formEntity = formEntity;
    }

    public String getquestionCode() {
        return questionCode;
    }

    public void setquestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public Integer getFormid() {
        return formid;
    }

    public void setFormid(Integer formid) {
        this.formid = formid;
    }

    public String getdisplayQuestionNumber() {
        return displayQuestionNumber;
    }

    public void setdisplayQuestionNumber(String displayQuestionNumber) {
        this.displayQuestionNumber = displayQuestionNumber;
    }

    public String getdisplayText() {
        return displayText;
    }

    public void setdisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpadatedBy() {
        return lastUpadatedBy;
    }

    public void setLastUpadatedBy(String lastUpadatedBy) {
        this.lastUpadatedBy = lastUpadatedBy;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
