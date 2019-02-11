package uk.gov.ons.collection.PersistenceLayer.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Form", schema = "dev01")
public class FormEntity {

    @JsonManagedReference
    @OneToMany(mappedBy = "formEntity")
    private List<ContributorEntity> contributorEntityList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "formEntity")
    private List<FormDefinitionEntity> formDefinitionEntityList = new ArrayList<>();

    @Id
    @Column(name = "formid", nullable = false)
    private Integer formid;

    @Column(name = "Survey", nullable = false)
    private String survey;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "periodstart", nullable = false)
    private String periodStart;

    @Column(name = "periodend", nullable = false)
    private String periodEnd;

    @Column(name = "createdby", nullable = false)
    private String createdBy;

    @Column(name = "createddate", nullable = false)
    private String createdDate;

    @Column(name = "lastupdatedby")
    private String lastUpdatedBy;

    @Column(name = "lastupdateddate")
    private String lastUpdatedDate;

    public Integer getFormid() {
        return formid;
    }

    public List<ContributorEntity> getContributorEntityList() {
        return contributorEntityList;
    }

    public void setContributorEntityList(List<ContributorEntity> contributorEntityList) {
        this.contributorEntityList = contributorEntityList;
    }

    public List<FormDefinitionEntity> getFormDefinitionEntityList() {
        return formDefinitionEntityList;
    }

    public void setFormDefinitionEntityList(List<FormDefinitionEntity> formDefinitionEntityList) {
        this.formDefinitionEntityList = formDefinitionEntityList;
    }

    public void setFormid(Integer formid) {
        this.formid = formid;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    public String getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
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

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
