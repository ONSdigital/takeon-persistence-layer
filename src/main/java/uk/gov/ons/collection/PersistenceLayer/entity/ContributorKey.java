package uk.gov.ons.collection.PersistenceLayer.entity;

import io.swagger.annotations.ApiModel;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@Embeddable
@ApiModel(value = "ContributorKey", description = "The Contributor primary Key entity")
public class ContributorKey implements Serializable {

    @Column(name = "reference", length = 11, nullable = false)
    private String reference;

    @Column(name = "period", length = 6, nullable = false)
    private String period;

    @Column(name = "survey", length = 3, nullable = false)
    private String survey;

    public ContributorKey() {

    }

    public ContributorKey(String reference, String period, String survey){
        this.reference = reference;
        this.period = period;
        this.survey = survey;
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

}
