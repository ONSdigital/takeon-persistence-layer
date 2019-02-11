package uk.gov.ons.collection.PersistenceLayer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@IdClass(ResponseKey.class)
@Table(name = "Response", schema = "dev01")
@NamedQuery(name = "Response.findAllByContributorKey", query = "SELECT r from ResponseEntity r WHERE r.reference = ?1 AND r.period = ?2 AND r.survey = ?3")
public class ResponseEntity {

    @JsonBackReference
    @ManyToOne
    @JoinColumns({
            @JoinColumn(updatable = false, insertable = false, name = "reference", referencedColumnName = "reference"),
            @JoinColumn(updatable = false, insertable = false, name = "period", referencedColumnName = "period"),
            @JoinColumn(updatable = false, insertable = false, name = "survey", referencedColumnName = "survey")
    })
    private ContributorEntity contributorEntity;

    @Id
    @Column(name = "reference", columnDefinition = "char(11)", nullable = false)
    private String reference;

    @Id
    @Column(name = "period", columnDefinition = "char(6)", nullable = false)
    private String period;

    @Id
    @Column(name = "survey", columnDefinition = "char(3)", nullable = false)
    private String survey;

    @Id
    @Column(name = "questioncode", columnDefinition = "char(4)", nullable = false)
    private String questionCode;

    @Id
    @Column(name = "instance", columnDefinition = "int", nullable = false)
    private int instance;

    @Column(name = "response", length = 256, nullable = false)
    private String response;

    @Column(name = "createdby", length = 16, nullable = false)
    private String createdBy;

    @Column(name = "createddate", length = 12, nullable = false)
    private Timestamp createdDate;

    @Column(name = "lastupdatedby", length = 16, nullable = false)
    private String lastUpdatedBy;


    @Column(name = "lastupdateddate", length = 12, nullable = false)
    private Timestamp lastUpdatedDate;

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "contributorEntity=" + contributorEntity +
                ", reference='" + reference + '\'' +
                ", period='" + period + '\'' +
                ", survey='" + survey + '\'' +
                ", questionCode='" + questionCode + '\'' +
                ", instance=" + instance +
                ", response='" + response + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", lastUpdatedDate=" + lastUpdatedDate +
                '}';
    }

    public ContributorEntity getContributorEntity() {
        return contributorEntity;
    }

    public void setContributorEntity(ContributorEntity contributorEntity) {
        this.contributorEntity = contributorEntity;
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

    public int getInstance() {
        return instance;
    }

    public void setInstance(int instance) {
        this.instance = instance;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = Timestamp.valueOf(createdDate);
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Timestamp getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = Timestamp.valueOf(lastUpdatedDate);
    }
}